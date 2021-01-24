package com.mina.npay.gateway.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus 创建时间和更新时间字段填充实现类
 * @author daimingdong
 */
@Component
public class MybatisPlusMetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("cts", LocalDateTime.now(), metaObject);
        this.setFieldValByName("uts", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新数据执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("uts", LocalDateTime.now(), metaObject);
    }
}
