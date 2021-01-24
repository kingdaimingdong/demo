package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 接口服务商状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwChannelInfTCStatusEnum {

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

    private GwChannelInfTCStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwChannelInfTCStatusEnum getStatusEnumByCode(Integer code) {

        for(GwChannelInfTCStatusEnum key : GwChannelInfTCStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("inf tc status not find !");
        throw new BusinessException(RespCodeTypeEnum.INF_TC_STATUS_ENUM_NOT_FIND);
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
