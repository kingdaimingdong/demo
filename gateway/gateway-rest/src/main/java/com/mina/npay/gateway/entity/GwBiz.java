package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 业务类型
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_BIZ")
public class GwBiz extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务名称
     */
    private String name;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
