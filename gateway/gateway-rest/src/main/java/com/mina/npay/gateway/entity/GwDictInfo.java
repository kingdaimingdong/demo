package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_DICT_INFO")
public class GwDictInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型KEY
     */
    private String dkey;

    /**
     * 字典值
     */
    private String dvalue;

    /**
     * 字典文本信息
     */
    private String dtext;

    /**
     * 排序。0排最前
     */
    private Integer sort;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;


}
