package com.wlh.wpd.common.hibernate.util;

/**
 * 查询条件实现类 查询条件类
 */
public class QueryCondition
{

    /** property必须是实体对象的属性名,大小写敏感,不是表的字段名 */
    private String property;

    /** 要查询的对象 */
    private Object value;

    /** 查询比较符, 缺省为等于要 */
    private QueryOperator operator = QueryOperator.EQ;

    /**
     * 缺省构造函数
     */
    public QueryCondition()
    {
    }

    /**
     * 构造函数
     * @param property 要查询的条件
     * @param operator 比较符
     * @param value 要查询的对象
     */
    public QueryCondition(String property, QueryOperator operator, Object value)
    {
        this.property = property;
        this.operator = operator;

        // 如果是like操作, 需要将查询条件中的'*', 替换成通配符'%'
        switch (operator)
        {
            case SLIKE:
            case ELIKE:
            case LIKE:
            case EXACTLIKE:
            case ISLIKE:
            case IELIKE:
            case IEXACTLIKE:
            case ILIKE:
                /*
                 * { this.value = ((String) value).replace('*', '%'); this.value
                 * = ((String) value).replace("%", "/%"); break; }
                 */
            default:
            {
                this.value = value;
            }
        }
    }

    /**
     * 构造函数
     * @param property 要查询的条件
     * @param value 要查询的对象
     */
    public QueryCondition(String property, Object value)
    {
        // 缺省比较符为eq
        this.property = property;
        this.value = value;
    }

    /**
     * 比较符的get方法
     * @return
     */
    public QueryOperator getOperator()
    {
        return operator;
    }

    /**
     * 比较符的set方法
     * 
     * @param operator 比较符
     */
    public void setOperator(QueryOperator operator)
    {
        this.operator = operator;
    }

    /**
     * 属性名的get方法
     * @return
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * 属性名的set方法
     * @param property 属性
     */
    public void setProperty(String property)
    {
        this.property = property;
    }

    /**
     * 查询对象的get方法
     * @return
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * 查询对象的set方法
     * @param value
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
}
