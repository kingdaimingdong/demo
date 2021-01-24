package com.mina.npay.gateway.service.pay;

/**
 * 统一支付接口
 * @author daimingdong
 */
public interface PayFacadeService {

    /**
     * 支付方法
     * @param mid
     * @param merSerial
     * @param orderId
     * @return
     */
    public boolean pay(String mid,String merSerial,String orderId);
}
