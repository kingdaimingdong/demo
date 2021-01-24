package com.mina.npay.gateway.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接口
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_INTERFACE")
public class GwInterface extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 服务商ID
     */
    private String servId;

    /**
     * 合同有效期
     */
    private LocalDateTime contractExpire;

    /**
     * 证书有效期
     */
    private LocalDateTime certExpire;

    /**
     * 单次允许累计最小交易额
     */
    private BigDecimal amtMin;

    /**
     * 单次允许累计最大交易额
     */
    private BigDecimal amtMax;

    /**
     * 属性。0-对私；1-对公；2-混合
     */
    private Integer nature;

    /**
     * 类型。详见字典CARD_TYPE
     */
    private String type;

    /**
     * 是否支持批量交易。0-支持批量；1-不支持批量
     */
    private Integer isBatch;

    /**
     * 协议类BEAN
     */
    private String bean;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
