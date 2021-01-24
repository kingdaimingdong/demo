package com.mina.npay.gateway.ctl;

import com.mina.npay.gateway.common.utils.GsonUtils;
import com.mina.npay.gateway.dto.req.QuickPayReq;
import com.mina.npay.gateway.service.ucard.UserCardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户绑卡管理controller
 * @author daimingodng
 */

@Controller
@RequestMapping(value = "/npay/common")
@Slf4j
@Api(hidden = true)
public class UserCardCtl {

    @Autowired
    private UserCardService userCardService;

    @PostMapping(value = "/ucard")
    public String userBindCard(QuickPayReq req,
                               @RequestParam(value = "cid",required = true) String cid,
                               @RequestParam(value="certNo",required=true) String certNo,
                               @RequestParam(value="name",required=true) String name,
                               @RequestParam(value="phoneNo",required=true) String phoneNo,
                               @RequestParam(value="bizType",required=true) String bizType){

        log.info("userAdd request params cid:{},certNo:{},name:{},phoneNo:{},bizType:{},commParams:{}",cid,certNo,name,phoneNo,bizType,GsonUtils.toJson(req));


        String res = userCardService.userBindCard(req,cid,certNo,name,phoneNo);

        return res;
    }
}
