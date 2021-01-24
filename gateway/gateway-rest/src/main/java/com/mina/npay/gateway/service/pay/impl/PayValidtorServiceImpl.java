package com.mina.npay.gateway.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.AmountUtils;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.*;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.pay.PayValidtorService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 支付校验实现类
 * @author  daimingdong
 */
@Slf4j
@Service
public class PayValidtorServiceImpl implements PayValidtorService {

    @Autowired
    private IGwMerchantService iGwMerchantService;

    @Autowired
    private IGwMerUsrService iGwMerUsrService;

    @Autowired
    private IGwUsrService iGwUsrService;

    @Autowired
    private IGwMerBizService iGwMerBizService;

    @Autowired
    private IGwBizService iGwBizService;

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Autowired
    private IGwBankService iGwBankService;

    @Autowired
    private IGwInterfaceService iGwInterfaceService;

    @Autowired
    private IGwChannelInfService iGwChannelInfService;

    @Autowired
    private IGwChannelInfTcService iGwChannelInfTcService;

    @Autowired
    private IGwServService iGwServService;

    @Override
    public GwMerchant merchantValid(String mid){
        Optional.ofNullable(mid).orElseThrow(() -> new RuntimeException("mid can not be null"));

        GwMerchant gwMerchant = iGwMerchantService.getById(mid);
        if(gwMerchant == null){
            log.error("merchant info is not exist! mid:{}",mid);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_NOT_EXIST);
        }

        //校验商户开通状态
        if(!GwMerchantStatusEnum.OPEN.getCode().equals(gwMerchant.getStatus())){
            log.error("merchant status is not open! mid:{}",mid);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_STATUS_NOT_OPEN);
        }

        //校验商户是否上线
        long durationDays = DateUtils.between(DateUtils.getLocalDateTimeNow(),gwMerchant.getUplineTs(), TimeUnit.DAYS);
        if(durationDays > 0){
            log.error("merchant is offline mid:{}",mid);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_OFFLINE);
        }

        return gwMerchant;
    }

    @Override
    public GwMerUsr merUsrValid(String mid,String merUid){
        Optional.ofNullable(mid).orElseThrow(() -> new RuntimeException("mid can not be null"));
        Optional.ofNullable(merUid).orElseThrow(() -> new RuntimeException("merUid can not be null"));
        QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
        queryMerUsrWrapper
                .eq("mid",mid)
                .eq("mer_uid",merUid);
        GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);

        if(gwMerUsr == null){
            log.error("merchant user info is not exist! mid:{},merUid:{}",mid,merUid);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_USR_NOT_EXIST);
        }

        return gwMerUsr;
    }


    @Override
    public GwUsr usrValid(String uid){
        Optional.ofNullable(uid).orElseThrow(() -> new RuntimeException("uid can not be null"));
        GwUsr gwUsr = iGwUsrService.getById(uid);

        if(gwUsr == null){
            log.error("usr is not open uid:{}",uid);
            throw new BusinessException(RespCodeTypeEnum.USER_NOT_EXIST);
        }

        if(!GwUsrStatusEnum.OPEN.getCode().equals(gwUsr.getStatus())){
            log.error("usr is not open uid:{}",uid);
            throw new BusinessException(RespCodeTypeEnum.USER_STATUS_NOT_OPEN);
        }

        return gwUsr;
    }

    @Override
    public GwMerBiz merBizValid(String mid, String bizCode){
        Optional.ofNullable(mid).orElseThrow(() -> new RuntimeException("mid can not be null"));
        Optional.ofNullable(bizCode).orElseThrow(() -> new RuntimeException("bizCode can not be null"));
        //校验商户业务信息是否存在
        QueryWrapper<GwMerBiz> queryMerBizWrapper = new QueryWrapper<>();
        queryMerBizWrapper
                .eq("mid",mid)
                .eq("biz_id", bizCode);
        GwMerBiz gwMerBiz = iGwMerBizService.getOne(queryMerBizWrapper);
        if(gwMerBiz == null){
            log.error("merchant biz info is not exist! mid:{},bizId:{}",mid,bizCode);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_NOT_EXIST);
        }

        //校验商户业务是否开通
        if(!GwMerchantBizStatusEnum.OPEN.getCode().equals(gwMerBiz.getStatus())){
            log.error("merchant biz is not openmid:{},bizId:{}",mid,bizCode);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_STATUS_NOT_OPEN);
        }

        //校验商户业务是否上线
        if(LocalDateTime.now().isBefore(gwMerBiz.getStime()) || LocalDateTime.now().isAfter(gwMerBiz.getEtime())){
            log.error("merchant biz is offline mid:{},bizId:{}",mid,bizCode);
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_OFFLINE);
        }

        return gwMerBiz;
    }


    @Override
    public GwBiz bizValid(String bizCode){
        Optional.ofNullable(bizCode).orElseThrow(() -> new RuntimeException("bizCode can not be null"));
        //校验业务线
        GwBiz gwBiz = iGwBizService.getById(bizCode);
        if(gwBiz == null){
            log.error("biz info is not exist! bizCode:{}",bizCode);
            throw new BusinessException(RespCodeTypeEnum.BIZ_NOT_EXIST);
        }

        //校验业务开通状态
        if(!GwBizStatusEnum.OPEN.getCode().equals(gwBiz.getStatus())){
            log.error("biz status is not open! bizCode:{}",bizCode);
            throw new BusinessException(RespCodeTypeEnum.BIZ_STATUS_NOT_OPEN);
        }

        return gwBiz;
    }

    @Override
    public GwConsumeOrder consumeOrderValid(String mid, String merSerial){
        Optional.ofNullable(mid).orElseThrow(() -> new RuntimeException("mid can not be null"));
        Optional.ofNullable(merSerial).orElseThrow(() -> new RuntimeException("merSerial can not be null"));
        //校验订单
        QueryWrapper<GwConsumeOrder> queryConsumeOrder = new QueryWrapper<>();
        queryConsumeOrder
                .eq("mid", mid)
                .eq("mer_serial", merSerial);
        GwConsumeOrder consumeOrder = iGwConsumeOrderService.getOne(queryConsumeOrder);
        if (consumeOrder == null) {
            log.error("consume order is not exist ! mid:{},serial:{}", mid, merSerial);
            throw new BusinessException(RespCodeTypeEnum.ORDER_NOT_EXIST);
        }

        return  consumeOrder;
    }

    @Override
    public GwChannel channelValid(String cid){
        Optional.ofNullable(cid).orElseThrow(() -> new RuntimeException("cid can not be null"));
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

        return channel;
    }

    @Override
    public GwBindCard bindCardValid(String bindId){
        Optional.ofNullable(bindId).orElseThrow(() -> new RuntimeException("bindId can not be null"));
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

        return gwBindCard;
    }

    @Override
    public GwBank bankValid(String bankId){
        Optional.ofNullable(bankId).orElseThrow(() -> new RuntimeException("bankId can not be null"));
        GwBank bank = iGwBankService.getById(bankId);
        if(bank == null){
            log.error("bank info is not exist! bankId:{}",bankId);
            throw new BusinessException(RespCodeTypeEnum.BANK_NOT_EXIST);
        }

        if(!GwBankStatusEnum.OPEN.getCode().equals(bank.getStatus())){
            log.error("bank status is not open! bankId:{}",bankId);
            throw new BusinessException(RespCodeTypeEnum.BANK_STATUS_NOT_OPEN);
        }

        return bank;
    }

    @Override
    public GwInterface interfaceValid(String infId ,String amount){
        Optional.ofNullable(infId).orElseThrow(() -> new RuntimeException("infId can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));

        GwInterface gwInterface = iGwInterfaceService.getById(infId);
        if(gwInterface == null){
            log.info("inf info is not exist! infId:{}",infId);
            throw new BusinessException(RespCodeTypeEnum.INF_NOT_EXIST);
        }

        if(!GwInterfaceStatusEnum.OPEN.getCode().equals(gwInterface.getStatus())){
            log.error("interface status is not open! infId:{}",infId);
            throw new BusinessException(RespCodeTypeEnum.INF_STATUS_NOT_OPEN);
        }

        if(AmountUtils.compare(amount,AmountUtils.bigDecimalToString(gwInterface.getAmtMin())) < 0){
            log.error("interface amount less amt_min  amount:{},amt_minL{}",amount,AmountUtils.bigDecimalToString(gwInterface.getAmtMin()));
            throw new BusinessException(RespCodeTypeEnum.AMOUNT_LESS_MIN_AMT);
        }

        if(gwInterface.getAmtMax() != null) {
            if (AmountUtils.compare(amount, AmountUtils.bigDecimalToString(gwInterface.getAmtMax())) > 0) {
                log.error("interface amount great amt_max  amount:{},amt_minL{}", amount, AmountUtils.bigDecimalToString(gwInterface.getAmtMax()));
                throw new BusinessException(RespCodeTypeEnum.AMOUNT_GREAT_MAX_AMT);
            }
        }

        if(gwInterface.getContractExpire() != null){
            if(DateUtils.getLocalDateTimeNow().isAfter(gwInterface.getContractExpire())){
                log.error("interface contract is expired !LocaleDate:{},contractDate:{}",DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS),DateUtils.format(gwInterface.getContractExpire(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                throw new BusinessException(RespCodeTypeEnum.INF_CONTRACT_EXPIRE);
            }
        }

        if(gwInterface.getCertExpire() != null){
            if(DateUtils.getLocalDateTimeNow().isAfter(gwInterface.getContractExpire())){
                log.error("interface cert is expired !LocaleDate:{},contractDate:{}",DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS),DateUtils.format(gwInterface.getCertExpire(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                throw new BusinessException(RespCodeTypeEnum.INF_CERT_EXPIRE);
            }
        }

        return gwInterface;
    }

    @Override
    public GwChannelInf channelInfValid(String cid,String infId){
        Optional.ofNullable(cid).orElseThrow(() -> new RuntimeException("cid can not be null"));
        Optional.ofNullable(infId).orElseThrow(() -> new RuntimeException("infId can not be null"));
        //校验通道接口
        QueryWrapper<GwChannelInf> queryChannelInf = new QueryWrapper<>();
        queryChannelInf
                .eq("cid",cid)
                .eq("inf_id",infId);
        GwChannelInf channelInf = iGwChannelInfService.getOne(queryChannelInf);
        if(channelInf == null){
            log.info("channel inf info is not exist! cid:{},infId:{}",cid,infId);
            throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_NOT_EXIST);
        }

        if(!GwChannelInfStatusEnum.OPEN.getCode().equals(channelInf.getStatus())){
            log.info("channel inf is not open! cid:{},infId:{}",cid,infId);
            throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_NOT_OPEN);
        }

        return channelInf;
    }

    @Override
    public GwChannelInfTc channelInfTcValid(String cid,String infId){
        Optional.ofNullable(cid).orElseThrow(() -> new RuntimeException("cid can not be null"));
        Optional.ofNullable(infId).orElseThrow(() -> new RuntimeException("infId can not be null"));
        //校验接口服务商
        QueryWrapper<GwChannelInfTc> queryChannelInfTc = new QueryWrapper<>();
        queryChannelInfTc
                .eq("cid",cid)
                .eq("inf_id",infId);
        GwChannelInfTc channelInfTc = iGwChannelInfTcService.getOne(queryChannelInfTc);
        if(channelInfTc == null){
            log.info("inf serv info is not exist! cid:{},infId:{}",cid,infId);
            throw new BusinessException(RespCodeTypeEnum.INF_SERV_NOT_EXIST);
        }

        if(!GwChannelInfTCStatusEnum.OPEN.getCode().equals(channelInfTc.getStatus())){
            log.error("inf serv status is not open! cid:{},infId:{}",cid,infId);
            throw new BusinessException(RespCodeTypeEnum.INF_SERV_STATUS_NOT_OPEN);
        }

        return channelInfTc;
    }


    @Override
    public GwServ servValid(String servId) {
        Optional.ofNullable(servId).orElseThrow(() -> new RuntimeException("servId can not be null"));
        GwServ serv = iGwServService.getById(servId);
        if(serv == null){
            log.info("serv is empty! servId:{}",servId);
            throw new BusinessException(RespCodeTypeEnum.SERV_NOT_EXIST);
        }

        if(!GwServStatusEnum.OPEN.getCode().equals(serv.getStatus())){
            log.info("serv is not open! servId:{}",servId);
            throw new BusinessException(RespCodeTypeEnum.SERV_STATUS_NOT_OPEN);
        }

        CronExpression cronExpression = null;
        try {
            cronExpression = new CronExpression(serv.getActiveTime());
        }catch (Exception e){
            log.info("CronExpression(serv.getActiveTime()) is error servId:{},activeCron:{},localDate:{}! ",servId,serv.getActiveTime(),DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS));
            throw new BusinessException(RespCodeTypeEnum.UNKNOWN_ERROR);
        }

        boolean resCron = cronExpression.isSatisfiedBy(DateUtils.localDateTimeToDateConverter(LocalDateTime.now()));
        if(resCron == false){
            log.info("serv  is not satisfy servId:{},activeCron:{},localDate:{}! ",servId,serv.getActiveTime(),DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS));
            throw new BusinessException(RespCodeTypeEnum.SERV_NOT_TRADE);
        }

        return serv;
    }

}
