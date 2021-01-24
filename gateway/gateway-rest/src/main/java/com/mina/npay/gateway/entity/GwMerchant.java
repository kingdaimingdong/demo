package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户表
 * </p>
 *
 * @author daimingdong
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("GW_MERCHANT")
public class GwMerchant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 默认通讯KEY
     */
    private String tkey;

    /**
     * 默认服务器通知地址。如果空，不发送服务器通知
     */
    private String servNotice;

    /**
     * 默认页面通知地址。如果空，不发送页面通知
     */
    private String pageNotice;

    /**
     * 状态。0-正常；1-关闭；2-限制登录；3-限制密码交易；4-限制交易
     */
    private Integer status;

    /**
     * 对账单周期
     */
    private String billCyc;

    /**
     * 上线时间
     */
    private LocalDateTime uplineTs;


}
