package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.dto.req.QuickPayConfirmReq;
import com.mina.npay.gateway.dto.resp.QuickPayResp;
import com.mina.npay.gateway.service.pay.QuickPayConfirmService;
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
@Api(description = "快捷支付支付确认接口服务",tags = "pconfirm")
@RequestMapping(value = "/npay")
@Slf4j
public class QuickPayConfirmCtl {

    @Autowired
    private QuickPayConfirmService quickPayConfirmService;

    @PostMapping(value = "/confirm")
    @ApiOperation(value = "快捷支付确认", notes = "快捷支付确认方法")
    public ResultBase<QuickPayResp> quickPayConfirm(QuickPayConfirmReq req){

        log.info("quickPayConfirm request params:{}", GsonUtils.toJson(req));

        QuickPayCheck.checkConfirmParam(req);

        ResultBase<QuickPayResp> res = quickPayConfirmService.payConfirm(req);

        log.info("quickPayConfirm reponse params:{}", GsonUtils.toJson(res));

        return res;
    }
}
