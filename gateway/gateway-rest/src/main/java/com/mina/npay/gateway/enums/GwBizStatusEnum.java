package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 商户业务状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwBizStatusEnum {

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

    private GwBizStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwBizStatusEnum getStatusEnumByCode(Integer code) {

        for(GwBizStatusEnum key : GwBizStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("biz status not find !");
        throw new BusinessException(RespCodeTypeEnum.BIZ_STATUS_ENUM_NOT_FIND);
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
