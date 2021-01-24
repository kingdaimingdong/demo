package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.check.QuickPayCheck;
import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.req.QuickPayChannelReq;
import com.mina.npay.gateway.service.pay.PayConfirmService;
import com.mina.npay.gateway.service.pay.QuickPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 快捷支付产品controller
 * @author daimingodng
 */
@Controller
@Api(description = "快捷支付h5首页接口服务",tags = "h5index")
@Slf4j
public class QuickPayH5Ctl {

    @Autowired
    private QuickPayService quickPayService;

    @Autowired
    private PayConfirmService payConfirmService;

    @PostMapping(value = "/h5/pay")
    @ApiOperation(value = "支付", notes = "快捷支付接口")
    public String quickPayCreate(HttpServletRequest request , QuickPayChannelReq req){

        log.info("quick pay request params:{}", GsonUtils.toJson(req));

        QuickPayCheck.checkChannelParam(req);

        requestSave(request,req);

        return "first_default_page";
    }

    private void requestSave(HttpServletRequest request,QuickPayChannelReq req){

        request.setAttribute("mid",req.getMid());
        request.setAttribute("reqTs",req.getReqTs());
        request.setAttribute("uid",req.getUid());
        request.setAttribute("serial",req.getSerial());
        request.setAttribute("amount",req.getAmount());
        request.setAttribute("currency",req.getCurrency());
        request.setAttribute("sign",req.getSign());
        request.setAttribute("reserved",req.getReserved());
        request.setAttribute("servNotice",req.getServNotice());
        request.setAttribute("pageNotice",req.getPageNotice());

    }

}
