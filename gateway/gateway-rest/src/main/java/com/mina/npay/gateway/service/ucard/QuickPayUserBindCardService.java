package com.mina.npay.gateway.service.ucard;

import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayUserCardReq;
import com.mina.npay.gateway.dto.resp.UserBindResp;

/**
 * 快捷支付用户绑卡接口
 * @author daimingdong
 */
public interface QuickPayUserBindCardService {

    /**
     * 快捷支付绑卡方法
     * @param req
     * @return
     */
    public ResultBase<UserBindResp> userBindCard(QuickPayUserCardReq req);
}
