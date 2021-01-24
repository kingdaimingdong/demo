package com.mina.npay.gateway.service.pay;


/**
 * 支付商户校验器接口
 * @author daimingdong
 */
public interface PayMerchantValidtorSerivce {
    /**
     * 商户校验
     */
    public void valid(String mid,String uid,String bizCode);
}
