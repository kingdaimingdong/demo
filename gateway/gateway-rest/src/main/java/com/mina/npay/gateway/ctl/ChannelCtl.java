package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.service.channel.ChannelService;
import com.mina.npay.gateway.service.channel.impl.ChannelServiceFactory;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通道controller
 * @author daimingodng
 */

@RestController
@RequestMapping(value = "/npay/common")
@Slf4j
@Api(hidden = true)
public class ChannelCtl {

    @GetMapping(value = "/channels")
    public String getChannelList(@RequestParam(value = "req") String req,@RequestParam(value = "bizType")String bizType){

        log.info("channelList request params:{},bitType:{}", req,bizType);

        ChannelServiceFactory channelServiceFactory = new ChannelServiceFactory();
        ChannelService channelService = channelServiceFactory.createChannelService(bizType);
        String res = channelService.getChannelList(req);

        return res;
    }
}
