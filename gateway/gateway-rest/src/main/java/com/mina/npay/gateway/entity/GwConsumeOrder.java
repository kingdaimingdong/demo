package com.mina.npay.gateway.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.mina.npay.gateway.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消费订单表
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_CONSUME_ORDER")
public class GwConsumeOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 商户流水号
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
     * 商户扩展字段
     */
    private String merExt;

    /**
     * 商户保留字段
     */
    private String merReserved;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 已退款金额
     */
    private BigDecimal refundedAmt;

    /**
     * 下单IP
     */
    private String reqIp;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 业务码
     */
    private String bizId;

    /**
     * 通道ID
     */
    private String cid;

    /**
     * 接口ID
     */
    private String infId;

    /**
     * 绑卡ID
     */
    private String bcId;

    /**
     * 发送到服务商的流水号
     */
    private String pcsSerial;

    /**
     * 发送到服务商的时间
     */
    private LocalDateTime pcsReqTs;

    /**
     * 下次到服务商查询的时间
     */
    private LocalDateTime pcsNextQts;

    /**
     * 已到服务商查询的次数
     */
    private Integer pcsQc;

    /**
     * 服务商返回成功金额
     */
    private BigDecimal sprSuccAmt;

    /**
     * 服务商返回错误码
     */
    private String sprErrCode;

    /**
     * 服务商返回错误信息
     */
    private String sprErrMsg;

    /**
     * 状态。0-成功；1-失败；2-初始化；3-处理中
     */
    private Integer status;

    /**
     * 订单完成时间
     */
    private LocalDateTime finishTs;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误信息
     */
    private String errMsg;

}
