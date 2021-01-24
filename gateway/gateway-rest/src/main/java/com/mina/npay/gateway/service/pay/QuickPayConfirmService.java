package com.mina.npay.gateway.service.pay;

import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayConfirmReq;
import com.mina.npay.gateway.dto.resp.QuickPayResp;

/**
 * 快捷支付确认接口
 * @author daimingdong
 */
public interface QuickPayConfirmService {
    /**
     * 支付确认
     * @param req
     * @return
     */
    public ResultBase<QuickPayResp> payConfirm(QuickPayConfirmReq req);
}
