package com.mina.npay.gateway.config.filter.security.exception;

import com.mina.npay.gateway.config.filter.security.domain.ResultJson;
import lombok.Getter;

/**
 * 自定义异常
 * @author daimingdong
 */
@Getter
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}
