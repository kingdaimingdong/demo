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
 * 接口通道映射码
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CHANNEL_INF_TC")
public class GwChannelInfTc extends BaseEntity implements Serializable {

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
     * 服务商通道ID
     */
    private String sprCid;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
