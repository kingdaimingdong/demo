package com.mina.npay.gateway.service.channel.impl;

import com.mina.npay.gateway.common.utils.SpringUtils;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.channel.ChannelService;

/**
 * 通道服务工厂
 * @author daimingdong
 */
public class ChannelServiceFactory {

    /**
     * 创建通道
     * @param bizType
     * @return
     */
    public ChannelService createChannelService(String bizType){

        if(GwBizTypeEnum.QUICK_PAY.getCode().equals(bizType)){
            return SpringUtils.getBean(QjChannelServiceImpl.class);
        }

        throw new BusinessException(RespCodeTypeEnum.BIZ_TYPE_NOT_EXIST);
    }
}
