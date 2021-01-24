package com.mina.npay.gateway.enums;


import com.mina.npay.gateway.base.BaseResultType;
import com.mina.npay.gateway.common.utils.MessageUtils;


/**
 * 
 * 异常结果枚举类
 * @author daimingdong
 */
public enum RespCodeTypeEnum implements BaseResultType {


    /**
     * 成功
     */
    SUCCESS("0000", "成功","0000", "成功"),


    /**
     * 系统级
     */
    //sys error
    UNKNOWN_ERROR("9999", "未知系统异常","9999", "未知系统异常"),

    /**
     * api 接口级
     */
    PAY_REQ_PARAM_ERR("1001", "请求参数错误", "1001", "请求参数错误"),
    SIGN_ERROR("1002", "sian签名失败","1002", "sian签名失败"),
    BIZ_KTKEY_NOT_EXIST("1003", "业务key不存在","1003", "业务key不存在"),

    /**
     * 参数校验
     */
    PARAM_ILLEGAL("2001", "参数不符合规范", "2001", "参数不符合规范"),
    BIZ_TYPE_NOT_EXIST("2002", "业务类型不存在", "2002", "业务类型不存在"),

    /**
     * 业务级
     */
    MERCHANT_NOT_EXIST("30001", "商户信息不存在","30001", "商户信息不存在"),
    MERCHANT_STATUS_ENUM_NOT_FIND("30002", "商户没有对应状态类型","30002", "商户没有对应状态类型"),
    MERCHANT_STATUS_NOT_OPEN("30003", "商户没有开通","30003", "商户没有开通"),
    MERCHANT_OFFLINE("30004", "商户离线","30004", "商户离线"),
    MERCHANT_BIZ_NOT_EXIST("30005", "商户业务信息不存在","30005", "商户业务信息不存在"),
    MERCHANT_BIZ_STATUS_ENUM_NOT_FIND("30006", "商户业务没有对应状态类型","30006", "商户业务没有对应状态类型"),
    MERCHANT_BIZ_STATUS_NOT_OPEN("30007", "商户业务没有开通","30007", "商户业务没有开通"),
    MERCHANT_BIZ_OFFLINE("30008", "商户业务离线","30008", "商户业务离线"),
    BIZ_STATUS_ENUM_NOT_FIND("30009", "业务没有对应状态类型","30009", "业务没有对应状态类型"),
    BIZ_NOT_EXIST("30010", "业务信息不存在","30010", "业务信息不存在"),
    BIZ_STATUS_NOT_OPEN("30011", "业务没有开通","30011", "业务没有开通"),
    ORDER_STATUS_ENUM_NOT_FIND("30012", "订单没有对应状态类型","30012", "订单没有对应状态类型"),
    CHANNEL_STATUS_ENUM_NOT_FIND("30013", "通道没有对应状态类型","30013", "通道没有对应状态类型"),
    USR_STATUS_NOT_OPEN("30015", "用户没有开通","30015", "用户没有开通"),
    ORDER_PAY_FAILED("30016", "订单支付失败","30016", "订单支付失败"),
    ORDER_PAY_SUCCESS("30017", "订单支付成功,请勿重新支付","30017", "订单支付成功,请勿重新支付"),
    ORDER_PAYING("30018", "订单支付中","30018", "订单支付中"),
    BANK_STATUS_ENUM_NOT_FIND("30019", "银行没有对应状态类型","30019", "银行没有对应状态类型"),
    BIND_CARD_STATUS_ENUM_NOT_FIND("30020", "绑卡没有对应状态类型","30020", "绑卡没有对应状态类型"),
    CHANNEL_NOT_EXIST("30021", "通道信息不存在","30021", "通道信息不存在"),
    CHANNEL_STATUS_NOT_OPEN("30022", "渠道没有开通","30022", "渠道没有开通"),
    CHANNEL_INF_STATUS_ENUM_NOT_FIND("30014", "通道接口没有对应状态类型","30014", "通道接口没有对应状态类型"),
    CHANNEL_INF_NOT_EXIST("30023", "通道接口不存在","30023", "通道接口不存在"),
    CHANNEL_INF_SERV_CONDITION_NOT_SATISFY("30024", "所有通道接口服务商条件不满足","30024", "所有通道接口服务商条件不满足"),
    INF_TC_STATUS_ENUM_NOT_FIND("30025", "接口服务商没有对应状态类型","30025", "接口服务商没有对应状态类型"),
    SERV_STATUS_ENUM_NOT_FIND("30026", "服务商没有对应状态类型","30026", "服务商没有对应状态类型"),
    ORDER_NOT_EXIST("30027", "订单不存在","30027", "订单不存在"),
    BIND_CARD_NOT_EXIST("30028", "绑卡信息不存在","30028", "绑卡信息不存在"),
    BIND_CARD_STATUS_NOT_OPEN("30029", "绑卡没有开通","30029", "绑卡没有开通"),
    BANK_NOT_EXIST("30030", "银行信息不存在","30030", "银行信息不存在"),
    BANK_STATUS_NOT_OPEN("30031", "银行没有开通","30031", "银行没有开通"),
    CHANNEL_INF_STATUS_NOT_OPEN("30032","通道接口没有开通","30032", "通道接口没有开通"),
    INF_SERV_NOT_EXIST("30033", "接口服务商不存在","30033", "接口服务商不存在"),
    INF_SERV_STATUS_NOT_OPEN("30034", "接口服务商没有开通","30034", "接口服务商没有开通"),
    SERV_NOT_EXIST("30035", "服务商不存在","30035", "服务商不存在"),
    SERV_STATUS_NOT_OPEN("30036", "服务商没有开通","30036", "服务商没有开通"),
    INF_NOT_EXIST("30037", "接口不存在","30037", "接口不存在"),
    INF_STATUS_ENUM_NOT_FIND("30039", "接口没有对应状态类型","30039", "接口没有对应状态类型"),
    USER_STATUS_NOT_OPEN("30040", "用户没有开通","30040", "用户没有开通"),
    USER_NOT_EXIST("30041", "用户不存在","30041", "用户不存在"),
    INF_STATUS_NOT_OPEN("30042", "接口没有开通","30042", "接口没有开通"),
    CHANNEL_INF_NOT_OPEN("30043", "通道接口没有开通","30043", "通道接口没有开通"),
    MERCHANT_USR_NOT_EXIST("30044", "商户用户信息不存在","30044", "商户用户信息不存在"),
    BEAN_NOT_EXIST("30045", "接口协议bean信息不存在","30045", "接口协议bean信息不存在"),
    BEAN_TRANSFER_EXCEPTION("30046", "接口协议bean转换异常","30046", "接口协议bean转换异常"),
    USER_CARD_ALREADY_EXIST("30047", "用户已经绑卡，无需重新绑卡","30047", "用户已经绑卡，无需重新绑卡"),
    USR_STATUS_ENUM_NOT_FIND("30048", "用户没有对应状态类型","30048", "用户没有对应状态类型"),
    AMOUNT_LESS_MIN_AMT("30049", "支付金额小于接口最小交易额","30049", "支付金额小于接口最小交易额"),
    AMOUNT_GREAT_MAX_AMT("30050", "支付金额大于接口最大交易额","30050", "支付金额大于接口最大交易额"),
    INF_CONTRACT_EXPIRE("30051", "接口合约已经过期","30051", "接口合约已经过期"),
    INF_CERT_EXPIRE("30052", "接口证书已经过期","30052", "接口证书已经过期"),
    SERV_NOT_TRADE("30053", "交易不在服务商时间范围内","30053", "交易不在服务商时间范围内");



    private String code;
    private String message;
    private String icode;
    private String imessage;

    public static RespCodeTypeEnum getRespCodeTypeEnumByCode(String code) {
        RespCodeTypeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RespCodeTypeEnum respCodeTypeEnum = var1[var3];
            if (respCodeTypeEnum.code.equals(code)) {
                return respCodeTypeEnum;
            }
        }

        return null;
    }

    private RespCodeTypeEnum(String code, String message, String icode, String imessage) {
        this.code = code;
        this.message = message;
        this.icode = icode;
        this.imessage = imessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcode() {
        return icode;
    }

    public void setIcode(String icode) {
        this.icode = icode;
    }

    public String getImessage() {
        return imessage;
    }

    public void setImessage(String imessage) {
        this.imessage = imessage;
    }}
