package com.mina.npay.gateway.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mina.npay.gateway.entity.GwUsr;
import com.mina.npay.gateway.service.IGwUsrService;
import com.mina.npay.gateway.service.user.UserSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author  daimingdong
 */
@Slf4j
@Service
public class UserServiceImpl implements UserSerive {

    @Autowired
    public IGwUsrService iGwUsrService;

    @Override
    public void userAdd(String certNo,String Name,String phoneNo){

        QueryWrapper<GwUsr> usrWrapper = new QueryWrapper<>();
        usrWrapper.eq("id_no",certNo);
        GwUsr gwUsr = iGwUsrService.getOne(usrWrapper);
        if(gwUsr != null){
            log.info("user not existed!,certNo:{}",certNo);
            // relate channeluser todo;
            return ;

        }

        //add new user and relate channeluser todo;


    }
}
