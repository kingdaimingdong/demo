package com.mina.npay.gateway.service.acc;

/**
 * 清结算服务接口
 * @author daimingdong
 */
public interface AccFacadeService {

    /**
     * 创建账户
     * @param uid
     * @param name
     * @param type
     * @param currency
     */
    public void accCreate(String uid,String name,Integer type,String[]currency);

    /**
     * 支付记账
     * @param mid
     * @param biz
     * @param sid
     * @param uid
     * @param amount
     * @param currency
     */
    public void payAcc(String mid,String biz,String sid,String uid, String amount,String currency);

    /**
     * 退款记账
     * @param mid
     * @param origSerial
     * @param biz
     * @param sid
     * @param uid
     * @param amount
     * @param currency
     */
    public void refundAcc(String mid,String origSerial,String biz,String sid,String uid,String amount,String currency);

}
