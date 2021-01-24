package com.mina.npay.gateway.service.order;

import com.mina.npay.gateway.dto.base.BaseReq;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.resp.OrderQueryResp;

/**
 * 订单服务接口
 * @author daimingdong
 */
public interface OrderService {

    /**
     * 订单查询
     * @param req
     * @return
     */
    public ResultBase<OrderQueryResp> query(BaseReq req);
}
