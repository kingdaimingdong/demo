package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 序列表
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_SEQ")
public class GwSeq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序列名称
     */
    private String name;

    /**
     * 当前值
     */
    private Integer currValue;

    /**
     * 最小值
     */
    private Integer minValue;

    /**
     * 最大值
     */
    private Integer maxValue;

    /**
     * 创建时间
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
