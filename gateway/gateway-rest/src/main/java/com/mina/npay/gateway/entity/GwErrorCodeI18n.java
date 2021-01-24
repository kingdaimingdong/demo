package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 错误码
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_ERROR_CODE_I18N")
public class GwErrorCodeI18n implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 语言。详见字典LANG
     */
    private String lang;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 用户端显示码
     */
    private String uiCode;

    /**
     * 用户端显示信息
     */
    private String uiMessage;

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
