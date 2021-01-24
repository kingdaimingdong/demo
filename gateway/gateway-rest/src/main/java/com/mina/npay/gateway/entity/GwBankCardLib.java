package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 银行卡库
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_BANK_CARD_LIB")
public class GwBankCardLib implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 状态。0-打开；1-关闭
     */
    private Boolean status;

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
