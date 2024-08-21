package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTran;
import com.linus.query.TranQuery;
import com.linus.result.R;
import com.linus.service.TranService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranController {
    @Resource
    private TranService tranService;

    @GetMapping("/api/trans")
    public R tranPage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TTran> pageInfo = tranService.getTranByPage(current);

        return R.OK(pageInfo);
    }

    @PostMapping("/api/customer/tran")
    public R createTran(@RequestBody TranQuery tranQuery, @RequestHeader("Authorization") String token){
        tranQuery.setToken(token);
        int result = tranService.createTran(tranQuery);
        return result >= 1 ? R.OK() : R.FAIL();
    }


    @GetMapping("/api/tran/detail/{id}")
    public R loadCustomer(@PathVariable("id")Integer id){
        TTran tTran = tranService.getTranById(id);
        return R.OK(tTran);
    }
}
