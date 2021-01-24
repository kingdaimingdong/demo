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
 * 客户(用户/商户)绑定银行卡
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_BIND_CARD")
public class GwBindCard extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户/商户ID
     */
    private String cid;

    /**
     * 卡号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件类型。详见字典ID_TYPE
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 卡扩展信息，JSON格式
     */
    private String ext;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 状态。详见字典BIND_CARD_STATUS
     */
    private Integer status;

    /**
     * 绑卡接口
     */
    private String infId;

    /**
     * 服务商绑卡ID
     */
    private String sprBindNo;

    /**
     * 绑定时间
     */
    private LocalDateTime bindTs;

    /**
     * 解绑时间
     */
    private LocalDateTime unbindTs;

}
