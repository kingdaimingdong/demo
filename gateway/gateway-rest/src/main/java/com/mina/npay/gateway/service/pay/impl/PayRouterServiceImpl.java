package com.mina.npay.gateway.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.AmountUtils;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.*;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.pay.PayRouterService;
import com.mina.npay.gateway.service.pay.PayValidtorService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支付路由实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class PayRouterServiceImpl implements PayRouterService {

    @Autowired
    private IGwChannelInfService iGwChannelInfService;

    @Autowired
    private IGwChannelInfTcService iGwChannelInfTcService;

    @Autowired
    private IGwServService iGwServService;

    @Autowired
    private IGwInterfaceService iGwInterfaceService;

    @Autowired
    private PayValidtorService payValidtorService;

    @Override
    public String routing(String channelId,String amount){

        GwChannel channel = payValidtorService.channelValid(channelId);


        boolean channelFlag = false;
        List<GwChannelInf> ciList = new ArrayList<GwChannelInf>();
        //获取通道接口列表
        QueryWrapper<GwChannelInf> queryChannelInf = new QueryWrapper<>();
        queryChannelInf.eq("cid",channelId);
        List<GwChannelInf> channelInfList = iGwChannelInfService.list(queryChannelInf);
        if(channelInfList != null && !channelInfList.isEmpty()){
            Iterator<GwChannelInf> ciit = channelInfList.iterator();
            while(ciit.hasNext()){
                GwChannelInf channelInf = ciit.next();
                if(!GwChannelInfStatusEnum.OPEN.getCode().equals(channelInf.getStatus())){
                    log.info("channel inf is not open! channelId:{},infId:{}",channelId,channelInf.getInfId());
                    continue;
                }

                //校验接口
                GwInterface gwInterface = iGwInterfaceService.getById(channelInf.getInfId());
                if(gwInterface == null){
                    log.info("inf info is not exist! infId:{}",channelInf.getInfId());
                    continue;
                }

                if(!GwInterfaceStatusEnum.OPEN.getCode().equals(gwInterface.getStatus())){
                    log.info("interface status is not open! infId:{}",channelInf.getInfId());
                    continue;
                }

                if(AmountUtils.compare(amount,AmountUtils.bigDecimalToString(gwInterface.getAmtMin())) < 0){
                    log.info("interface amount less amt_min  amount:{},amt_minL{}",amount,AmountUtils.bigDecimalToString(gwInterface.getAmtMin()));
                    continue;
                }

                if(gwInterface.getAmtMax() != null) {
                    if (AmountUtils.compare(amount, AmountUtils.bigDecimalToString(gwInterface.getAmtMax())) > 0) {
                        log.info("interface amount great amt_max  amount:{},amt_minL{}", amount, AmountUtils.bigDecimalToString(gwInterface.getAmtMax()));
                        continue;
                    }
                }

                if(gwInterface.getContractExpire() != null){
                    if(DateUtils.getLocalDateTimeNow().isAfter(gwInterface.getContractExpire())){
                        log.info("interface contract is expired !LocaleDate:{},contractDate:{}",DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS),DateUtils.format(gwInterface.getContractExpire(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                        continue;
                    }
                }

                if(gwInterface.getCertExpire() != null){
                    if(DateUtils.getLocalDateTimeNow().isAfter(gwInterface.getContractExpire())){
                        log.info("interface cert is expired !LocaleDate:{},contractDate:{}",DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS),DateUtils.format(gwInterface.getCertExpire(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                        continue;
                    }
                }

                //获取接口服务商关联关系
                QueryWrapper<GwChannelInfTc> queryChannelInfTc = new QueryWrapper<>();
                queryChannelInfTc
                        .eq("cid",channelId)
                        .eq("inf_id",channelInf.getInfId());
                GwChannelInfTc gwChannelInfTc = iGwChannelInfTcService.getOne(queryChannelInfTc);
                if(gwChannelInfTc == null){
                    log.info("inf serv is empty! channelId:{},infId:{}",channelId,channelInf.getInfId());
                    continue;
                }

                if(!GwChannelInfTCStatusEnum.OPEN.getCode().equals(gwChannelInfTc.getStatus())){
                    log.info("inf serv is not open! channelId:{},infId:{},servId:{}",channelId,gwChannelInfTc.getInfId(),gwChannelInfTc.getSprCid());
                    continue;
                }

                //校验服务商
                GwServ serv = iGwServService.getById(gwChannelInfTc.getSprCid());
                if(serv == null){
                    log.info("serv is empty! servId:{}",gwChannelInfTc.getSprCid());
                    continue;
                }

                if(!GwServStatusEnum.OPEN.getCode().equals(serv.getStatus())){
                    log.info("serv is is not open! servId:{}",gwChannelInfTc.getSprCid());
                    continue;
                }

                CronExpression cronExpression = null;
                try {
                    cronExpression = new CronExpression(serv.getActiveTime());
                }catch (Exception e){
                    log.info("CronExpression(serv.getActiveTime()) is error servId:{},activeCron:{},localDate:{}! ",serv.getId(),serv.getActiveTime(),DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                    throw new BusinessException(RespCodeTypeEnum.UNKNOWN_ERROR);
                }

                boolean resCron = cronExpression.isSatisfiedBy(DateUtils.localDateTimeToDateConverter(LocalDateTime.now()));
                if(resCron == false){
                    log.info("serv  is not satisfy servId:{},activeCron:{},localDate:{}! ",serv.getId(),serv.getActiveTime(),DateUtils.format(DateUtils.getLocalDateTimeNow(),DateUtils.YYYY_MM_DD_HH_MM_SS));
                    continue;
                }

                ciList.add(channelInf);
                channelFlag = true;
            }

        }else{
            log.error("channel inf list is empty! channelId:{}",channelId);
            throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_NOT_EXIST);
        }

        if(channelFlag == false){
            log.error("all channel inf serv condition not Satisfy! channelId:{}",channelId);
            throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_SERV_CONDITION_NOT_SATISFY);
        }

        List<GwChannelInf> sortedChannel = ciList.stream().sorted(Comparator.comparing(GwChannelInf::getPriority)).collect(Collectors.toList());
        return  sortedChannel.get(0).getInfId();
    }
}
