package com.mszx.hyb.ums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ServletComponentScan   //扫描Servlet
@MapperScan(basePackages = {"com.mszx.hyb.ums.dao", "com.mszx.hyb.common"})
@ComponentScan(basePackages = {"com.mszx.hyb"})
public class UmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }

}
