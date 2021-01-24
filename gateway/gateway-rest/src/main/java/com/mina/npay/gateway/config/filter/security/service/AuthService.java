package com.mina.npay.gateway.config.filter.security.service;


import com.mina.npay.gateway.config.filter.security.domain.auth.ResponseUserToken;
import com.mina.npay.gateway.config.filter.security.domain.auth.UserDetail;

/**
 * 认证服务接口
 * @author: daimingdong
 */
public interface AuthService {
    /**
     * 注册用户
     * @param userDetail
     * @return
     */
    UserDetail register(UserDetail userDetail);

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

    /**
     * 刷新Token
     * @param oldToken
     * @return
     */
    ResponseUserToken refresh(String oldToken);

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    UserDetail getUserByToken(String token);
}
