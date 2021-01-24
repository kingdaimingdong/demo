package com.mina.npay.gateway.dto.req;

import com.mina.npay.gateway.dto.base.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 快捷支付用户绑卡请求dto
 * @author daimingdong
 */
@Data
public class QuickPayUserCardReq extends BaseReq implements Serializable {

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额", required = true)
    private String amount;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种", required = false)
    private String currency;

    /**
     * 商户会员ID
     */
    @ApiModelProperty(value = "商户会员ID", required = true)
    private String uid;

    /**
     * 服务器通知地址
     */
    @ApiModelProperty(value = "服务器通知地址", required = false)
    private String servNotice;

    /**
     * 页面通知地址
     */
    @ApiModelProperty(value = "页面通知地址", required = false)
    private String pageNotice;

    /**
     * 商户保留字段
     */
    @ApiModelProperty(value = "商户保留字段", required = false)
    private String reserved;

    /**
     * 通道ID
     */
    @ApiModelProperty(value = "通道ID", required = false)
    private String channelId;

    /**
     * 渠道卡号
     */
    @ApiModelProperty(value = "渠道卡号", required = false)
    private String cardNo;

    /**
     * 用户绑卡名称
     */
    @ApiModelProperty(value = "用户绑卡名称", required = false)
    private String name;

    /**
     * 用户绑卡手机号
     */
    @ApiModelProperty(value = "用户绑卡手机号", required = false)
    private String phoneNo;

}
