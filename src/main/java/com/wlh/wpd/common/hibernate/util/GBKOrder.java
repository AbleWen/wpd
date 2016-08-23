package com.wlh.wpd.common.hibernate.util;

import java.sql.Types;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class GBKOrder extends Order
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -885693264599923285L;

    private final String encoding = "GBK";

    private boolean ascending;

    private boolean ignoreCase;

    private String propertyName;

    @Override
    public String toString()
    {
        return "CONVERT( " + propertyName + " USING " + encoding + " ) "
                + (ascending ? "asc" : "desc");
    }

    @Override
    public Order ignoreCase()
    {
        ignoreCase = true;
        return this;
    }

    /**
     * Constructor for Order.
     */
    public GBKOrder(String propertyName, boolean ascending)
    {
        super(propertyName, ascending);
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    /**
     * Constructor for Order.
     */
    public GBKOrder(String propertyName, String mode)
    {
        super(propertyName, mode.equalsIgnoreCase("ASC") ? true : false);
        this.ascending = mode.equalsIgnoreCase("ASC") ? true : false;
        this.propertyName = propertyName;
    }

    /**
     * Render the SQL fragment
     * 
     */
    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException
    {
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria,
                propertyName);
        Type type = criteriaQuery
                .getTypeUsingProjection(criteria, propertyName);
        StringBuffer fragment = new StringBuffer();
        for (int i = 0; i < columns.length; i++)
        {
            SessionFactoryImplementor factory = criteriaQuery.getFactory();
            boolean lower = ignoreCase
                    && type.sqlTypes(factory)[i] == Types.VARCHAR;
            if (lower)
            {
                fragment.append(factory.getDialect().getLowercaseFunction())
                        .append('(');
            }
            fragment.append("CONVERT( " + columns[i] + " USING " + encoding
                    + " )");
            if (lower)
            {
                fragment.append(')');
            }
            fragment.append(ascending ? " asc" : " desc");
            if (i < columns.length - 1)
            {
                fragment.append(", ");
            }
        }

        return fragment.toString();
    }

    /**
     * Ascending order
     * 
     * @param propertyName
     * @return Order
     */
    public static Order asc(String propertyName)
    {
        return new GBKOrder(propertyName, true);
    }

    /**
     * Descending order
     * 
     * @param propertyName
     * @return Order
     */
    public static Order desc(String propertyName)
    {
        return new GBKOrder(propertyName, false);
    }
}
