package com.mina.npay.gateway.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.AmountUtils;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.dto.resp.QuickPayResp;
import com.mina.npay.gateway.entity.GwChannel;
import com.mina.npay.gateway.entity.GwConsumeOrder;
import com.mina.npay.gateway.entity.GwMerchant;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.GwChannelStatusEnum;
import com.mina.npay.gateway.enums.GwConsumeOrderStatusEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.IGwChannelService;
import com.mina.npay.gateway.service.IGwConsumeOrderService;
import com.mina.npay.gateway.service.IGwMerchantService;
import com.mina.npay.gateway.service.pay.QuickPayService;
import com.mina.npay.gateway.service.pay.PayMerchantValidtorSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 快捷支付预下单接口实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class QuickPayServiceImpl implements QuickPayService {

    @Autowired
    private PayMerchantValidtorSerivce payMerchantValidtorSerivce;

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Autowired
    private IGwMerchantService iGwMerchantService;

    /**
     * 新增预下单
     * @param quickPayReq
     * @return
     */
    @Override
    public QuickPayResp payAdd(QuickPayReq quickPayReq){

        // 开始进行商户业务校验
        payMerchantValidtorSerivce.valid(quickPayReq.getMid(),quickPayReq.getUid(), GwBizTypeEnum.QUICK_PAY.getCode());

        QueryWrapper<GwConsumeOrder> queryOrderWrapper = new QueryWrapper<>();
        queryOrderWrapper
                .eq("mid",quickPayReq.getMid())
                .eq("mer_serial",quickPayReq.getSerial());
        GwConsumeOrder order = iGwConsumeOrderService.getOne(queryOrderWrapper);

        if(order == null){
            order = new GwConsumeOrder();
            order.setId(String.valueOf(snowFlakeUtils.nextId()));
            order.setMid(quickPayReq.getMid());
            order.setMerSerial(quickPayReq.getSerial());
            order.setMerReqTs(DateUtils.getLocalDateTimeNow());
            order.setAmount(AmountUtils.null2Zero(quickPayReq.getAmount()));
            order.setCurrency(quickPayReq.getCurrency());
            order.setRefundedAmt(AmountUtils.null2Zero(""));
            order.setPcsSerial(String.valueOf(snowFlakeUtils.nextId()));
            order.setStatus(GwConsumeOrderStatusEnum.INIT.getCode());

            iGwConsumeOrderService.save(order);
        }


        //校验商户信息是否存在
        GwMerchant gwMerchant = iGwMerchantService.getById(quickPayReq.getMid());
        if(gwMerchant == null){
            log.error("merchant info is not exist!");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_NOT_EXIST);
        }

        QueryWrapper<GwChannel> queryChannelWrapper = new QueryWrapper<>();
        queryChannelWrapper
                .eq("biz_id",GwBizTypeEnum.QUICK_PAY.getCode())
                .eq("status", GwChannelStatusEnum.OPEN.getCode());
        List<GwChannel> channelList = iGwChannelService.list(queryChannelWrapper);


        return null;
    }
}
