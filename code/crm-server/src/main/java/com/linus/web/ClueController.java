package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;
import com.linus.model.TClue;
import com.linus.query.ActivityQuery;
import com.linus.query.ClueQuery;
import com.linus.result.R;
import com.linus.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    @PreAuthorize(value="hasAuthority('clue:list')")
    @GetMapping("/api/clues")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);

        return R.OK(pageInfo);
    }

    @PreAuthorize(value="hasAuthority('clue:import')")
    @PostMapping("/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader("Authorization")String token) throws IOException {//變量名需要與前端formData的名字相同，否則接收不到
        clueService.importExcel(file.getInputStream(),token);
        return R.OK();
    }

    @GetMapping("/api/clue/{phone}")
    public R checkPhone(@PathVariable("phone")String phone){
        Boolean check = clueService.checkPhone(phone);
        return check ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value="hasAuthority('clue:add')")
    @PostMapping("/api/clue")
    public R addActivity(ClueQuery clueQuery, @RequestHeader("Authorization") String token){
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value="hasAuthority('clue:view')")
    @GetMapping("/api/clue/detail/{id}")
    public R loadClue(@PathVariable("id")Integer id){
        TClue tClue = clueService.getClueById(id);
        return R.OK(tClue);
    }

    @PreAuthorize(value="hasAuthority('clue:edit')")
    @PutMapping("/api/clue")
    public R editUser(ClueQuery clueQuery, @RequestHeader("Authorization") String token){
        clueQuery.setToken(token);
        int update = clueService.updateClue(clueQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/clue/{id}")
    public R delClue(@PathVariable("id")Integer id){
        int del = clueService.delClueById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/clue")
    public R batchDelClue(@RequestParam("ids")String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = clueService.batchDelClueByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }
}
