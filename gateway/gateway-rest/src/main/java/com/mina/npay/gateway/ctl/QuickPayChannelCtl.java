package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayChannelReq;
import com.mina.npay.gateway.dto.resp.ChannelInfo;
import com.mina.npay.gateway.service.channel.QuickPayChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通道controller
 * @author daimingodng
 */

@RestController
@Api(description = "快捷支付通道接口服务",tags = "channel")
@RequestMapping(value = "/npay")
@Slf4j
public class QuickPayChannelCtl {

    @Autowired
    private QuickPayChannelService quickPayChannelService;

    @GetMapping(value = "/channels")
    @ApiOperation(value = "快捷支付通道列表", notes = "快捷支付通道列表方法")
    public ResultBase<List<ChannelInfo>> getQuickPayChannelList(QuickPayChannelReq req){

        log.info("quick pay channelList request params:{}", GsonUtils.toJson(req));

        QuickPayCheck.checkChannelParam(req);

        ResultBase<List<ChannelInfo>> res = quickPayChannelService.getChannelList(req);

        log.info("quick pay channelList response params:{}", GsonUtils.toJson(res));

        return res;
    }
}
