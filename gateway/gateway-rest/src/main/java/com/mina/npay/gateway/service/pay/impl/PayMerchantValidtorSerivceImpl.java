package com.mina.npay.gateway.service.pay.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.common.utils.RandomUtils;
import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import com.mina.npay.gateway.entity.*;
import com.mina.npay.gateway.enums.*;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.*;
import com.mina.npay.gateway.service.pay.PayMerchantValidtorSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 支付商户校验器实现类
 * @author daimingdong
 */
@Slf4j
@Service
public class PayMerchantValidtorSerivceImpl implements PayMerchantValidtorSerivce {

    @Autowired
    private IGwMerchantService iGwMerchantService;

    @Autowired
    private IGwMerUsrService iGwMerUsrService;

    @Autowired
    private IGwMerBizService iGwMerBizService;

    @Autowired
    private IGwBizService iGwBizService;

    @Autowired
    private IGwUsrService iGwUsrService;

    @Autowired
    private SnowFlakeUtils snowFlakeUtils;


    /**
     * 商户校验
     */
    @Override
    public void valid(String mid,String uid,String bizCode){


        //校验商户信息是否存在
        GwMerchant gwMerchant = iGwMerchantService.getById(mid);
        if(gwMerchant == null){
            log.error("merchant info is not exist!");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_NOT_EXIST);
        }

        //校验商户开通状态
        if(!GwMerchantStatusEnum.OPEN.getCode().equals(gwMerchant.getStatus())){
            log.error("merchant status is not open!");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_STATUS_NOT_OPEN);
        }

        //校验商户是否上线
        long durationDays = DateUtils.between(DateUtils.getLocalDateTimeNow(),gwMerchant.getUplineTs(), TimeUnit.DAYS);
        if(durationDays > 0){
            log.error("merchant is offline");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_OFFLINE);
        }

        //检查商户会员信息是否存在，不存在 新增
        QueryWrapper<GwMerUsr> queryMerUsrWrapper = new QueryWrapper<>();
        queryMerUsrWrapper
                .eq("mid",mid)
                .eq("mer_uid",uid);
        GwMerUsr gwMerUsr = iGwMerUsrService.getOne(queryMerUsrWrapper);
        if(gwMerUsr == null){
            GwMerUsr merUsr = new GwMerUsr();
            merUsr.setId(String.valueOf(snowFlakeUtils.nextId()));
            merUsr.setMid(mid);
            merUsr.setMerUid(uid);
            merUsr.setUid(RandomUtils.getUid());
            iGwMerUsrService.save(merUsr);
            //保存用户信息
            GwUsr gwUsr = new GwUsr();
            gwUsr.setId(String.valueOf(snowFlakeUtils.nextId()));
            gwUsr.setName("test");
            gwUsr.setMobile("185010101010");
            gwUsr.setStatus(GwUsrStatusEnum.OPEN.getCode());
            iGwUsrService.save(gwUsr);

        }

        //检查用户权限
        GwUsr gwUsr = iGwUsrService.getById(gwMerUsr.getUid());
        if(!GwUsrStatusEnum.OPEN.getCode().equals(gwUsr.getStatus())){
            log.error("usr is not open");
            throw new BusinessException(RespCodeTypeEnum.USER_STATUS_NOT_OPEN);
        }

        //校验商户业务信息是否存在
        QueryWrapper<GwMerBiz> queryMerBizWrapper = new QueryWrapper<>();
        queryMerBizWrapper
                .eq("mid",mid)
                .eq("biz_id", bizCode);
        GwMerBiz gwMerBiz = iGwMerBizService.getOne(queryMerBizWrapper);
        if(gwMerBiz == null){
            log.error("merchant biz info is not exist!");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_NOT_EXIST);
        }

        //校验商户业务是否开通
        if(!GwMerchantBizStatusEnum.OPEN.getCode().equals(gwMerBiz.getStatus())){
            log.error("merchant biz is not open");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_STATUS_NOT_OPEN);
        }

        //校验商户业务是否上线
        if(LocalDateTime.now().isBefore(gwMerBiz.getStime()) || LocalDateTime.now().isAfter(gwMerBiz.getEtime())){
            log.error("merchant biz is offline");
            throw new BusinessException(RespCodeTypeEnum.MERCHANT_BIZ_OFFLINE);
        }

        //校验业务线
        GwBiz gwBiz = iGwBizService.getById(bizCode);
        if(gwBiz == null){
            log.error("biz info is not exist!");
            throw new BusinessException(RespCodeTypeEnum.BIZ_NOT_EXIST);
        }

        //校验业务开通状态
        if(!GwBizStatusEnum.OPEN.getCode().equals(gwBiz.getStatus())){
            log.error("biz status is not open!");
            throw new BusinessException(RespCodeTypeEnum.BIZ_STATUS_NOT_OPEN);
        }

    }


}
