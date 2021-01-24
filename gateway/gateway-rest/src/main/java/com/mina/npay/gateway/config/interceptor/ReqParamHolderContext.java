package com.mina.npay.gateway.config.interceptor;

import com.mina.npay.gateway.config.interceptor.vo.ReqParamVo;

/**
 * 请求参数线程私有变量
 * @author daimingdong
 */
public class ReqParamHolderContext {

    private final static ThreadLocal<ReqParamVo> requestHolder = new ThreadLocal<ReqParamVo>();

    public static void add(String mid,String serial,String sign,Long reqTs) {
        ReqParamVo req = new ReqParamVo();
        req.setMid(mid);
        req.setSerial(serial);
        req.setSign(sign);
        req.setReqTs(reqTs);
        requestHolder.set(req);
    }

    public static ReqParamVo get() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }

}
