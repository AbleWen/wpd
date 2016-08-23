package com.wlh.wpd.common.hibernate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wlh.wpd.common.page.PageInfo;


/**
 * 查询规格 实现查询条件的组合, 包括查询条件, 分页, 排序
 */
public class QuerySpecification implements Serializable
{

    private static final long serialVersionUID = 8639666606028177358L;

    /** 查询where条件list，可有多个查询条件 */
    private List<QueryCondition> condList = new ArrayList<QueryCondition>();

    /** 查询时的order by排序信息list,可有多个order by字段 */
    private List<OrderByInfo> orderByList = new ArrayList<OrderByInfo>();

    /** 分页信息 */
    private PageInfo pageInfo = new PageInfo();

    /** 别名列表 */	
    private Map<String, String> aliasList = new ConcurrentHashMap<String, String>();

    /** 总记录数 */
    private int totalCount = 0;

    private String fetchModeField;

    /**
     * 缺省构造函数
     */
    public QuerySpecification()
    {
    }

    public QuerySpecification(String fetchModeField)
    {
        this.fetchModeField = fetchModeField;
    }

    /**
     * 构造函数
     * @param queryCondList 查询条件列表
     * @param orderByList 字段排序列表
     */
    public QuerySpecification(List<QueryCondition> queryCondList,
            ArrayList<OrderByInfo> orderByList)
    {
        // 设置查询条件
        if (null != queryCondList)
        {
            this.condList = queryCondList;
        }

        // 设置排序条件
        if (null != orderByList)
        {
            this.orderByList = orderByList;
        }
    }

    /**
     * 构造函数
     * @param queryCondList 查询条件列表
     * @param orderByList 字段排序列表
     * @param pageInfo 分页信息
     * @return
     * @throws
     */
    public QuerySpecification(List<QueryCondition> queryCondList,
            List<OrderByInfo> orderByList, PageInfo pageInfo)
    {
        // 设置查询条件
        if (null != queryCondList)
        {
            this.condList = queryCondList;
        }

        // 设置排序条件
        if (null != orderByList)
        {
            this.orderByList = orderByList;
        }

        // 设置分页条件
        if (null != pageInfo)
        {
            this.pageInfo = pageInfo;
        }
    }

    /**
     * 查询条件的get方法
     * @return 查询条件
     */
    public List<QueryCondition> getCondList()
    {
        return condList;
    }

    /**
     * 查询条件的set方法
     * @param condList 查询条件
     */
    public void setCondList(List<QueryCondition> condList)
    {
        if (null != condList)
        {
            this.condList = condList;
        }
    }

    /**
     * 排序条件的get方法
     * @return 排序条件
     */
    public List<OrderByInfo> getOrderByList()
    {
        return orderByList;
    }

    /**
     * 排序条件的set方法
     * @param orderByList 排序条件
     */
    public void setOrderByList(List<OrderByInfo> orderByList)
    {
        if (null != orderByList)
        {
            this.orderByList = orderByList;
        }
    }

    /**
     * 分页条件的get方法
     * @return
     */
    public PageInfo getPageInfo()
    {
        return pageInfo;
    }

    /**
     * 分页条件的set方法
     * @param pageInfo 分页条件
     */
    public void setPageInfo(PageInfo pageInfo)
    {
        if (null != pageInfo)
        {
            this.pageInfo = pageInfo;
        }
    }

    /**
     * 增加查询条件
     * @param cond 查询条件
     */
    public void addQueryCond(QueryCondition cond)
    {
        this.condList.add(cond);
    }

    /**
     * 增加排序条件
     * @param orderBy 排序条件
     */
    public void addOrderByInfo(OrderByInfo orderBy)
    {
        this.orderByList.add(orderBy);
    }

    /**
     * 设置页大小
     * @param pageSize 页大小
     */
    public void setPageSize(int pageSize)
    {
        pageInfo.setPageSize(pageSize);
    }

    /**
     * 页大小的get方法
     * @return 页大小
     */
    public int getPageSize()
    {
        return pageInfo.getPageSize();
    }

    /**
     * 设置起始页序号
     * @param pageNo
     */
    public void setPageNo(int pageNo)
    {
        pageInfo.setPageNo(pageNo);
    }

    /**
     * 取起始页序号
     * @return 页序号
     */
    public int getPageNo()
    {
        if (isPagingQuery())
        {
            return pageInfo.getPageNo();
        }
        else
        {
            return 0;
        }
    }

    /**
     * 取结果分页的总页数
     * @return 总页数
     */
    public int getPageCount()
    {
        if (isPagingQuery())
        {
            return pageInfo.getPageCount();
        }
        else
        {
            return 1;
        }
    }

    /**
     * 设置结果集大小
     * @param totalCount 结果集大小
     */
    public void setTotalCount(int totalCount)
    {
        if (isPagingQuery())
        {
            pageInfo.setTotalCount(totalCount);
        }
        else
        {
            this.totalCount = totalCount;
        }
    }

    /**
     * 取结果集大小
     * @return 结果集大小
     */
    public int getTotalCount()
    {
        if (isPagingQuery())
        {
            return pageInfo.getTotalCount();
        }
        else
        {
            return this.totalCount;
        }
    }

    /**
     * 是否分页标志
     * @return 分页标志 0 不分页 >0 分页
     */
    public boolean isPagingQuery()
    {
        return pageInfo.getPageSize() > 0;
    }

    /**
     * 清除排序条件
     */
    public void clearOrderByList()
    {
        this.orderByList.clear();

    }

    /**
     * 清除查询条件
     */
    public void clearQueryCondList()
    {
        this.condList.clear();
    }

    public boolean hasAlias()
    {
        return aliasList.isEmpty();
    }

    /**
     * @return 返回 aliasList
     */
    public Map<String, String> getAliasList()
    {
        return aliasList;
    }

    /**
     * @param 对aliasList进行赋值
     */
    public void setAliasList(Map<String, String> aliasList)
    {
        this.aliasList = aliasList;
    }

    /**
     * @return 返回 fetchModeField
     */
    public String getFetchModeField()
    {
        return fetchModeField;
    }

    /**
     * @param 对fetchModeField进行赋值
     */
    public void setFetchModeField(String fetchModeField)
    {
        this.fetchModeField = fetchModeField;
    }

}
