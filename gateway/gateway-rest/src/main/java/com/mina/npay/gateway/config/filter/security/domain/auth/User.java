package com.mina.npay.gateway.config.filter.security.domain.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 用户
 * @author : daimingdong
 */
@Builder
@Data
public class User {
    @ApiModelProperty(value = "用户名", required = true)
    @Size(min=6, max=20)
    private String name;
    @ApiModelProperty(value = "密码", required = true)
    @Size(min=8, max=20)
    private String password;
}
