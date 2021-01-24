package com.mina.npay.gateway.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * baseEntity 基础类
 * @author daimingdong
 */
@Data
public class BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;

    /**
     * 入库时间
     */
    @TableField(value = "cts", fill = FieldFill.INSERT)
    private LocalDateTime cts;

    /**
     * 更新时间
     */
    @TableField(value = "uts", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uts;

    /**
     * 版本戳
     */
    @Version
    private Integer vs;
}
