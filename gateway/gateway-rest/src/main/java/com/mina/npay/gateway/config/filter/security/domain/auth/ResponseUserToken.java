package com.mina.npay.gateway.config.filter.security.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * token结果响应
 * @author daimingdong
 */
@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;
}
