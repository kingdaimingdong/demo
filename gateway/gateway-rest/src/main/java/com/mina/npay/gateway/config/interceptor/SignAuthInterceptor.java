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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.SortedMap;

/**
 * 接口签名sign认证拦截器
 * @author daimingdong
 */
@Slf4j
@Component
public class SignAuthInterceptor implements HandlerInterceptor {

    private IGwMerchantService iGwMerchantService = SpringUtils.getBean(IGwMerchantService.class);

    private IGwMerBizService iGwMerBizService = SpringUtils.getBean(IGwMerBizService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //get tkey
        String tkey = getBizKey(request.getParameter("mid"), GwBizTypeEnum.QUICK_PAY.getCode());

        //复制流
        HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);

        //获取全部参数(包括URL和body上的)
        SortedMap<String, String> allParams = HttpUtils.getAllParams(requestWrapper);

        //对参数进行签名验证
//        boolean isSigned = SignUtil.verifySign(allParams);

        boolean isSigned = SignUtil.verifySign(allParams,(String) tkey);
        if (isSigned) {
            log.info("签名通过");
            return true;
        } else {
            log.info("签名失败");

            throw new BusinessException(RespCodeTypeEnum.SIGN_ERROR);

        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    private String getBizKey(String mid,String bizType){

        String key = null;
        GwMerchant gwMerchant = iGwMerchantService.getById(mid);
        if(gwMerchant != null) {
            key = gwMerchant.getTkey();
        }
        QueryWrapper<GwMerBiz> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mid",mid).eq("biz_id",bizType);
        GwMerBiz gwMerBiz = iGwMerBizService.getOne(queryWrapper);
        if(gwMerBiz != null) {
            key = gwMerBiz.getTkey();
        }
        if(key == null){
            log.error("request tkey is null!");
            throw new BusinessException(RespCodeTypeEnum.BIZ_KTKEY_NOT_EXIST);
        }

        return key;
    }
}
