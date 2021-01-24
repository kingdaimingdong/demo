package com.mina.npay.gateway.service.channel.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.*;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayChannelReq;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.dto.resp.CardInfo;
import com.mina.npay.gateway.dto.resp.ChannelInfo;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.*;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.channel.ChannelService;
import com.mina.npay.gateway.service.pay.PayValidtorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 快捷通道服务实现类
 *
 * @author daimingdong
 */
@Slf4j
@Service
public class QjChannelServiceImpl implements ChannelService {

    @Autowired
    private PayValidtorService payValidtorService;

    @Autowired
    private IGwMerUsrService iGwMerUsrService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    @Autowired
    private IGwUsrService iGwUsrService;

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Autowired
    private IGwChannelService iGwChannelService;

    @Autowired
    private IGwBankService iGwBankService;

    @Autowired
    private IGwBindCardService iGwBindCardService;

    @Override
    public String getChannelList(String req) {

        QuickPayChannelReq channelReq = GsonUtils.fromJson(req, QuickPayChannelReq.class);

        QuickPayCheck.checkChannelParam(channelReq);

        bizCheck(channelReq);

        List<ChannelInfo> channelList = prepareChannelList(channelReq);

        String res = prepareResult(channelReq,channelList);

        return res;
    }

    private String prepareResult(QuickPayChannelReq quickPayReq,List<ChannelInfo> channelInfoList){
        ResultBase<List<ChannelInfo>> res = new ResultBase<List<ChannelInfo>>();
        res.setSerial(quickPayReq.getSerial());
        res.setMid(quickPayReq.getMid());
        res.setSign(quickPayReq.getSign());
        res.setCode(RespCodeTypeEnum.SUCCESS.getCode());
        res.setMessage(RespCodeTypeEnum.SUCCESS.getMessage());
        res.setData(channelInfoList);

        return GsonUtils.toJson(res);
    }

   public List<ChannelInfo> prepareChannelList(QuickPayChannelReq quickPayReq){

       //获取商户用户信息
       QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
       queryMerUsrWrapper
               .eq("mid",quickPayReq.getMid())
               .eq("mer_uid",quickPayReq.getUid());
       GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);

       //获取通道列表
       QueryWrapper<GwChannel> queryChannelWrapper = new QueryWrapper<>();
       queryChannelWrapper
               .eq("biz_id",GwBizTypeEnum.QUICK_PAY.getCode())
               .eq("status", GwChannelStatusEnum.OPEN.getCode());
       List<GwChannel> channelList = iGwChannelService.list(queryChannelWrapper);


       List<ChannelInfo> channelInfoList = new ArrayList<ChannelInfo>();
       if(channelList != null && !channelList.isEmpty()) {
           Iterator<GwChannel> cit = channelList.iterator();
           while (cit.hasNext()) {
               GwChannel channel = cit.next();
               GwBank bank = iGwBankService.getById(channel.getBankId());
               if (bank == null) {
                   continue;
               }
               if (!GwBankStatusEnum.OPEN.getCode().equals(bank.getStatus())) {
                   continue;
               }


               QueryWrapper<GwBindCard> queryBindCardWrapper = new QueryWrapper<>();
               queryBindCardWrapper
                       .eq("cid", gwMerUsr.getUid())
                       .eq("bank_id", bank.getId());
               List<GwBindCard> bindCardList = iGwBindCardService.list(queryBindCardWrapper);

               boolean isBind = false;
               List<CardInfo> cardInfoList = new ArrayList<CardInfo>();
               if (bindCardList != null && !bindCardList.isEmpty()) {
                   Iterator<GwBindCard> bcit = bindCardList.iterator();
                   while (bcit.hasNext()) {
                       GwBindCard bindCard = bcit.next();
                       if (!GwBindCardEnum.BINDED.getCode().equals(bindCard.getStatus())) {
                           continue;
                       }
                       isBind = true;
                       CardInfo cardInfo = new CardInfo();
                       cardInfo.setId(bindCard.getId());
                       cardInfo.setNo(bindCard.getNo());
                       cardInfo.setName(bindCard.getName());
                       cardInfoList.add(cardInfo);
                   }
               }

               ChannelInfo channelInfo = new ChannelInfo();
               channelInfo.setUid(quickPayReq.getUid());
               channelInfo.setCid(channel.getId());
               channelInfo.setBind(isBind);
               if (isBind == true) {
                   channelInfo.setCards(cardInfoList);
               }
               channelInfo.setBankId(bank.getId());
               channelInfo.setBankName(bank.getName());

               channelInfoList.add(channelInfo);

           }
       }

       return channelInfoList;
   }

    private GwMerUsr userCheck(QuickPayChannelReq quickPayReq) {
        //检查商户会员信息是否存在，不存在 新增
        QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
        queryMerUsrWrapper
                .eq("mid", quickPayReq.getMid())
                .eq("mer_uid", quickPayReq.getUid());
        GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);
        if (gwMerUsr == null) {
            GwMerUsr merUsr = new GwMerUsr();
            merUsr.setId(String.valueOf(snowFlakeUtils.nextId()));
            merUsr.setMid(quickPayReq.getMid());
            merUsr.setMerUid(quickPayReq.getUid());
            merUsr.setUid(RandomUtils.getUid());
            iGwMerUsrService.save(merUsr);
            //保存用户信息
            GwUsr gwUsr = new GwUsr();
            gwUsr.setId(merUsr.getUid());
            gwUsr.setName("test");
            gwUsr.setMobile("185010101010");
            gwUsr.setStatus(GwUsrStatusEnum.OPEN.getCode());
            iGwUsrService.save(gwUsr);
        }
        return gwMerUsr;
    }

    private void orderCheck(QuickPayChannelReq quickPayReq){
        QueryWrapper<GwConsumeOrder> queryOrderWrapper = new QueryWrapper<>();
        queryOrderWrapper
                .eq("mid", quickPayReq.getMid())
                .eq("mer_serial", quickPayReq.getSerial());
        GwConsumeOrder order = iGwConsumeOrderService.getOne(queryOrderWrapper);

        if (order == null) {
            order = new GwConsumeOrder();
            order.setId(String.valueOf(snowFlakeUtils.nextId()));
            order.setMid(quickPayReq.getMid());
            order.setMerSerial(quickPayReq.getSerial());
            order.setMerReqTs(DateUtils.getLocalDateTimeNow());
            order.setAmount(AmountUtils.null2Zero(quickPayReq.getAmount()));
            order.setCurrency("人民币");
            order.setRefundedAmt(AmountUtils.null2Zero(""));
            order.setPcsSerial(String.valueOf(snowFlakeUtils.nextId()));
            order.setStatus(GwConsumeOrderStatusEnum.INIT.getCode());

            iGwConsumeOrderService.save(order);
        }else{
            //校验订单状态
            orderStatusHandler(order.getStatus(),order);
        }
    }

    private void bizCheck(QuickPayChannelReq quickPayReq) {

        GwMerchant gwMerchant = payValidtorService.merchantValid(quickPayReq.getMid());

        GwMerUsr gwMerUsr = userCheck(quickPayReq);

        payValidtorService.usrValid(gwMerUsr.getUid());

        GwMerBiz gwMerBiz = payValidtorService.merBizValid(quickPayReq.getMid(), GwBizTypeEnum.QUICK_PAY.getCode());

        GwBiz gwBiz = payValidtorService.bizValid(GwBizTypeEnum.QUICK_PAY.getCode());

        orderCheck(quickPayReq);

    }

    private void orderStatusHandler(Integer status,GwConsumeOrder order){

        GwConsumeOrderStatusEnum type = GwConsumeOrderStatusEnum.getStatusEnumByCode(status);

        switch (type){
            case FAIL:
                log.info("order status is failed status:{},err_code:{},err_msg:{}",status,order.getErrCode(),order.getErrMsg());
                //todo; response right info
                throw new BusinessException(RespCodeTypeEnum.ORDER_PAY_FAILED);
            case SUCCESS:
                log.info("order status is success");
                throw new BusinessException(RespCodeTypeEnum.ORDER_PAY_SUCCESS);
            case INIT:
                log.info("order status is created!");
                return;
            case PROCESSING:
                log.info("order status is paying");
                throw new BusinessException(RespCodeTypeEnum.ORDER_PAYING);
            default:
        }

    }

}
