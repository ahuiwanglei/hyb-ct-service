package com.mszx.hyb.ordercenter.params;

import com.mszx.hyb.common.model.Result;
import com.mszx.hyb.common.util.ResultFactory;
import com.mszx.hyb.common.util.Strings;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderParams {

    private String appid;
    private String pkmuser;
    private String pkmuser_name;
    private String userid;
    private Integer order_type =0;
    private String order_title;
    private BigDecimal discounted_amount = new BigDecimal(0);
    private BigDecimal amount;
    private BigDecimal real_pay_amount;
    private Integer pay_type;
    private BigDecimal offline_pay_amount;
    private Integer outid;

    private  String barcode;


    List<GoodsParams> goodsList;

    GoodsReceiptInfoParams goodsReceiptInfo;


    public Result<String> checkParams(){
        String result = null;
        if(Strings.isEmpty(appid)){
            result = "appid 不能为空";
        }
        if(Strings.isEmpty(pkmuser)){
            result = "pkmuser 不能为空";
        }
        if(Strings.isEmpty(pkmuser_name)){
            result = "pkmuser_name 不能为空";
        }
        if(Strings.isEmpty(userid)){
            result = "userid 不能为空";
        }
        if(Strings.isEmpty(order_title)){
            result = "order_title 不能为空";
        }
        if(amount == null){
            result = "amount 不能为空";
        }
        if(amount == null){
            result = "real_pay_amount 不能为空";
        }
        if(goodsList == null || goodsList.size() == 0){
            result = "商品不能为空";
        }
        if(goodsList != null){
            for (int i=0;i< goodsList.size();i++){
                result = goodsList.get(i).checkParams();
                if(Strings.isNotNullOrEmpty(result)){
                    break;
                }
            }
        }

        if(Strings.isEmpty(result)){
            return ResultFactory.getSuccessResult("验证成功");
        }else{
            return ResultFactory.getErrorResult(result);
        }

    }
}
