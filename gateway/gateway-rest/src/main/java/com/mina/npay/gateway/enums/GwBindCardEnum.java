package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 绑卡状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwBindCardEnum {

    /**
     * 已绑定
     */
    BINDED(0, "已绑定"),
    /**
     * 已解绑
     */
    UNBIND(1, "已解绑"),
    /**
     * 绑定申请
     */
    BIND_APPLY(2, "绑定申请"),
    /**
     * 绑定处理中
     */
    BINDING(3, "绑定处理中"),
    /**
     * 解绑申请
     */
    UNBIND_APPLY(4, "解绑申请"),
    /**
     * 解绑处理中
     */
    UNBINGING(5,"解绑处理中");

    private Integer code;
    private String  name;

    private GwBindCardEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwBindCardEnum getStatusEnumByCode(Integer code) {

        for(GwBindCardEnum key : GwBindCardEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("bind card status not find !");
        throw new BusinessException(RespCodeTypeEnum.BIND_CARD_STATUS_ENUM_NOT_FIND);
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
