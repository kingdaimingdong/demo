package com.mina.npay.gateway.service.acc.impl;

import com.mina.npay.gateway.service.acc.AccFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 清结算服务接口实现类
 * @author daimingdong
 */
@Service
@Slf4j
public class AccFacadeServiceImpl implements AccFacadeService {

    @Override
    public void accCreate(String uid,String name,Integer type,String[]currency){

        log.info("accCreate facade request params:{}");

        //prepare params
        //acc api interface invoke

        log.info("accCreate facade response params:{}");
    }

    @Override
    public void payAcc(String mid,String biz,String sid,String uid, String amount,String currency){

        log.info("payAcc request params:{}");

        log.info("payAcc response params:{}");
    }

    @Override
    public void refundAcc(String mid,String origSerial,String biz,String sid,String uid,String amount,String currency){

        log.info("refundAcc request params{}");

        log.info("refundAcc response params{}");

    }

}
