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
 * 个人用户
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_USR")
public class GwUsr extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 证件类型。详见字典ID_TYPE
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 状态。0-正常；1-关闭；2-限制登录；3-限制密码交易
     */
    private Integer status;

}
