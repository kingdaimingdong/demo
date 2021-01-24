package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 接口状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwInterfaceStatusEnum {

    /**
     * 打开
     */
    OPEN(0, "打开"),
    /**
     * 关闭
     */
    CLOSE(1, "关闭");

    private Integer code;
    private String  name;

    private GwInterfaceStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwInterfaceStatusEnum getStatusEnumByCode(Integer code) {

        for(GwInterfaceStatusEnum key : GwInterfaceStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("interface status not find !");
        throw new BusinessException(RespCodeTypeEnum.INF_STATUS_ENUM_NOT_FIND);
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
