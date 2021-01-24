package com.mina.npay.gateway.config.filter.security.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分页结果DO
 * @author daimingdong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {
    private int page;
    private int rows;
    private int total;
    private T data;
}