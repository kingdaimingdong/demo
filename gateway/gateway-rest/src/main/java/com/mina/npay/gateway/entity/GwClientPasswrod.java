package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户(用户/商户)密码
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CLIENT_PASSWROD")
public class GwClientPasswrod implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID/商户ID
     */
    private String cid;

    /**
     * 密码类型。LOGIN-登录密码；TRADE-交易密码
     */
    private String type;

    /**
     * 静态密码
     */
    private String passwd;

    /**
     * 密码有效期
     */
    private LocalDateTime expire;

    /**
     * 密码连续错误次数
     */
    private Integer wrongCount;

    /**
     * 密码解锁时间
     */
    private LocalDateTime unlockTs;

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
