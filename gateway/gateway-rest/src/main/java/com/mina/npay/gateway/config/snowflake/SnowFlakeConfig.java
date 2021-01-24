package com.mina.npay.gateway.config.snowflake;

import com.mina.npay.gateway.common.utils.SnowFlakeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法配置类
 * @author daimingdong
 */
@Configuration
public class SnowFlakeConfig {

    @Bean
    public SnowFlakeUtils snowFlakeUtils(){
        SnowFlakeUtils snowFlakeUtils = new SnowFlakeUtils(2,3);
        return snowFlakeUtils;
    }
}
