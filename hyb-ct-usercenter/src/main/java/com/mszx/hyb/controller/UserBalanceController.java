package com.mszx.hyb.controller;

import com.mszx.hyb.common.base.BaseController;
import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.entity.BalanceParams;
import com.mszx.hyb.param.UserInfo;
import com.mszx.hyb.service.IBalanceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_balance/")
public class UserBalanceController  extends BaseController {

    @Autowired
    IBalanceAction balanceAction;

    @PostMapping(value = "updateBalance", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result updateBalance(@ModelAttribute BalanceParams balanceParams) {
        return balanceAction.updateBalance(balanceParams);
    }


}
