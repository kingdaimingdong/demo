package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 通道接口状态枚举类
 * @author  daimingodng
 */
@Slf4j
public enum GwChannelInfStatusEnum {

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

    private GwChannelInfStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwChannelInfStatusEnum getStatusEnumByCode(Integer code) {

        for(GwChannelInfStatusEnum key : GwChannelInfStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("channel inf status not find !");
        throw new BusinessException(RespCodeTypeEnum.CHANNEL_INF_STATUS_ENUM_NOT_FIND);
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
