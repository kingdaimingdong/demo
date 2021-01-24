package com.mina.npay.gateway.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *订单查询响应结果
 * @author daimingdong
 */
@Data
public class OrderQueryResp {
    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String gwSerial;
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
