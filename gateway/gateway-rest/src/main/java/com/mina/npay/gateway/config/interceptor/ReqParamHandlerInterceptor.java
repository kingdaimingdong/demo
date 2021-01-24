package com.mina.npay.gateway.config.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.common.utils.SpringUtils;
import com.mina.npay.gateway.entity.GwMerBiz;
import com.mina.npay.gateway.entity.GwMerchant;
import com.mina.npay.gateway.enums.GwBizTypeEnum;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import com.mina.npay.gateway.service.IGwMerBizService;
import com.mina.npay.gateway.service.IGwMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求参数拦截器
 * @author daimingdong
 */
@Slf4j
@Component
public class ReqParamHandlerInterceptor implements HandlerInterceptor {

    //方法请求参数过滤
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String mid      = request.getParameter("mid");
        String serial   = request.getParameter("serial");
        String sign     = request.getParameter("sign");
        Long   reqTs    = Long.parseLong(request.getParameter("reqTs"));


        log.info("requset interceptor param mid:{},serial:{},sign:{},reqTs:{}",mid,serial,sign,reqTs);

        if(StringUtils.isEmpty(mid) || StringUtils.isEmpty(serial)
                || StringUtils.isEmpty(sign) || reqTs == null){
            throw new BusinessException(RespCodeTypeEnum.PAY_REQ_PARAM_ERR);
        }

        ReqParamHolderContext.add(mid,serial,sign,reqTs);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ReqParamHolderContext.get() != null) {
            log.info( request.getRequestURL() + "->请求用时：" + (System.currentTimeMillis() - ReqParamHolderContext.get().getReqTs()));
            ReqParamHolderContext.remove();
        }
    }

}
