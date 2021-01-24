package com.mina.npay.gateway.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户绑卡响应
 * @author daimingdong
 */
@Data
public class UserBindResp {

    /**
     * 通道ID
     */
    @ApiModelProperty(value = "通道ID")
    private String channelId;

    /**
     * 绑卡ID
     */
    @ApiModelProperty(value = "绑卡ID")
    private String bindId;

    /**
     * 绑卡状态 0, "已绑定" 1, "已解绑" 2, "绑定申请" 3, "绑定处理中" 4, "解绑申请" 5,"解绑处理中"
     */
    @ApiModelProperty(value = "绑卡状态")
    private Integer status;

    /**
     * 网关完成时间
     */
    @ApiModelProperty(value = "网关完成时间")
    private Long finishTs;

}
