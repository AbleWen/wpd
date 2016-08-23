package com.wlh.wpd.common.hibernate.util;

import java.io.Serializable;

/**
 * 排序条件实现类
 */
public class OrderByInfo implements Serializable
{
    private static final long serialVersionUID = -5052450233909170173L;

    /** 需排序的属性 必须是实体对象的属性名,大小写敏感,不是表的字段名 */
    private String propertyName;

    /** 升, 降序属性, 缺省为升序 */
    private boolean asc = true;

    /** 是否按拼音排序，缺省为false */
    private boolean pyOrder = false;

    /**
     * 构造函数
     */
    public OrderByInfo()
    {
    }

    /**
     * 构造函数
     * @param propertyName 属性名
     */
    public OrderByInfo(String propertyName)
    {
        this.propertyName = propertyName;
    }

    /**
     * 构造函数
     * @param propertyName 属性名
     * @param asc 升, 降序标志
     */
    public OrderByInfo(String propertyName, boolean asc)
    {
        this.asc = asc;
        this.propertyName = propertyName;
        this.pyOrder = false;
    }

    /**
     * 构造函数
     * @param propertyName 属性名
     * @param asc 升, 降序标志
     * @param pyOrder 按拼音排序
     */
    public OrderByInfo(String propertyName, boolean asc, boolean pyOrder)
    {
        this.asc = asc;
        this.propertyName = propertyName;
        this.pyOrder = pyOrder;
    }

    /**
     * 排序标志属性的get方法
     * @return 排序标志
     */
    public boolean isAsc()
    {
        return asc;
    }

    /**
     * 排序标志属性的set方法
     * @param asc true 升序 fase 降序
     */
    public void setAsc(boolean asc)
    {
        this.asc = asc;
    }

    public boolean isPyOrder()
    {
        return pyOrder;
    }

    public void setPyOrder(boolean pyOrder)
    {
        this.pyOrder = pyOrder;
    }

    /**
     * 排序属性名的get方法
     * @return 需排序的属性名
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * 排序属性名的set方法
     * @param propertyName 属性名称
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
