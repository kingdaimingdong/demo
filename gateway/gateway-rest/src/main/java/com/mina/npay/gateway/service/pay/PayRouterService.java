package com.mina.npay.gateway.service.pay;

/**
 * 支付路由服务接口
 * @author daimingdong
 */
public interface PayRouterService {
    /**
     * 支付路由算法,返回接口信息
     * @param channelId
     * @return
     */
    public String routing(String channelId,String policy);
}
