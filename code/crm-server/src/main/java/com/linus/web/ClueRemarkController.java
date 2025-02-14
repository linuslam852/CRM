package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TClueRemark;
import com.linus.query.ClueRemarkQuery;
import com.linus.result.R;
import com.linus.service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClueRemarkController {
    @Resource
    private ClueRemarkService clueRemarkService;

    @PostMapping("/api/clue/remark")
    public R addClueRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader("Authorization")String token){
        clueRemarkQuery.setToken(token);
        int save = clueRemarkService.saveClueRemark(clueRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @GetMapping("/api/clue/remark")
    public R clueRemarkPage(@RequestParam(value = "current", required = false) Integer current,@RequestParam(value = "clueId") Integer clueId){
        ClueRemarkQuery clueRemarkQuery = new ClueRemarkQuery();
        clueRemarkQuery.setClueId(clueId);
        if(current == null){
            current = 1;
        }
        PageInfo<TClueRemark> pageInfo = clueRemarkService.getClueRemarkByPage(current, clueRemarkQuery);

        return R.OK(pageInfo);
    }

    @GetMapping("/api/clue/remark/{id}")
    public R clueRemark(@PathVariable(value = "id") Integer id){
        TClueRemark tClueRemark = clueRemarkService.getClueRemarkById(id);
        return R.OK(tClueRemark);
    }

    @PutMapping("/api/clue/remark")
    public R editClueRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader("Authorization")String token){
        clueRemarkQuery.setToken(token);
        int save = clueRemarkService.updateClueRemark(clueRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @DeleteMapping("/api/clue/remark/{id}")
    public R delClueRemark(@PathVariable("id")Integer id){
        int del = clueRemarkService.delClueRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }


}
