package com.mina.npay.gateway.check;

import com.google.common.base.Preconditions;
import com.mina.npay.gateway.dto.base.BaseReq;
import com.mina.npay.gateway.enums.RespCodeTypeEnum;
import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;

/**
 * 
 * 类BaseCheck.java的实现描述：TODO 类实现描述 
 * @author daimingdong
 */
@Slf4j
public class BaseCheck {

    /**
     * 校验公共参数
     * @param req
     */
    public static void checkCommonParam(BaseReq req) {
        try {
            Preconditions.checkNotNull(req, "请求对象为空");
            Preconditions.checkArgument(isNotBlank(req.getMid()), "商户编号不能为空");
            Preconditions.checkArgument(isNotBlank(req.getSerial()), "请求流水号不能为空");
            Preconditions.checkArgument(isNotBlank(req.getSign()), "sign不能为空");
            Preconditions.checkArgument(isNotBlank(req.getReqTs()), "请求时间戳不能为空");
        } catch (Exception e) {
            log.warn("公共请求参数校验错误, msg={}", e.getMessage(), e);
            throw new BusinessException(RespCodeTypeEnum.PARAM_ILLEGAL,e.getMessage());
        }
    }
    
	/**
	 * 校验日期
	 * @param filed 日期字段
	 * @param parsePatterns 日期格式，例如yyyy-MM-dd HH:mm:ss
	 * 
     * @Author daimingdong
     * @Date   2017/05/15
	 */
	protected static boolean checkDateFormat(String filed, String parsePatterns){
	        try {
	            org.apache.commons.lang3.time.DateUtils.parseDateStrictly(filed, parsePatterns);
	        } catch (ParseException e) {
	            log.warn("==>checkDateFormat ParseException, errorMsg:{}", e.getMessage(), e);
	            return false;
	        }
	        return true;
	    }

	/**
	 * 校验是否为空
	 * @Author daimingdong
     * @Date   2017/05/15
	 */
	protected static boolean isNotBlank(String filed){
	    return StringUtils.isNotBlank(filed);
	}
	
	/**
     * 校验字段枚举值
     * filed:字段
     * enums:字段枚举值
     * 
     * @Author daimingdong
     * @Date 2017/03/06
     */
    protected static boolean checkFiledEnum(String filed, String[] enums) {
        boolean resultFlag = false;
        if (StringUtils.isNotBlank(filed)) {
            for (String obj : enums) {
                if (filed.equals(obj)) {
                    resultFlag = true;
                }
            }
        }
        return resultFlag;
    }
}
