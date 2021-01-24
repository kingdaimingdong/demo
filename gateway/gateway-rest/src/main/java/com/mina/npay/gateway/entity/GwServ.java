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
 * 服务商
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_SERV")
public class GwServ extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String name;

    /**
     * 类型。详见字典SERV_TYPE
     */
    private Integer type;

    /**
     * 可交易时间。cron表达式
     */
    private String activeTime;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省
     */
    private String province;

    /**
     * 所在市
     */
    private String city;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

}
