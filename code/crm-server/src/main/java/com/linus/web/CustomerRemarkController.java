package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TClueRemark;
import com.linus.model.TCustomerRemark;
import com.linus.query.ClueRemarkQuery;
import com.linus.query.CustomerRemarkQuery;
import com.linus.result.R;
import com.linus.service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRemarkController {

    @Resource
    private CustomerRemarkService customerRemarkService;

    @PostMapping("/api/customer/remark")
    public R addClueRemark(@RequestBody CustomerRemarkQuery customerRemarkQuery, @RequestHeader("Authorization")String token){
        customerRemarkQuery.setToken(token);
        int save = customerRemarkService.saveCustomerRemark(customerRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @GetMapping("/api/customer/remark")
    public R customerRemarkPage(@RequestParam(value = "current", required = false) Integer current,@RequestParam(value = "customerId") Integer customerId){
        CustomerRemarkQuery customerRemarkQuery = new CustomerRemarkQuery();
        customerRemarkQuery.setCustomerId(customerId);
        if(current == null){
            current = 1;
        }
        PageInfo<TCustomerRemark> pageInfo = customerRemarkService.getCustomerRemarkByPage(current, customerRemarkQuery);

        return R.OK(pageInfo);
    }

    @GetMapping("/api/customer/remark/{id}")
    public R customerRemark(@PathVariable(value = "id") Integer id){
        TCustomerRemark tCustomerRemark = customerRemarkService.getCustomerRemarkById(id);
        return R.OK(tCustomerRemark);
    }

    @PutMapping("/api/customer/remark")
    public R editCustomerRemark(@RequestBody CustomerRemarkQuery customerRemarkQuery, @RequestHeader("Authorization")String token){
        customerRemarkQuery.setToken(token);
        int save = customerRemarkService.updateCustomerRemark(customerRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @DeleteMapping("/api/customer/remark/{id}")
    public R delCustomerRemark(@PathVariable("id")Integer id){
        int del = customerRemarkService.delCustomerRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

}
