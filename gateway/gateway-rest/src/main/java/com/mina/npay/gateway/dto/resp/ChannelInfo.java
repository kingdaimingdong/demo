package com.mina.npay.gateway.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通道列表
 * @author daimingdong
 */
@Data
public class ChannelInfo implements Serializable {
    /**
     * 商户会员编号
     */
    @ApiModelProperty(value = "商户会员编号")
    private String uid;
    /**
     * 通道编号
     */
    @ApiModelProperty(value = "通道编号")
    private String cid;
    /**
     * 用户绑卡标识
     */
    @ApiModelProperty(value = "用户绑卡标识")
    private boolean bind;
    /**
     * 用户绑卡信息
     */
    @ApiModelProperty(value = "用户绑卡信息")
    private List<CardInfo> cards;
    /**
     * 渠道编号
     */
    @ApiModelProperty(value = "渠道编号")
    private String bankId;
    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称")
    private String bankName;
}
