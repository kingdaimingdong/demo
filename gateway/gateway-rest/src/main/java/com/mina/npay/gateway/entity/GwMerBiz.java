package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户业务
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_MER_BIZ")
public class GwMerBiz extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 业务码
     */
    private String bizId;

    /**
     * 通讯KEY。如果空，使用商户表默认
     */
    private String tkey;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

    /**
     * 起始时间
     */
    private LocalDateTime stime;

    /**
     * 结束时间
     */
    private LocalDateTime etime;

    /**
     * 商户域名(防钓鱼)
     */
    private String referer;

    /**
     * 服务器通知地址。如果空，使用商户表默认
     */
    private String servNotice;

    /**
     * 页面通知地址。如果空，使用商户表默认
     */
    private String pageNotice;

}
