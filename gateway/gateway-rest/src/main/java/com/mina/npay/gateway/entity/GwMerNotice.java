package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户通知表
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_MER_NOTICE")
public class GwMerNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联流水号
     */
    private String serialNo;

    /**
     * 通知CMD
     */
    private String cmd;

    /**
     * 通知地址
     */
    private String url;

    /**
     * 首次通知时间
     */
    private LocalDateTime ftime;

    /**
     * 上次已通知时间
     */
    private LocalDateTime ptime;

    /**
     * 下次待通知时间
     */
    private LocalDateTime ntime;

    /**
     * 已通知次数
     */
    private Integer ncount;

    /**
     * 状态。2-初始化；1-通知中；0-已完成
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
