package com.mina.npay.gateway.enums;

/**
 * 证件类型枚举类
 * @author daimingdong
 */
public enum GwCardTypeEnum {

    /**
     * 存折
     */
    BANKBOOK("BANKBOOK", "存折"),
    /**
     * 贷记卡
     */
    CREDIT_CARD("CREDIT_CARD", "贷记卡"),
    /**
     * 借记卡
     */
    DEBIT_CARD("DEBIT_CARD", "借记卡"),
    /**
     * 借贷混合卡
     */
    MIXED_CARD("MIXED_CARD", "借贷混合卡");

    private String code;
    private String  name;

    private GwCardTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GwCardTypeEnum getCardTypeEnumByCode(String code) {

        for(GwCardTypeEnum key : GwCardTypeEnum.values()){
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
