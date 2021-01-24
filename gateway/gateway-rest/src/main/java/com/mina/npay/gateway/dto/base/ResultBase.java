package com.mina.npay.gateway.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用业务响应结果
 */
@Data
public class ResultBase<T> extends BaseResp implements Serializable {

    /**
     * 成功标记
     */
    @ApiModelProperty(value = "成功标记")
    private boolean           success = true;
    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;
    /**
     * 业务接口是否成功
     * @return
     */
    public boolean isSuccess(){return success == true ? true : false;}

}
