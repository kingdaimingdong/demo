package com.mina.npay.gateway.config.filter.security.service;

import com.mina.npay.gateway.config.filter.security.domain.auth.UserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 登陆身份认证
 * @author: daimingdong
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
//    private final AuthMapper authMapper;
//
//    public CustomUserDetailsServiceImpl(AuthMapper authMapper) {
//        this.authMapper = authMapper;
//    }

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
//        UserDetail userDetail = authMapper.findByUsername(name);
//        if (userDetail == null) {
//            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
//        }
//        Role role = authMapper.findRoleByUserId(userDetail.getId());
//        userDetail.setRole(role);
        UserDetail userDetail = new UserDetail(1,"daimingdong","123456");

        return userDetail;
    }
}
