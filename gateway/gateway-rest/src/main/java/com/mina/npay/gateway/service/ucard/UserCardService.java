package com.mina.npay.gateway.service.ucard;

import com.mina.npay.gateway.dto.req.QuickPayReq;

/**
 * 用户绑卡接口
 * @author daimingdong
 */
public interface UserCardService {

    /**
     * 用户绑卡
     * @param certNo
     * @param Name
     * @param phoneNo
     */
    public String userBindCard(QuickPayReq req, String channelId, String certNo, String Name, String phoneNo);
}
