package com.mina.npay.gateway.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退款订单表
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_REFUND_ORDER")
public class GwRefundOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原订单ID
     */
    private String origId;

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 商户退款流水
     */
    private String merSerial;

    /**
     * 商户下单时间
     */
    private LocalDateTime merReqTs;

    /**
     * 服务器通知地址
     */
    private String merServNotice;

    /**
     * 页面通知地址
     */
    private String merPageNotice;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 发送到服务商的流水号
     */
    private String pcsSerial;

    /**
     * 发送到服务商的时间
     */
    private LocalDateTime pcsReqTs;

    /**
     * 下单IP
     */
    private String reqIp;

    /**
     * 状态。0-成功；1-失败；2-初始化；3-处理中
     */
    private Integer status;

    /**
     * 完成时间
     */
    private LocalDateTime finishTs;

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
