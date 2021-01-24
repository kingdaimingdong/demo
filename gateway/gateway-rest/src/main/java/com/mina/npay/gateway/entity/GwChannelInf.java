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
 * 通道接口
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CHANNEL_INF")
public class GwChannelInf extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口ID
     */
    private String infId;

    /**
     * 通道ID
     */
    private String cid;

    /**
     * 优先级。取值范围[0,99]，0最高
     */
    private Integer priority;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;


}
