package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.base.BaseReq;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.resp.OrderQueryResp;
import com.mina.npay.gateway.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单controller
 * @author daimingodng
 */

@RestController
@Api(description = "订单接口服务",tags = "order")
@RequestMapping(value = "/npay")
@Slf4j
public class OrderCtl {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order")
    @ApiOperation(value = "订单查询", notes = "订单查询方法")
    public ResultBase<OrderQueryResp> orderQuery(BaseReq req) {

        log.info("orderQuery request params:{}",GsonUtils.toJson(req));

        QuickPayCheck.checkCommonParam(req);

        ResultBase<OrderQueryResp> res = orderService.query(req);

        return res;
    }
}
