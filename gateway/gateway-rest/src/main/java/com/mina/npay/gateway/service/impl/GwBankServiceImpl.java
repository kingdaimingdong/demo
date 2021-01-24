package com.mina.npay.gateway.service.impl;

import com.mina.npay.gateway.entity.GwBank;
import com.mina.npay.gateway.mapper.GwBankMapper;
import com.mina.npay.gateway.service.IGwBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行 服务实现类
 * </p>
 *
 * @author daimingdong
 * @since 2020-02-28
 */
@Service
public class GwBankServiceImpl extends ServiceImpl<GwBankMapper, GwBank> implements IGwBankService {

}
