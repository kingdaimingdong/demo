package com.mina.npay.gateway.service.ucard.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.dto.req.QuickPayUserCardReq;
import com.mina.npay.gateway.dto.resp.UserBindResp;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.GwBindCardEnum;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.IGwBindCardService;
import com.mina.npay.gateway.service.IGwChannelInfTcService;
import com.mina.npay.gateway.service.IGwChannelService;
import com.mina.npay.gateway.service.IGwMerUsrService;
import com.mina.npay.gateway.service.pay.PayRouterService;
import com.mina.npay.gateway.service.pay.PayValidtorService;
import com.mina.npay.gateway.service.ucard.QuickPayUserBindCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 快捷支付绑卡实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class QuickPayUserBindCardServiceImpl implements QuickPayUserBindCardService {

    @Autowired
    private PayValidtorService payValidtorService;

    @Autowired
    private PayRouterService payRouterService;

    @Autowired
    private IGwMerUsrService iGwMerUsrService;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Autowired
    private IGwChannelInfTcService iGwChannelInfTcService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Override
    public ResultBase<UserBindResp> userBindCard(QuickPayUserCardReq req){

        bizCheck(req);

        ResultBase<UserBindResp> res = userBind(req);

        return res;
    }

    private ResultBase<UserBindResp> prepareResult(QuickPayUserCardReq req,String uid,String bankNo){

        QueryWrapper<GwBindCard> queryBindCardWrapper = new QueryWrapper<>();
        queryBindCardWrapper.eq("cid",uid);
        queryBindCardWrapper.eq("no",bankNo);
        GwBindCard gwBindCard = iGwBindCardService.getOne(queryBindCardWrapper);

        UserBindResp userBindResp = new UserBindResp();
        userBindResp.setChannelId(req.getChannelId());
        userBindResp.setBindId(gwBindCard.getId());
        userBindResp.setStatus(gwBindCard.getStatus());
        userBindResp.setFinishTs(DateUtils.localDateTimeToInstantConverter(DateUtils.getLocalDateTimeNow()).getEpochSecond());

        ResultBase<UserBindResp> res = new ResultBase<>();
        res.setCode(RespCodeTypeEnum.SUCCESS.getCode());
        res.setMessage(RespCodeTypeEnum.SUCCESS.getMessage());
        res.setMid(req.getMid());
        res.setSerial(req.getSerial());
        res.setSign(req.getSign());
        res.setData(userBindResp);

        return res;
    }

    private ResultBase<UserBindResp> userBind(QuickPayUserCardReq req){

        GwChannel channel = iGwChannelService.getById(req.getChannelId());

        QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
        queryMerUsrWrapper
                .eq("mid",req.getMid())
                .eq("mer_uid",req.getUid());
        GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);


        QueryWrapper<GwBindCard> queryBindCardWrapper = new QueryWrapper<>();
        queryBindCardWrapper.eq("cid",gwMerUsr.getUid());
        queryBindCardWrapper.eq("no",req.getCardNo());
        GwBindCard gwBindCard = iGwBindCardService.getOne(queryBindCardWrapper);
        if(gwBindCard == null){

            GwBindCard bindCard = new GwBindCard();
            bindCard.setCid(gwMerUsr.getUid());
            bindCard.setNo(req.getCardNo());
            bindCard.setName(req.getName());
            bindCard.setMobile(req.getPhoneNo());
            bindCard.setBankId(channel.getBankId());
            bindCard.setStatus(GwBindCardEnum.BINDING.getCode());
            bindCard.setUnbindTs(LocalDateTime.now());
            bindCard.setId(String.valueOf(snowFlakeUtils.nextId()));

            iGwBindCardService.save(bindCard);

            gwBindCard = bindCard;

        }else{
            log.info("user has already bind card! bindId:{}",gwBindCard.getId());
            throw new BusinessException(RespCodeTypeEnum.USER_CARD_ALREADY_EXIST);
        }

        String infId = payRouterService.routing(req.getChannelId(),req.getAmount());

        QueryWrapper<GwChannelInfTc> queryChannelInfTc = new QueryWrapper<>();
        queryChannelInfTc
                .eq("cid",req.getChannelId())
                .eq("inf_id",infId);
        GwChannelInfTc gwChannelInfTc = iGwChannelInfTcService.getOne(queryChannelInfTc);

        gwBindCard.setInfId(infId);
        gwBindCard.setSprBindNo(gwChannelInfTc.getSprCid());
        gwBindCard.setStatus(GwBindCardEnum.BINDED.getCode());
        UpdateWrapper<GwBindCard> updateBindCardWrapper = new UpdateWrapper<>();
        updateBindCardWrapper
                .eq("cid",gwMerUsr.getUid())
                .eq("no",req.getCardNo());
        iGwBindCardService.update(gwBindCard,updateBindCardWrapper);

        return prepareResult(req,gwMerUsr.getUid(),req.getCardNo());
    }

    private void bizCheck(QuickPayUserCardReq req) {

        GwMerchant gwMerchant = payValidtorService.merchantValid(req.getMid());

        GwMerUsr merUsr = payValidtorService.merUsrValid(req.getMid(),req.getUid());

        GwUsr gwUsr = payValidtorService.usrValid(merUsr.getUid());

        GwMerBiz gwMerBiz = payValidtorService.merBizValid(req.getMid(), GwBizTypeEnum.QUICK_PAY.getCode());

        GwBiz gwBiz = payValidtorService.bizValid(GwBizTypeEnum.QUICK_PAY.getCode());

        GwChannel gwChannel = payValidtorService.channelValid(req.getChannelId());

        GwBank gwBank = payValidtorService.bankValid(gwChannel.getBankId());
    }
}
