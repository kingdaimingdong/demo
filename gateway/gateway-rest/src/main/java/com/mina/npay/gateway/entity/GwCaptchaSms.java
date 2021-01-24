package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信码记录
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CAPTCHA_SMS")
public class GwCaptchaSms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联流水号
     */
    private String serialNo;

    /**
     * 验证码
     */
    private String code;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

    /**
     * 已验证次数
     */
    private Integer vcount;

    /**
     * 失效时间
     */
    private LocalDateTime expire;

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
