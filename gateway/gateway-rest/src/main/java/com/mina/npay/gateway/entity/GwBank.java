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
 * 银行
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_BANK")
public class GwBank extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 银行名称
     */
    private String name;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 银行LOGO图片路径
     */
    private String logo;

    /**
     * 属性。0-对私；1-对公
     */
    private Integer nature;

    /**
     * 类型。CREDIT_CARD-储值卡；DEBIT_CARD-信用卡
     */
    private String type;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
