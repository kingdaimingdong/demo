package com.mina.npay.gateway.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应结果
 * @author daimingdong
 */
@Data
public class BaseResp implements Serializable {
    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private String code;
    /**
     * 错误描述
     */
    @ApiModelProperty(value = "错误描述")
    private String message;
    /**
     * 商户ID
     */
    @ApiModelProperty(value = "商户ID")
    private String mid;
    /**
     * 商户请求流水
     */
    @ApiModelProperty(value = "商户请求流水")
    private String serial;
    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    private String sign;

}
