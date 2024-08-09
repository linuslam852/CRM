package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTran;
import com.linus.model.TUser;
import com.linus.result.R;
import com.linus.service.TranService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
