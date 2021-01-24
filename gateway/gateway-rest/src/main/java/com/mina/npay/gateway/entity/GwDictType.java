package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典类型
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_DICT_TYPE")
public class GwDictType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型KEY
     */
    private String dkey;

    /**
     * 类型名称
     */
    private String dname;


}
