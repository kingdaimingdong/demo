package com.mina.npay.gateway.config.filter.security.domain.auth;

import lombok.Builder;
import lombok.Data;

/**
 * 角色
 * @author : daimingdong
 */
@Data
@Builder
public class Role {
    private Long id;
    private String name;
    private String nameZh;
}
