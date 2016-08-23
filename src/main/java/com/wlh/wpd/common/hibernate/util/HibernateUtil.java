package com.wlh.wpd.common.hibernate.util;

import java.util.List;

import org.hibernate.HibernateException;
//import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.wlh.wpd.common.page.PageList;
import com.wlh.wpd.common.page.Paginator;


/**
 * <Hibernate工具类> <hibernate工具类,主要提供查询功能>
 */
public class HibernateUtil
{
    /**
     * <从hql查询语句得到相应的分页结果list>
     * @param hibernatTemplate
     * @param paginator
     * @param queryString
     * @return
     * @return List [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static List<?> getQueryList(HibernateTemplate hibernateTemplate,
            Paginator paginator, String queryString)
    {
        Integer total = 0;
        List<?> returnList = null;
        try
        {
            // 解析hql语句
            String tempHql = queryString.toLowerCase();
            int fromStart = tempHql.indexOf("from");
            // 没有from语句,查询语句错误
            if (fromStart == -1)
            {
                return null;
            }
            int orderStart = tempHql.indexOf("order");
            String countString = "";
            if (orderStart != -1)
            {
                countString = " select count(*) "
                        + queryString.substring(fromStart, orderStart);
            }
            else
            {
                countString = " select count(*) "
                        + queryString.substring(fromStart);
            }

            // 使用hibernateTemplate对象查询结果
            List<?> queryList = hibernateTemplate.find(countString);
            // 结果不为空
            if (!queryList.isEmpty())
            {
                long count = (Long) queryList.get(0);

                total = Integer.valueOf(count + "");
            }
            // 具体查询
            returnList = getQuery(hibernateTemplate, paginator, queryString,
                    total);
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        // 带分页参数的列表
        PageList pageList = new PageList(paginator.getPageIndex(), paginator
                .getPageSize(), total);
        if (null != returnList)
        {
            pageList.addAll(returnList);
        }
        return pageList;

    }

    /**
     * <从hql查询语句得到相应的分页结果list ,使用参数数组的方式>
     * @param hibernatTemplate
     * @param paginator
     * @param queryString 查询语句
     * @param args
     * @return List [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<?> getQueryListByValue(
            HibernateTemplate hibernatTemplate, Paginator paginator,
            String queryString, Object[] args)

    {
        List<?> resultList = null;
        try
        {
            // 解析hql语句
            String tempHql = queryString.toLowerCase();
            int fromStart = tempHql.indexOf("from");
            // 查询语句中没有from关键字

            if (fromStart == -1)
            {
                return null;
            }
            int orderStart = tempHql.indexOf("order");
            String countString = "";
            if (orderStart != -1)
            {
                countString = " select count(*) "
                        + queryString.substring(fromStart, orderStart);
            }
            else
            {
                countString = " select count(*) "
                        + queryString.substring(fromStart);
            }
            Integer total = 0;
            // 转码
            // countString=StringUtil.ISOtoUTF8(countString);
            List<?> queryList = hibernatTemplate.find(countString, args);
            if (!queryList.isEmpty())
            {
                total = (Integer) queryList.get(0);
            }

            resultList = queryListByValue(hibernatTemplate, paginator,
                    queryString, args, total);
        }

        catch (Exception ex)
        {
            // 异常,记录erro
            ex.printStackTrace();
        }
        return resultList;
    }

    /**
     * <根据spring回调方法得到查询记录> <功能详细描述>
     * @param hibernatTemplate
     * @param paginator
     * @param queryString
     * @param args
     * @param total
     * @return [参数说明]
     * @return List [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static List<?> queryListByValue(HibernateTemplate hibernatTemplate,
            final Paginator paginator, final String queryString,
            final Object[] args, final Integer total)
    {
        List<?> resultList = (List<?>) hibernatTemplate
                .execute(new HibernateCallback()
                {
                    public Object doInHibernate(Session session)
                            throws HibernateException
                    {
                        Query query = session.createQuery(queryString);
                        if (args != null)
                        {
                            for (int i = 0; i < args.length; i++)
                            {
                                query.setParameter(i, args[i]);
                            }
                        }

                        // // 设置分页对象的总条数
                        // paginator.setTotalCount(total.intValue());
                        // // 设置查询第几页
                        // query.setFirstResult(paginator.getStartIndex());
                        // // 返回的记录条数最大为分页对象的每页最大大小
                        //
                        // query.setMaxResults(paginator.getPageSize());
                        return query.list();
                    }
                });
        return resultList;
    }

    /**
     * <根据spring回调方法得到查询记录> <回调填充分页对象中的总条数,并返回查询出的结果>
     * @param hibernatTemplate
     * @param paginator
     * @param queryString
     * @param total
     * @return [参数说明]
     * @return List [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static List<?> getQuery(HibernateTemplate hibernatTemplate,
            final Paginator paginator, final String queryString,
            final Integer total)
    {
        List<?> resultList = (List<?>) hibernatTemplate
                .execute(new HibernateCallback()
                {
                    public Object doInHibernate(Session session)
                            throws HibernateException
                    {
                        Query query = session.createQuery(queryString);
                        // 设置分页对象的总条数
                        // paginator.setTotalCount(total);
                        // 取分页对象当前页,以便查询对应的记录
                        query.setFirstResult(paginator.getStartIndex());
                        // 设置取出多少条记录
                        query.setMaxResults(paginator.getPageSize());
                        return query.list();
                    }
                });
        return resultList;
    }
}
