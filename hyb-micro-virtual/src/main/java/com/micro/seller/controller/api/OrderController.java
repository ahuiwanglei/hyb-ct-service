package com.micro.seller.controller.api;

import com.micro.seller.service.VirtualIncomeDataService;
import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/")
public class OrderController extends BaseController {

    @Autowired
    VirtualIncomeDataService virtualIncomeDataService;

    @PostMapping(value = "/findUserById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void buyBalance(){

    }

    @GetMapping("balance")
    public Result getBalance(){
        virtualIncomeDataService.getData();
        return ResultFactory.getSuccessResult("统计完成");
    }
}
