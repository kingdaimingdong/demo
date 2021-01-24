package com.mina.npay.gateway.config.interceptor.vo;

import lombok.Data;

/**
 * 请求参数VO
 * @author daimingdong
 */
@Data
public class ReqParamVo {
    /**
     * 商户ID
     */
    private String mid;
    /**
     * 请求流水号
     */
    private String serial;
    /**
     * 签名
     */
    private String sign;
    /**
     * 请求时间戳
     */
    private Long reqTs;
}
