package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 国家
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_COUNTRY")
public class GwCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * logo图标路径
     */
    private String logo;

    /**
     * 币种
     */
    private String currency;

    /**
     * 时区
     */
    private Integer tz;

    /**
     * 语言
     */
    private String lang;

    /**
     * 入库时间
     */
    private LocalDateTime cts;

    /**
     * 更新时间
     */
    private LocalDateTime uts;

    /**
     * 版本戳
     */
    private Integer vs;


}
