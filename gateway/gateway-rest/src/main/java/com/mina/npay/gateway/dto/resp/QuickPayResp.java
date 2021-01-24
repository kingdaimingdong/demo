package com.mina.npay.gateway.dto.resp;

import com.mina.npay.gateway.dto.base.BaseResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 快捷支付请求dto
 * @author daimingdong
 */
@Data
public class QuickPayResp{

    /**
     * 网关流水
     */
    @ApiModelProperty(value = "网关流水")
    private String gwSerial;

    /**
     * 商户保留字段
     */
    @ApiModelProperty(value = "商户保留字段")
    private String reserved;

    /**
     * 支付结果
     */
    @ApiModelProperty(value = "支付结果")
    private Integer status;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private String amount;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;

    /**
     * 网关完成时间
     */
    @ApiModelProperty(value = "网关完成时间")
    private Long finishTs;

}
