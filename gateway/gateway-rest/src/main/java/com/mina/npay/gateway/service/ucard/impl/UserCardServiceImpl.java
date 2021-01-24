package com.mina.npay.gateway.service.ucard.impl;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.GwBankStatusEnum;
import com.mina.npay.gateway.enums.GwBindCardEnum;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.pay.PayMerchantValidtorSerivce;
import com.mina.npay.gateway.service.pay.PayRouterService;
import com.mina.npay.gateway.service.ucard.UserCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户绑卡实现类
 * @author daimingdong
 */
@Service
@Slf4j
public class UserCardServiceImpl implements UserCardService {

    @Autowired
    private PayMerchantValidtorSerivce payMerchantValidtorSerivce;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Autowired
    private IGwMerUsrService iGwMerUsrService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Autowired
    private PayRouterService payRouterService;

    @Autowired
    private IGwChannelInfTcService iGwChannelInfTcService;

    @Autowired
    private IGwBankService iGwBankService;

    @Override
    public String userBindCard(QuickPayReq req, String channelId, String cardNo, String name, String phoneNo){

        // 开始进行商户业务校验
        payMerchantValidtorSerivce.valid(req.getMid(),req.getUid(), GwBizTypeEnum.QUICK_PAY.getCode());

        GwChannel channel = iGwChannelService.getById(channelId);
        if(channel == null){
            log.error("channel info is not exist!");
            throw new BusinessException(RespCodeTypeEnum.CHANNEL_NOT_EXIST);
        }

        GwBank bank = iGwBankService.getById(channel.getBankId());
        if(bank == null){
            log.error("bank info is not exist! bankId:{}",channel.getBankId());
            throw new BusinessException(RespCodeTypeEnum.BANK_NOT_EXIST);
        }

        if(!GwBankStatusEnum.OPEN.getCode().equals(bank.getStatus())){
            log.error("bank status is not open! bankId:{}",channel.getBankId());
            throw new BusinessException(RespCodeTypeEnum.BANK_STATUS_NOT_OPEN);
        }


        QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
        queryMerUsrWrapper
                .eq("mid",req.getMid())
                .eq("mer_uid",req.getUid());
        GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);


        QueryWrapper<GwBindCard> queryBindCardWrapper = new QueryWrapper<>();
        queryBindCardWrapper.eq("cid",gwMerUsr.getUid());
        queryBindCardWrapper.eq("no",cardNo);
        GwBindCard gwBindCard = iGwBindCardService.getOne(queryBindCardWrapper);
        if(gwBindCard == null){

            GwBindCard bindCard = new GwBindCard();
            bindCard.setCid(gwMerUsr.getUid());
            bindCard.setNo(cardNo);
            bindCard.setName(name);
            bindCard.setMobile(phoneNo);
            bindCard.setBankId(channel.getBankId());
            bindCard.setStatus(GwBindCardEnum.BINDING.getCode());
            bindCard.setUnbindTs(LocalDateTime.now());
            bindCard.setId(String.valueOf(snowFlakeUtils.nextId()));

            boolean res = iGwBindCardService.save(bindCard);

            gwBindCard = bindCard;

        }

        String infId = payRouterService.routing(channelId,"defalut");

        QueryWrapper<GwChannelInfTc> queryChannelInfTc = new QueryWrapper<>();
        queryChannelInfTc
                .eq("cid",channelId)
                .eq("inf_id",infId);
        GwChannelInfTc gwChannelInfTc = iGwChannelInfTcService.getOne(queryChannelInfTc);

        gwBindCard.setInfId(infId);
        gwBindCard.setSprBindNo(gwChannelInfTc.getSprCid());
        UpdateWrapper<GwBindCard> updateBindCardWrapper = new UpdateWrapper<>();
        updateBindCardWrapper
                .eq("cid",gwMerUsr.getUid())
                .eq("no",cardNo);
        boolean updateRes = iGwBindCardService.update(gwBindCard,updateBindCardWrapper);

        ResultBase<String> res = new ResultBase<>();
        res.setCode(RespCodeTypeEnum.SUCCESS.getCode());
        res.setMessage(RespCodeTypeEnum.SUCCESS.getMessage());
        res.setMid(req.getMid());
        res.setSerial(req.getSerial());
        res.setSign(req.getSign());
        res.setData("success");

        return GsonUtils.toJson(res);
    }
}
