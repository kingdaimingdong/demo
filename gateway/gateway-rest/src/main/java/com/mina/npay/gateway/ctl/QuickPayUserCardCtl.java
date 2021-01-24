package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayUserCardReq;
import com.mina.npay.gateway.dto.resp.UserBindResp;
import com.mina.npay.gateway.service.ucard.QuickPayUserBindCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户绑卡管理controller
 * @author daimingodng
 */

@RestController
@Api(description = "快捷支付用户绑卡接口服务",tags = "ucard")
@RequestMapping(value = "/npay")
@Slf4j
public class QuickPayUserCardCtl {

    @Autowired
    private QuickPayUserBindCardService quickPayUserBindCardService;

    @PostMapping(value = "/ucard")
    @ApiOperation(value = "快捷用户绑卡", notes = "快捷用户绑卡方法")
    public ResultBase<UserBindResp> quickPayUserBindCard(QuickPayUserCardReq req){

        log.info("quickPayUserBindCard request params:{}", GsonUtils.toJson(req));

        QuickPayCheck.checkUserBindCardParam(req);

        ResultBase<UserBindResp> res = quickPayUserBindCardService.userBindCard(req);

        log.info("quickPayUserBindCard reponse params:{}", GsonUtils.toJson(res));

        return res;
    }
}
