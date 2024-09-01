package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.manager.RedisManager;
import com.linus.mapper.TPermissionMapper;
import com.linus.mapper.TRoleMapper;
import com.linus.mapper.TUserMapper;
import com.linus.model.TPermission;
import com.linus.model.TRole;
import com.linus.model.TUser;
import com.linus.query.BaseQuery;
import com.linus.query.PasswordQuery;
import com.linus.query.UserQuery;
import com.linus.result.CodeEnum;
import com.linus.result.R;
import com.linus.service.UserService;
import com.linus.utils.CacheUtils;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import com.linus.utils.ResponseUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private TRoleMapper tRoleMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private TPermissionMapper tPermissionMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if(tUser == null){
            throw new UsernameNotFoundException("登入帳號不存在");
        }
        List<TRole> tRoleList = tRoleMapper.selectByUserId(tUser.getId());
        List<String> stringRoleList = new ArrayList<>();
        tRoleList.forEach(tRole -> {
            stringRoleList.add(tRole.getRole());
        });
        tUser.setRoleList(stringRoleList);

        //查詢用戶有哪些菜單權限
        List<TPermission> menuPermissionsList = tPermissionMapper.selectMenuPermissionByUserId(tUser.getId());
        tUser.setMemuPermissionList(menuPermissionsList);

        //查詢該用戶有哪些功能權限
        List<TPermission> buttonPermissionList = tPermissionMapper.selectButtonPermissionByUserId(tUser.getId());
        List<String> stringPermissionList = new ArrayList<>();
        buttonPermissionList.forEach(tPermission -> {
            stringPermissionList.add(tPermission.getCode());
        });
        tUser.setPermissionList(stringPermissionList);
        return tUser;
    }

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TUser> list = tUserMapper.selectUserByPage(BaseQuery.builder().build());
        PageInfo<TUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(userQuery,tUser);
        //加密密碼
        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        tUser.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(userQuery.getToken()), TUser.class).getId();
        tUser.setCreateBy(loginUserId);
        return tUserMapper.insertSelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(userQuery,tUser);

        if(StringUtils.hasText(userQuery.getLoginPwd())){
            //加密密碼
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        }


        tUser.setEditTime(new Date());//編輯時間

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(userQuery.getToken()), TUser.class).getId();
        tUser.setEditBy(loginUserId);//編輯人
        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelUserByIds(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }

    @Override
    public List<TUser> getOwnerList() {
        //先從redis查詢
        //redis查詢不到，就從數據庫查詢，並且把數據放入redis(5分鐘過期)
        return CacheUtils.getCacheData(()->{
            //從redis查詢
            return (List<TUser>) redisManager.getValue(Constants.REDIS_OWNER_KEY);

        },
        ()->{
            return tUserMapper.selectByOwner();
        },
        (t)->{
            redisManager.setValue(Constants.REDIS_OWNER_KEY,t);
        });

    }

    @Override
    public R changePwd(PasswordQuery passwordQuery) {
        String oldPwd = passwordQuery.getOldPwd();
        String newPwd = passwordQuery.getNewPwd();
        TUser tUser = tUserMapper.selectByLoginAct(passwordQuery.getLoginAct());

        if(!(StringUtils.hasText(oldPwd) && StringUtils.hasText(newPwd))){
            return R.FAIL(CodeEnum.PASSWORD_IS_EMPTY);
        }
        if(!passwordEncoder.matches(oldPwd,tUser.getLoginPwd())) {
            return R.FAIL(CodeEnum.WRONG_PASSWORD);
        }
        //加密密碼
        tUser.setLoginPwd(passwordEncoder.encode(newPwd));
        int result = tUserMapper.updateByPrimaryKeySelective(tUser);
        return result >= 1 ? R.OK() : R.FAIL();
    }
}
