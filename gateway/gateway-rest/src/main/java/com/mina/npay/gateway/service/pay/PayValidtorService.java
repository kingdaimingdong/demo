package com.mina.npay.gateway.service.pay;

import com.mina.npay.gateway.entity.*;

/**
 * 支付校验器服务接口
 * @author daimingdong
 */
public interface PayValidtorService {

    /**
     * 商户校验
     * @param mid
     * @return
     */
    public GwMerchant merchantValid(String mid);


    /**
     * 用户校验
     * @param uid
     * @return
     */
    public GwUsr usrValid(String uid);

    /**
     * 商户用户校验
     * @param mid
     * @param merUid
     * @return
     */
    public GwMerUsr merUsrValid(String mid,String merUid);


    /**
     * 校验商户业务
     * @param mid
     * @param bizCode
     * @return
     */
    public GwMerBiz merBizValid(String mid, String bizCode);

    /**
     * 校验业务
     * @param bizCode
     * @return
     */
    public GwBiz bizValid(String bizCode);

    /**
     * 校验订单
     * @param mid
     * @param merSerial
     * @return
     */
    public GwConsumeOrder consumeOrderValid(String mid,String merSerial);


    /**
     * 校验通道
     * @param cid
     * @return
     */
    public GwChannel channelValid(String cid);

    /**
     * 校验绑卡
     * @param bindId
     * @return
     */
    public GwBindCard bindCardValid(String bindId);

    /**
     * 校验银行
     * @param bankId
     * @return
     */
    public GwBank bankValid(String bankId);

    /**
     * 校验接口
     * @param infId
     * @return
     */
    public GwInterface interfaceValid(String infId,String amount);

    /**
     * 校验通道接口
     * @param cid
     * @param infId
     * @return
     */
    public GwChannelInf channelInfValid(String cid,String infId);

    /**
     * 校验接口服务商
     * @param cid
     * @param infId
     * @return
     */
    public GwChannelInfTc channelInfTcValid(String cid,String infId);

    /**
     * 校验服务商
     * @param servId
     * @return
     */
    public GwServ servValid(String servId) ;

}
