package com.mina.npay.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户基本信息
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("GW_MER_INFO")
public class GwMerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业官网
     */
    private String website;

    /**
     * 企业所在地
     */
    private String addr;

    /**
     * 法人姓名
     */
    private String corporate;

    /**
     * 企业代码
     */
    private String ecode;

    /**
     * 营业执照文件路径
     */
    private String license;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactMobile;

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
