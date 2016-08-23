package com.wlh.wpd.common.hibernate.criterion;

import org.hibernate.criterion.MatchMode;
import org.hibernate.dialect.Dialect;

public class IlikeExpression extends LikeExpression
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8980345106191811622L;

    public IlikeExpression(String propertyName, Object value)
    {
        super(propertyName, value);
    }

    public IlikeExpression(String propertyName, String value,
            MatchMode matchMode)
    {
        super(propertyName, value, matchMode);

    }

    public IlikeExpression(String propertyName, String value,
            Character escapeChar)
    {
        super(propertyName, value, escapeChar);
    }

    @Override
    public String lhs(Dialect dialect, String column)
    {
        return dialect.getLowercaseFunction() + '(' + column + ')';
    }

    @Override
    public String typedValue(String value)
    {
        return super.typedValue(value).toLowerCase();
    }

}
