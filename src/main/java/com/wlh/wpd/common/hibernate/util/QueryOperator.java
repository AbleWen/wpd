package com.wlh.wpd.common.hibernate.util;

/**
 * 查询比较符定义 定义查询对象支持的比较符
 */
public enum QueryOperator
{
    EQ, // =
    IEQ, // = 时忽略大小写
    SLIKE, // 前like, like xxx%
    ELIKE, // 后like ,like %xxx
    LIKE, // like %xxx%
    EXACTLIKE, // like 'xx%xx_x'
    ISLIKE, // 前like,忽略大小写 like xxx%
    IELIKE, // 后like,忽略大小写 like %xxx
    IEXACTLIKE, // like 'xx%xx_x'
    ILIKE, // 忽略大小写 like %xxx%
    BETWEEN, IN, GT, // >
    LT, // <
    GE, // >=
    LE, // <=
    NE, // <>
    OR_EQ, // 可查询多个或者等于条件
    OR_IN, // 可查询多个或者in条件
    GE_OR_NUll, // >= or is null
    LE_OR_NUll; // <= or is null
}
