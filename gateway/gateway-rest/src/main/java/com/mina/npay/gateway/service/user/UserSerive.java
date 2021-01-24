package com.mina.npay.gateway.service.user;

/**
 * 用户服务
 * @author daimingdong
 */
public interface UserSerive {

    /**
     * 用户添加
     * @param certNo
     * @param Name
     */
    public void userAdd(String certNo,String Name,String phoneNo);
}
