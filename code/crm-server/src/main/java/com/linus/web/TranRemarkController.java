package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTranRemark;
import com.linus.query.TranRemarkQuery;
import com.linus.result.R;
import com.linus.service.TranRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranRemarkController {
    @Resource
    TranRemarkService tranRemarkService;

    @GetMapping("/api/tran/remark")
    public R tranRemarkPage(@RequestParam(value = "current", required = false) Integer current, @RequestParam(value = "tranId") Integer tranId){
        TranRemarkQuery tranRemarkQuery = new TranRemarkQuery();
        tranRemarkQuery.setTranId(tranId);
        if(current == null){
            current = 1;
        }
        PageInfo<TTranRemark> pageInfo = tranRemarkService.getTranRemarkByPage(current, tranRemarkQuery);

        return R.OK(pageInfo);
    }

    @PostMapping("/api/tran/remark")
    public R addTranRemark(@RequestBody TranRemarkQuery tranRemarkQuery, @RequestHeader("Authorization")String token){
        tranRemarkQuery.setToken(token);
        int save = tranRemarkService.saveTranRemark(tranRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @GetMapping("/api/tran/remark/{id}")
    public R tranRemark(@PathVariable(value = "id") Integer id){
        TTranRemark tTranRemark = tranRemarkService.getTranRemarkById(id);
        return R.OK(tTranRemark);
    }


    @PutMapping("/api/tran/remark")
    public R editTranRemark(@RequestBody TranRemarkQuery tranRemarkQuery, @RequestHeader("Authorization")String token){
        tranRemarkQuery.setToken(token);
        int save = tranRemarkService.updateTranRemark(tranRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @DeleteMapping("/api/tran/remark/{id}")
    public R delTranRemark(@PathVariable("id")Integer id){
        int del = tranRemarkService.delTranRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
