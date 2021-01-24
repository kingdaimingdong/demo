package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户会员映射
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_MER_USR")
public class GwMerUsr extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 商户会员ID
     */
    private String merUid;

    /**
     * 用户ID
     */
    private String uid;


}
