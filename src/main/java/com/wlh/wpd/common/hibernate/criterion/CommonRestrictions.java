package com.wlh.wpd.common.hibernate.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

public class CommonRestrictions
{
    public static Criterion like(String propertyName, Object value)
    {
        return new LikeExpression(propertyName, value);
    }

    public static Criterion like(String propertyName, String value,
            MatchMode matchMode)
    {
        return new LikeExpression(propertyName, value, matchMode);
    }

    public static Criterion like(String propertyName, String value,
            Character escapeChar)
    {
        return new LikeExpression(propertyName, value, escapeChar);
    }

    public static Criterion ilike(String propertyName, Object value)
    {
        return new IlikeExpression(propertyName, value);
    }

    public static Criterion ilike(String propertyName, String value,
            MatchMode matchMode)
    {
        return new IlikeExpression(propertyName, value, matchMode);
    }

    public static Criterion ilike(String propertyName, String value,
            Character escapeChar)
    {
        return new IlikeExpression(propertyName, value, escapeChar);
    }
}
