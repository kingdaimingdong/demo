package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CITY")
public class GwCity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属国家
     */
    private String cid;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级ID
     */
    private String parentId;

    /**
     * 城市区号
     */
    private String code;

    /**
     * 邮政编码
     */
    private String zipcode;

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
