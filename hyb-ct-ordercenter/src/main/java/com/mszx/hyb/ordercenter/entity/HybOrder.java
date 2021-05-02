package com.mszx.hyb.ordercenter.entity;

import com.mszx.hyb.ordercenter.params.GoodsParams;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class HybOrder {

    //0: 优惠券购买 1:优惠券抵扣 2：扫码支付 3:砍价活动
    public enum ORDERTYPE{
        CardTicket(0,"卡券"),DiscountTicket(1,"优惠券抵扣"),ScanPay(2,"扫码支付"), Bargain(3,"砍价活动"), CrowdfundingBuy(4,"众筹购买"),DrivingSchool(5, "驾校报名") ;

        private int value;
        private String name;
        private ORDERTYPE(int value, String name){
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    //0:预下单 1:已支付 2:已出库 ，3：已完成 8：已取消  9:已删除
    public enum STATUS{
        PreOrder("预下单",0),
        PayOrder("已支付",1),
        Outbound("已出库",2),
        Finish("已完成",3),
        Canceled("已取消", 8),
        Deleted("已删除", 9);
        private String name;
        private int value;
        private STATUS(String name, int status){
            this.name = name;
            this.value = status;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 核销状态
     */
    public enum CheckConsumeStatus{
        NoCheck(0), Checked(1);
        private int value;
        private CheckConsumeStatus(int status){
            this.value = status;
        }

        public int getValue() {
            return value;
        }
    }


    private Integer orderid;
    private String ordernum;
    private String appid;
    private String pkmuser;
    private String pkmuser_name;
    private String userid;
    private Integer order_type;
    private String order_title;
    private Integer order_status;
    private BigDecimal discounted_amount;
    private BigDecimal amount;
    private BigDecimal real_pay_amount;
    private String review_user;
    private Integer is_ship;
    private Date receipt_at;
    private BigDecimal ship_price;
    private String ship_info;
    private Date pay_at;
    private Integer pay_type;
    private BigDecimal offline_pay_amount;
    private Date update_at;
    private Date create_at;
    private String barcode;
    private Integer check_consume_source;

    private String check_code;//核销码

    private Integer outid; //根据order_type 判断是什么的iD

    private String check_consume_user;
    private Integer check_consume_status;
    private Date check_consume_time;

    private String outOrderId;//微信 支付宝 订单好

    List<HybGoods> goodsList;

}
