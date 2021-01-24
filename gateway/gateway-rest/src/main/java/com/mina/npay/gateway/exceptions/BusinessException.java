package com.mina.npay.gateway.exceptions;

import com.mina.npay.gateway.base.BaseResultType;
import org.springframework.core.NestedRuntimeException;

import java.text.MessageFormat;


/**
 *
 * 业务异常类
 * @author daimingdong
 */
public class BusinessException extends NestedRuntimeException {

    private static final long    serialVersionUID = 1L;

    private final BaseResultType type;

    /**
     * 构造
     *
     * @param type
     * @param args
     */
    public BusinessException(BaseResultType type, Object... args) {
        super(MessageFormat.format(type.getMessage(), args));
        this.type = type;
    }

    /**
     * 业务异常信息码
     *
     * @return
     */
    public String getCode() {
        return type.getCode();
    }

    /**
     * 业务异常信息
     *
     * @return
     */
    public BaseResultType getType() {
        return type;
    }

}