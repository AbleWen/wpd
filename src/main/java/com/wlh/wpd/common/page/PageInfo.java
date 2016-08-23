package com.wlh.wpd.common.page;

import java.io.Serializable;

/**
 * 查询条件的分页条件类实现
 */
public class PageInfo implements Serializable
{
    private static final long serialVersionUID = -2827227251993645482L;

    /** 分页大小，即每页最大记录数 */
    private int pageSize = -1;

    /** 页编号,即第几页,从0开始编号 */
    private int pageNo = 0;

    /** 总记录数 */
    private int totalCount = 0;

    /**
     * 缺省构造函数
     */
    public PageInfo()
    {
    }

    /**
     * 构造函数
     * @param pageSize 页大小, 即每页记录数
     */
    public PageInfo(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * 构造函数
     * @param pageSize 页大小, 即每页记录数
     * @param pageNo 页序号, 从0开始编号
     */
    public PageInfo(int pageSize, int pageNo)
    {
        this.pageSize = pageSize;
        setPageNo(pageNo);
    }

    /**
     * 计算总页数 计算方法为总记录数/每页大小
     * @return 查询结果集分页后的总页数
     * @throws
     */
    public int getPageCount()
    {
        // 如果没有结果集, 则总页数为 0
        if (0 == totalCount)
        {
            return 0;
        }

        // 如果未设置页大小, 则总页数为 1, 即所有结果一次返回
        if (pageSize < 1)
        {
            return 1;
        }

        // 否则, 由总记录数和每页大小, 计算出总页数
        return (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 页序号的get方法
     * @return 页号
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * 页序号的set方法
     * @param pageNo 页序号
     */
    public void setPageNo(int pageNo)
    {
        if (pageNo < 0)
        {
            pageNo = 0;
        }
        this.pageNo = pageNo;
    }

    /**
     * 页大小的get方法
     * @return 页大小
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * 页大小的set方法
     * @param pageSize 每页大小
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * 结果集大小的get方法
     * @return 结果集总记录数
     */
    public int getTotalCount()
    {
        return totalCount;
    }

    /**
     * 结果集大小的set方法
     * @param totalCount 结果集大小
     */
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
}
