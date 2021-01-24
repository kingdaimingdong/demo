package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 通道
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CHANNEL")
public class GwChannel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通道名称
     */
    private String name;

    /**
     * 业务码
     */
    private String bizId;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
