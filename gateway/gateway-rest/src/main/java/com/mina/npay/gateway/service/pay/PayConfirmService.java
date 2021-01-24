package com.mina.npay.gateway.service.pay;

import com.mina.npay.gateway.dto.req.QuickPayReq;

/**
 * 支付确认服务接口
 * @author daimingdong
 */
public interface PayConfirmService {

    /**
     * 支付确认
     * @param req
     * @param cid
     * @param bindId
     * @param bizType
     * @return
     */
    public String comfirm(QuickPayReq req,String cid,String bindId,String bizType);
}
