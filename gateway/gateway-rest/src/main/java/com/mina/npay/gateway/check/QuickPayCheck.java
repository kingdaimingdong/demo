package com.mina.npay.gateway.check;

import com.google.common.base.Preconditions;
import com.mina.npay.gateway.dto.req.QuickPayChannelReq;
import com.mina.npay.gateway.dto.req.QuickPayConfirmReq;
import com.mina.npay.gateway.dto.req.QuickPayUserCardReq;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 快捷支付校验
 * @author daimingdong
 */
@Slf4j
public class QuickPayCheck extends BaseCheck{
    /**
     * 快捷支付通道请求参数校验
     * @param req
     */
    public static void checkChannelParam(QuickPayChannelReq req) {
        checkCommonParam(req);
        try{
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getUid()), "商户会员不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getAmount()), "金额不能为空");
        }catch(Exception e){
            log.warn("快捷支付获取通道列表请求参数错误", e);
            throw new BusinessException(RespCodeTypeEnum.PARAM_ILLEGAL, e.getMessage());
        }
    }

    /**
     * 快捷支付用户绑卡求参数校验
     * @param req
     */
    public static void checkUserBindCardParam(QuickPayUserCardReq req) {
        checkCommonParam(req);
        try{
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getUid()), "商户会员不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getChannelId()), "通道ID不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getCardNo()), "绑卡卡号不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getPhoneNo()), "绑卡手机号不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getName()), "绑卡姓名不能为空");
        }catch(Exception e){
            log.warn("快捷支付用户绑卡请求参数错误", e);
            throw new BusinessException(RespCodeTypeEnum.PARAM_ILLEGAL, e.getMessage());
        }
    }

    /**
     * 快捷支付支付确认求参数校验
     * @param req
     */
    public static void checkConfirmParam(QuickPayConfirmReq req) {
        checkCommonParam(req);
        try{
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getUid()), "商户会员不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getAmount()), "金额不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getChannelId()), "通道ID不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getBindId()), "绑卡编号ID不能为空");
        }catch(Exception e){
            log.warn("快捷支付确认请求参数错误", e);
            throw new BusinessException(RespCodeTypeEnum.PARAM_ILLEGAL, e.getMessage());
        }
    }
}
