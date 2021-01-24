package com.mina.npay.gateway.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.AmountUtils;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.dto.base.BaseReq;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.resp.OrderQueryResp;
import com.mina.npay.gateway.entity.GwConsumeOrder;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.IGwConsumeOrderService;
import com.mina.npay.gateway.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Override
    public ResultBase<OrderQueryResp> query(BaseReq req){

        QueryWrapper<GwConsumeOrder> queryOrderWrapper = new QueryWrapper<>();
        queryOrderWrapper
                .eq("mid", req.getMid())
                .eq("mer_serial", req.getSerial());
        GwConsumeOrder order = iGwConsumeOrderService.getOne(queryOrderWrapper);

        if(order == null){
            log.error("order is not exist! mid:{},serial:{}",req.getMid(),req.getSerial());
            throw new BusinessException(RespCodeTypeEnum.ORDER_NOT_EXIST);
        }

        OrderQueryResp qrderQueryResp = new OrderQueryResp();
        qrderQueryResp.setGwSerial(req.getSerial());
        qrderQueryResp.setStatus(order.getStatus());
        qrderQueryResp.setAmount(AmountUtils.bigDecimalToString(order.getAmount()));
        qrderQueryResp.setCurrency("迪拉姆");
        qrderQueryResp.setFinishTs(DateUtils.localDateTimeToInstantConverter(DateUtils.getLocalDateTimeNow()).getEpochSecond());


        ResultBase<OrderQueryResp> res = new ResultBase<>();
        res.setSerial(req.getSerial());
        res.setMid(req.getMid());
        res.setSign(req.getSign());
        res.setCode(RespCodeTypeEnum.SUCCESS.getCode());
        res.setMessage(RespCodeTypeEnum.SUCCESS.getMessage());
        res.setData(qrderQueryResp);

        return res;

    }
}
