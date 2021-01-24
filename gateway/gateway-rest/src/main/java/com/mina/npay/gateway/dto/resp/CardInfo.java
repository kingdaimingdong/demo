package com.mina.npay.gateway.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户绑卡信息
 * @author daimingdong
 */
@Data
public class CardInfo implements Serializable {
    /**
     * 用户绑卡编号
     */
    @ApiModelProperty(value = "用户绑卡编号")
    private String id;
    /**
     * 用户绑卡卡号
     */
    @ApiModelProperty(value = "用户绑卡卡号")
    private String no;
    /**
     * 用户绑卡名称
     */
    @ApiModelProperty(value = "用户绑卡名称")
    private String name;
}
