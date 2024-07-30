package com.linus.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.read.listener.ReadListener;
import com.linus.mapper.TClueMapper;
import com.linus.model.TClue;
import com.linus.model.TUser;
import com.linus.utils.JSONUtil;

import java.util.Date;
import java.util.List;

import com.linus.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadDataListener implements ReadListener<TClue> {

    private static final int BATCH_COUNT = 100;
    private List<TClue> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private TClueMapper tClueMapper;
    private String token;

    public UploadDataListener(TClueMapper tClueMapper, String token) {
        this.tClueMapper = tClueMapper;
        this.token = token;
    }


    @Override
    public void invoke(TClue tClue, AnalysisContext context) {
        log.info("解析到一條數據:{}", JSONUtil.toJSON(tClue));

        tClue.setCreateTime(new Date());
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(token), TUser.class).getId();
        tClue.setCreateBy(loginUserId);
        cachedDataList.add(tClue);

        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();

            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有數據解析完成！");
    }


    private void saveData() {
        log.info("{}條數據，開始存入數據庫！", cachedDataList.size());
        tClueMapper.saveClue(cachedDataList);
        log.info("儲存數據庫成功！");
    }
}
