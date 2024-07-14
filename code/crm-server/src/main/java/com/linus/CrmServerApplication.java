package com.linus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@MapperScan(basePackages = {"com.linus.mapper"})
@SpringBootApplication
public class CrmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmServerApplication.class, args);
    }

}
