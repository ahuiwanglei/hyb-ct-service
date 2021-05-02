package com.mszx.hyb.ordercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ServletComponentScan   //扫描Servlet
@MapperScan(basePackages = {"com.mszx.hyb.ordercenter.dao", "com.mszx.hyb.common"})
@ComponentScan(basePackages = {"com.mszx.hyb"})
public class OrderCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderCenterApplication.class, args);
    }

}
