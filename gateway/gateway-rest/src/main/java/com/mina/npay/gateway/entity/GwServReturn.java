package com.mina.npay.gateway.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 服务商返回交易数据
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_SERV_RETURN")
public class GwServReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单(消费/付款)ID
     */
    private String orderId;

    /**
     * 业务码
     */
    private String bizId;

    /**
     * 服务商返回流水
     */
    private String sprSerial;

    /**
     * 服务商返回完成时间
     */
    private LocalDateTime sprFinishTs;

    /**
     * 服务商返回成功金额
     */
    private BigDecimal sprSuccAmt;

    /**
     * 服务商返回币种
     */
    private String sprCurrency;

    /**
     * 服务商返回账务日期
     */
    private LocalDate sprBillDate;

    /**
     * 服务商返回错误码
     */
    private String sprErrCode;

    /**
     * 服务商返回错误信息
     */
    private String sprErrMsg;

    /**
     * 服务商返回原始数据
     */
    private String sprRaw;

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
