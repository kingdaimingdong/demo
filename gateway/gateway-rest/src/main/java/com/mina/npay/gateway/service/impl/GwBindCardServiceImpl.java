package com.mina.npay.gateway.service.impl;

import com.mina.npay.gateway.entity.GwBindCard;
import com.mina.npay.gateway.mapper.GwBindCardMapper;
import com.mina.npay.gateway.service.IGwBindCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户(用户/商户)绑定银行卡 服务实现类
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Service
public class GwBindCardServiceImpl extends ServiceImpl<GwBindCardMapper, GwBindCard> implements IGwBindCardService {

}
