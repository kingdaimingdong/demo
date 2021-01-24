package com.mina.npay.gateway.enums;

/**
 * 网关业务类型枚举类
 * @author daimingdong
 */
public enum GwBizTypeEnum {

    /**
     * H5对公网银
     */
    B2B_H5("B2B_H5", "H5对公网银"),
    /**
     * PC对公网银
     */
    B2B_WEB("B2B_WEB", "PC对公网银"),
    /**
     * H5对私网银
     */
    B2C_H5("B2C_H5", "H5对私网银"),
    /**
     * PC对私网银
     */
    B2C_WEB("B2C_WEB", "PC对私网银"),
    /**
     * 快捷支付
     */
    QUICK_PAY("QUICK_PAY", "快捷支付"),
    /**
     * 退款
     */
    REFUND("REFUND", "退款");

    private String code;
    private String  name;

    private GwBizTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwBizTypeEnum getBizTypeEnumByCode(String code) {

        for(GwBizTypeEnum key : GwBizTypeEnum.values()){
            if(key.code.equals(code)){
                return key;
            }
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
