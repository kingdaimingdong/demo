package com.mina.npay.gateway.service.pay;

import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.dto.resp.QuickPayResp;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * 快捷支付接口
 * @author daimingdong
 */
public interface QuickPayService {

    /**
     * 支付下单
     * @param preOrderReq
     * @return
     */
    public QuickPayResp payAdd(QuickPayReq preOrderReq);
}
