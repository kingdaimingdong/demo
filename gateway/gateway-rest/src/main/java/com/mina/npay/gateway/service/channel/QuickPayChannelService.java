package com.mina.npay.gateway.service.channel;

import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayChannelReq;
import com.mina.npay.gateway.dto.resp.ChannelInfo;

import java.util.List;

/**
 * 快捷通道接口
 * @author daimingdong
 */
public interface QuickPayChannelService {

    /**
     * 获取通道列表
     * @param quickPayReq
     * @return
     */
    public ResultBase<List<ChannelInfo>> getChannelList(QuickPayChannelReq quickPayReq);
}
