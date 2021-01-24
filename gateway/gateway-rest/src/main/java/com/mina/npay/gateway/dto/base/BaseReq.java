package com.mina.npay.gateway.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通用请求参数
 * @author daimingdong
 */
@Data
public class BaseReq implements Serializable {

    /**
     * 商户ID
     */
    @ApiModelProperty(value = "商户ID", required = true)
    private String mid;
    /**
     * 请求流水号
     */
    @ApiModelProperty(value = "请求流水号", required = true)
    private String serial;
    /**
     * 请求时间戳
     */
    @ApiModelProperty(value = "请求时间戳", required = true)
    private String reqTs;
    /**
     * 签名
     */
    @ApiModelProperty(value = "签名", required = true)
    private String sign;

}
