package com.mina.npay.gateway.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mina.npay.gateway.common.utils.AmountUtils;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import com.mina.npay.gateway.common.utils.SpringUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayConfirmReq;
import com.mina.npay.gateway.dto.resp.QuickPayResp;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.GwConsumeOrderStatusEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.IGwBindCardService;
import com.mina.npay.gateway.service.IGwConsumeOrderService;
import com.mina.npay.gateway.service.IGwInterfaceService;
import com.mina.npay.gateway.service.pay.PayFacadeService;
import com.mina.npay.gateway.service.pay.PayValidtorService;
import com.mina.npay.gateway.service.pay.QuickPayConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 快捷支付确认服务实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class QuickPayConfirmServiceImpl implements QuickPayConfirmService {

    @Autowired
    private PayValidtorService payValidtorService;

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Autowired
    private IGwInterfaceService iGwInterfaceService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Override
    public ResultBase<QuickPayResp> payConfirm(QuickPayConfirmReq req){

        bizCheck(req);

        ResultBase<QuickPayResp> res = payFacade(req);

        return res;
    }

    private ResultBase<QuickPayResp> payFacade(QuickPayConfirmReq req){
        QueryWrapper<GwConsumeOrder> queryConsumeOrder = new QueryWrapper<>();
        queryConsumeOrder
                .eq("mid", req.getMid())
                .eq("mer_serial", req.getSerial());
        GwConsumeOrder consumeOrder = iGwConsumeOrderService.getOne(queryConsumeOrder);

        GwBindCard gwBindCard = iGwBindCardService.getById(req.getBindId());

        GwInterface gwInterface = iGwInterfaceService.getById(gwBindCard.getInfId());

        consumeOrder.setBankId(gwBindCard.getBankId());
        consumeOrder.setCid(req.getChannelId());
        consumeOrder.setInfId(gwBindCard.getInfId());
        consumeOrder.setBcId(gwBindCard.getId());
        consumeOrder.setPcsSerial(String.valueOf(snowFlakeUtils.nextId()));
        consumeOrder.setPcsReqTs(DateUtils.getLocalDateTimeNow());
        UpdateWrapper<GwConsumeOrder> updateConsumeOrder = new UpdateWrapper<>();
        updateConsumeOrder
                .eq("mid", req.getMid())
                .eq("mer_serial", req.getSerial());
        consumeOrder.setStatus(GwConsumeOrderStatusEnum.PROCESSING.getCode());
        iGwConsumeOrderService.update(consumeOrder,updateConsumeOrder);


        //获取协议类并完成支付 attention: class must be absolute path
        //String bean = "com.mina.npay.gateway.service.protocol.PayTestBankFacadeServiceImpl";
        String bean = gwInterface.getBean();
        if(StringUtils.isEmpty(bean)){
            log.error("protocol bean is not exist! infId:{}",gwBindCard.getInfId());
            throw new BusinessException(RespCodeTypeEnum.BEAN_NOT_EXIST);
        }

        Class<?> class1 = null;
        try {
            class1 = Class.forName(bean);
        } catch (ClassNotFoundException e) {
            log.error("get protocol bean exception bean name:{}",bean,e);
            throw new BusinessException(RespCodeTypeEnum.BEAN_TRANSFER_EXCEPTION);
        }

        PayFacadeService payFacadeService = (PayFacadeService) SpringUtils.getBean(class1);

        payFacadeService.pay(req.getMid(),req.getSerial(),consumeOrder.getId());

        return prepareResult(req,consumeOrder.getId());

    }

    private ResultBase<QuickPayResp> prepareResult(QuickPayConfirmReq req,String orderId){

        QueryWrapper<GwConsumeOrder> queryConsumeOrder = new QueryWrapper<>();
        queryConsumeOrder
                .eq("mid", req.getMid())
                .eq("mer_serial", req.getSerial());
        GwConsumeOrder consumeOrder = iGwConsumeOrderService.getOne(queryConsumeOrder);

        QuickPayResp quickPayResp = new QuickPayResp();
        quickPayResp.setGwSerial(consumeOrder.getMerSerial());
        quickPayResp.setReserved(consumeOrder.getMerReserved());
        quickPayResp.setStatus(consumeOrder.getStatus());
        quickPayResp.setAmount(AmountUtils.bigDecimalToString(consumeOrder.getAmount()));
        quickPayResp.setCurrency("迪拉姆");
        quickPayResp.setFinishTs(DateUtils.localDateTimeToInstantConverter(DateUtils.getLocalDateTimeNow()).getEpochSecond());


        ResultBase<QuickPayResp> res = new ResultBase<>();
        res.setCode(RespCodeTypeEnum.SUCCESS.getCode());
        res.setMessage(RespCodeTypeEnum.SUCCESS.getMessage());
        res.setMid(req.getMid());
        res.setSerial(req.getSerial());
        res.setSign(req.getSign());
        res.setData(quickPayResp);

        return res;
    }

    private void bizCheck(QuickPayConfirmReq req) {

        GwMerchant gwMerchant = payValidtorService.merchantValid(req.getMid());

        GwMerUsr gwMerUsr = payValidtorService.merUsrValid(req.getMid(),req.getUid());

        GwUsr gwUsr = payValidtorService.usrValid(gwMerUsr.getUid());

        GwMerBiz gwMerBiz = payValidtorService.merBizValid(req.getMid(),GwBizTypeEnum.QUICK_PAY.getCode());

        GwBiz gwBiz = payValidtorService.bizValid(GwBizTypeEnum.QUICK_PAY.getCode());

        GwConsumeOrder gwConsumeOrder = payValidtorService.consumeOrderValid(req.getMid(),req.getSerial());

        GwChannel gwChannel = payValidtorService.channelValid(req.getChannelId());

        GwBindCard gwBindCard = payValidtorService.bindCardValid(req.getBindId());

        GwBank gwBank = payValidtorService.bankValid(gwBindCard.getBankId());

        GwInterface gwInterface = payValidtorService.interfaceValid(gwBindCard.getInfId(),req.getAmount());

        GwChannelInf gwChannelInf = payValidtorService.channelInfValid(req.getChannelId(),gwBindCard.getInfId());

        GwChannelInfTc gwChannelInfTc = payValidtorService.channelInfTcValid(req.getChannelId(),gwBindCard.getInfId());

        GwServ gwServ = payValidtorService.servValid(gwBindCard.getSprBindNo());

    }
}
