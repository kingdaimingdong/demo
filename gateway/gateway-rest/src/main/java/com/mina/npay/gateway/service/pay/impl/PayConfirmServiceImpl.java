package com.mina.npay.gateway.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.SpringUtils;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.*;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.pay.PayConfirmService;
import com.mina.npay.gateway.service.pay.PayFacadeService;
import com.mina.npay.gateway.service.pay.PayMerchantValidtorSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付确认实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class PayConfirmServiceImpl implements PayConfirmService {

    @Autowired
    private PayMerchantValidtorSerivce payMerchantValidtorSerivce;

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Autowired
    private IGwBankService iGwBankService;

    @Autowired
    private IGwChannelInfService iGwChannelInfService;

    @Autowired
    private IGwChannelInfTcService iGwChannelInfTcService;

    @Autowired
    private IGwServService iGwServService;

    @Autowired
    private IGwInterfaceService iGwInterfaceService;

    @Override
    public String comfirm(QuickPayReq req, String cid,String bindId,String bizType){


        if(GwBizTypeEnum.QUICK_PAY.getCode().equals(bizType)) {

            // 开始进行商户业务校验
            payMerchantValidtorSerivce.valid(req.getMid(), req.getUid(), GwBizTypeEnum.QUICK_PAY.getCode());


            //校验订单
            QueryWrapper<GwConsumeOrder> queryConsumeOrder = new QueryWrapper<>();
            queryConsumeOrder
                    .eq("mid", req.getMid())
                    .eq("mer_serial", req.getSerial());
            GwConsumeOrder consumeOrder = iGwConsumeOrderService.getOne(queryConsumeOrder);
            if (consumeOrder == null) {
                log.error("consume order is not exist ! mid:{},serial:{}", req.getMid(), req.getServNotice());
                throw new BusinessException(RespCodeTypeEnum.ORDER_NOT_EXIST);
            }

            //校验通道
            GwChannel channel = iGwChannelService.getById(cid);
            if(channel == null){
                log.error("channel info is not exist! channelId:{}",cid);
                throw new BusinessException(RespCodeTypeEnum.CHANNEL_NOT_EXIST);
            }

            //校验通道状态
            if(!GwChannelStatusEnum.OPEN.getCode().equals(channel.getStatus())){
                log.error("channel is not open! channelId:{}",cid);
                throw new BusinessException(RespCodeTypeEnum.CHANNEL_STATUS_NOT_OPEN);
            }

            //校验绑卡信息
            GwBindCard gwBindCard = iGwBindCardService.getById(bindId);
            if(gwBindCard == null){
                log.error("bind card info is not exist! bindId:{}",bindId);
                throw new BusinessException(RespCodeTypeEnum.BIND_CARD_NOT_EXIST);
            }

            //校验用户绑卡状态
            if(!GwBindCardEnum.BINDED.getCode().equals(gwBindCard.getStatus())){
                log.error("bind card is not open! bindId:{}",bindId);
                throw new BusinessException(RespCodeTypeEnum.BIND_CARD_STATUS_NOT_OPEN);
            }

            //校验银行
            GwBank bank = iGwBankService.getById(gwBindCard.getBankId());
            if(bank == null){
                log.error("bank info is not exist! bankId:{}",gwBindCard.getBankId());
                throw new BusinessException(RespCodeTypeEnum.BANK_NOT_EXIST);
            }

            if(!GwBankStatusEnum.OPEN.getCode().equals(bank.getStatus())){
                log.error("bank status is not open! bankId:{}",gwBindCard.getBankId());
                throw new BusinessException(RespCodeTypeEnum.BANK_STATUS_NOT_OPEN);
            }

            //校验接口
            GwInterface gwInterface = iGwInterfaceService.getById(gwBindCard.getInfId());
            if(gwInterface == null){
                log.info("inf info is not exist! infId:{}",gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.INF_NOT_EXIST);
            }

            if(!GwInterfaceStatusEnum.OPEN.getCode().equals(gwBindCard.getInfId())){
                log.error("interface status is not open! infId:{}",gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.INF_SERV_STATUS_NOT_OPEN);
            }

            //校验通道接口
            QueryWrapper<GwChannelInf> queryChannelInf = new QueryWrapper<>();
            queryChannelInf
                    .eq("cid",cid)
                    .eq("inf_id",gwBindCard.getInfId());
            GwChannelInf channelInf = iGwChannelInfService.getOne(queryChannelInf);
            if(channelInf == null){
                log.info("channel inf info is not exist! infId:{}",gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_NOT_EXIST);
            }

            if(!GwChannelInfStatusEnum.OPEN.getCode().equals(channelInf.getStatus())){
                log.info("channel inf is not open! infId:{}",gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_NOT_EXIST);
            }

            //校验接口服务商
            QueryWrapper<GwChannelInfTc> queryChannelInfTc = new QueryWrapper<>();
            queryChannelInfTc
                    .eq("cid",cid)
                    .eq("inf_id",gwBindCard.getInfId());
            GwChannelInfTc channelInfTc = iGwChannelInfTcService.getOne(queryChannelInfTc);
            if(channelInfTc == null){
                log.info("inf serv info is not exist! cid:{},infId:{}",cid,gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.INF_SERV_NOT_EXIST);
            }

            if(!GwChannelInfTCStatusEnum.OPEN.getCode().equals(channelInfTc.getStatus())){
                log.error("inf serv status is not open! cid:{},infId:{}",cid,gwBindCard.getInfId());
                throw new BusinessException(RespCodeTypeEnum.INF_SERV_STATUS_NOT_OPEN);
            }

            GwServ serv = iGwServService.getById(gwBindCard.getSprBindNo());
            if(serv == null){
                log.info("serv is empty! servId:{}",gwBindCard.getSprBindNo());
                throw new BusinessException(RespCodeTypeEnum.SERV_NOT_EXIST);
            }

            if(!GwServStatusEnum.OPEN.getCode().equals(serv.getStatus())){
                log.info("serv is is not open! servId:{}",gwBindCard.getSprBindNo());
                throw new BusinessException(RespCodeTypeEnum.SERV_STATUS_NOT_OPEN);
            }

            //获取协议类并完成支付
            String bean = gwInterface.getBean();
            Class<?> class1 = null;
            try {
                class1 = Class.forName(bean);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PayFacadeService payFacadeService = (PayFacadeService)SpringUtils.getBean(class1);
//            payFacadeService.pay(consumeOrder.getId());
        }


        return null;

    }

}
