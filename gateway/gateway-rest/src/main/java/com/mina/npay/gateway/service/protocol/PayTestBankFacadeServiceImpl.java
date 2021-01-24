package com.mina.npay.gateway.service.protocol;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mina.npay.gateway.common.utils.DateUtils;
import com.mina.npay.gateway.entity.GwConsumeOrder;
import com.mina.npay.gateway.enums.GwConsumeOrderStatusEnum;
import com.mina.npay.gateway.service.IGwConsumeOrderService;
import com.mina.npay.gateway.service.pay.PayFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * mock 协议bean实现类
 * @author  daimingdong
 */
@Slf4j
@Service
public class PayTestBankFacadeServiceImpl implements PayFacadeService {

    @Autowired
    private IGwConsumeOrderService iGwConsumeOrderService;

    @Override
    public boolean pay(String mid,String merSerial,String orderId){

        QueryWrapper<GwConsumeOrder> queryConsumeOrder = new QueryWrapper<>();
        queryConsumeOrder
                .eq("mid", mid)
                .eq("mer_serial", merSerial);
        GwConsumeOrder consumeOrder = iGwConsumeOrderService.getOne(queryConsumeOrder);

        //todo invoke bank begin real pay

        consumeOrder.setSprSuccAmt(consumeOrder.getAmount());
        consumeOrder.setFinishTs(DateUtils.getLocalDateTimeNow());
        consumeOrder.setSprErrCode("0000");
        consumeOrder.setSprErrMsg("success");
        UpdateWrapper<GwConsumeOrder> updateConsumeOrder = new UpdateWrapper<>();
        updateConsumeOrder
                .eq("mid", mid)
                .eq("mer_serial", mid);
        consumeOrder.setStatus(GwConsumeOrderStatusEnum.SUCCESS.getCode());
        iGwConsumeOrderService.update(consumeOrder,updateConsumeOrder);
        return true;
    }
}
