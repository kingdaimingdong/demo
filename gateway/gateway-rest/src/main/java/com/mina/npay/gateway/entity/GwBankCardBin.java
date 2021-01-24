package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 银行卡BIN
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_BANK_CARD_BIN")
public class GwBankCardBin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 发卡机构代码
     */
    private String bankCode;

    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 卡名称
     */
    private String cardName;

    /**
     * 卡号长度
     */
    private Integer panLength;

    /**
     * 卡号取值
     */
    private String panCode;

    /**
     * 卡标识号长度
     */
    private Integer verifyLength;

    /**
     * 卡标识号取值
     */
    private String verifyCode;

    /**
     * 状态。0-打开；1-关闭
     */
    private Integer status;

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
