package com.mszx.hyb.service;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.entity.BalanceParams;
import com.mszx.hyb.entity.HybRegister;

public interface IBalanceAction {
    Result updateBalance(BalanceParams balanceParams);

    public Result createBalance(HybRegister hybRegister);
}
