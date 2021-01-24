package com.mina.npay.gateway.enums;

import com.mina.npay.gateway.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户状态枚举类
 * @author daimingdong
 */
@Slf4j
public enum GwUsrStatusEnum {

    /**
     * 正常
     */
    OPEN(0, "正常"),
    /**
     * 关闭
     */
    CLOSE(1, "关闭"),
    /**
     * 限制登录
     */
    LIMIT_LOGIN(2, "限制登录"),
    /**
     * 限制密码交易
     */
    LIMIT_PWD_TRADE(3, "限制密码交易");

    private Integer code;
    private String  name;

    private GwUsrStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwUsrStatusEnum GwUsrStatusEnum(Integer code) {

        for(GwUsrStatusEnum key : GwUsrStatusEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        log.error("usr status not find !");
        throw new BusinessException(RespCodeTypeEnum.USR_STATUS_ENUM_NOT_FIND);
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
