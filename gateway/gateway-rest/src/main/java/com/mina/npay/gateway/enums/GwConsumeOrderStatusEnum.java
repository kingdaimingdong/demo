package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwConsumeOrderStatusEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 失败
     */
    FAIL(1, "失败"),
    /**
     * 初始化
     */
    INIT(2, "初始化"),
    /**
     * 处理中
     */
    PROCESSING(3, "处理中");

    private Integer code;
    private String  name;

    private GwConsumeOrderStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwConsumeOrderStatusEnum getStatusEnumByCode(Integer code) {

        for(GwConsumeOrderStatusEnum key : GwConsumeOrderStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("comsume order status not find !");
        throw new BusinessException(RespCodeTypeEnum.ORDER_STATUS_ENUM_NOT_FIND);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
