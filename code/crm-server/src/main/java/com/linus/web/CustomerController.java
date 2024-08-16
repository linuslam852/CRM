package com.linus.web;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.model.TClue;
import com.linus.model.TCustomer;
import com.linus.query.CustomerQuery;
import com.linus.result.CustomerExcel;
import com.linus.result.R;
import com.linus.service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("/api/clue/customer")
    public R convetCustomer(@RequestBody CustomerQuery customerQuery, @RequestHeader("Authorization") String token){
        customerQuery.setToken(token);
        Boolean convert = customerService.converCutomer(customerQuery);
        return convert ? R.OK() : R.FAIL();
    }

    @GetMapping("/api/customers")
    public R customerPage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TCustomer> pageInfo = customerService.getCustomerByPage(current);

        return R.OK(pageInfo);
    }

    @GetMapping("/api/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam(value = "ids",required=false)String ids) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode(Constants.EXCEL_FILE_NAME + System.currentTimeMillis(), StandardCharsets.UTF_8) + ".xlsx");

        List<String> idList = StringUtils.hasText(ids) ? Arrays.asList(ids.split(",")) : new ArrayList<>();
        List<CustomerExcel> dataList = customerService.getCustomerByExcel(idList);

        EasyExcel.write(response.getOutputStream(),CustomerExcel.class)
                .sheet()
                .doWrite(dataList);
    }


    @GetMapping("/api/customer/detail/{id}")
    public R loadClue(@PathVariable("id")Integer id){
        TCustomer tCustomer = customerService.getCustomerById(id);
        return R.OK(tCustomer);
    }

}
