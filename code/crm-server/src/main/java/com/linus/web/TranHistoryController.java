package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTranHistory;
import com.linus.query.TranHistoryQuery;
import com.linus.result.R;
import com.linus.service.TranHistoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TranHistoryController {
    @Resource
    TranHistoryService tranHistoryService;

    @GetMapping("/api/tran/history")
    public R tranRemarkPage(@RequestParam(value = "current", required = false) Integer current,
                            @RequestParam(value = "tranId") Integer tranId){
        TranHistoryQuery tranHistoryQuery = new TranHistoryQuery();
        tranHistoryQuery.setTranId(tranId);
        if(current == null){
            current = 1;
        }
        PageInfo<TTranHistory> pageInfo = tranHistoryService.getHistorykByPage(current, tranHistoryQuery);

        return R.OK(pageInfo);
    }


    @PostMapping("/api/tran/history")
    public R addTranHistory(@RequestBody TranHistoryQuery tranHistoryQuery,
                            @RequestHeader(value = "Authorization") String token) {

        tranHistoryQuery.setToken(token);
        Boolean result = tranHistoryService.saveTranHistory(tranHistoryQuery);
        return  result ? R.OK() : R.FAIL();
    }
}

