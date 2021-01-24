package com.mina.npay.gateway.exceptions;

import com.mina.npay.gateway.common.utils.MessageUtils;
import com.mina.npay.gateway.common.utils.SpringUtils;
import com.mina.npay.gateway.config.interceptor.ReqParamHolderContext;
import com.mina.npay.gateway.config.interceptor.vo.ReqParamVo;
import com.mina.npay.gateway.dto.base.ResultBase;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局controller异常处理类配置定义
 *
 * @author daimingdong
 */
@Slf4j
@RestControllerAdvice
public class BusinessExcpHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBase<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        e.getConstraintViolations().forEach(err -> log.error(err.getMessage()));
        return getExceptionResult(RespCodeTypeEnum.PAY_REQ_PARAM_ERR.getCode(), RespCodeTypeEnum.PAY_REQ_PARAM_ERR.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBase<?> bindExceptionHandler(BindException e) {
        e.getBindingResult().getFieldErrors().forEach(err -> log.error(err.getDefaultMessage(), e));
        return getExceptionResult(RespCodeTypeEnum.PAY_REQ_PARAM_ERR.getCode(), RespCodeTypeEnum.PAY_REQ_PARAM_ERR.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBase<?> bindExceptionHandler(BusinessException e) {
        log.error("业务异常:{}" + e.getMessage(), e);
        return getExceptionResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBase<?> throwableHandler(Throwable t) {
        log.error("系统未知异常:{}", t.getLocalizedMessage(), t);
        return getExceptionResult(RespCodeTypeEnum.UNKNOWN_ERROR.getCode(), RespCodeTypeEnum.UNKNOWN_ERROR.getMessage());
    }

    private ResultBase<?> getExceptionResult(String code, String message) {

        ResultBase<?> result = new ResultBase<>();

        result.setSuccess(false);
        result.setCode(code);
        //国际化message
        result.setMessage(MessageUtils.get(code));
        ReqParamVo req = ReqParamHolderContext.get();
        if (req != null && !StringUtils.isEmpty(req.getMid())
                && !StringUtils.isEmpty(req.getSerial())
                && !StringUtils.isEmpty(req.getSign())) {

            result.setMid(req.getMid());
            result.setSerial(req.getSerial());
            result.setSign(req.getSign());
            ReqParamHolderContext.remove();

        }

        return result;

    }
}
