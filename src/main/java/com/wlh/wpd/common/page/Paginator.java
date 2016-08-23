package com.wlh.wpd.common.page;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.wlh.wpd.util.PropertyFileReader;



/**
 * 分页标志器
 */
public class Paginator implements Serializable
{

    private static final long serialVersionUID = 4923978372466686728L;

    // 每页显示记录数
    private int pageSize = 0;

    // 总记录数
    private int totalCount = 0;

    // 当前页号
    private int pageIndex = 1;

    // 当前页
    private int index = 0;

    /**
     * <默认构造函数>
     */
    public Paginator()
    {
        this.pageSize = 10;

        // 系统默认值10
        String strPageSize = PropertyFileReader.readData("pagesize");
        if (StringUtils.isNotEmpty(strPageSize))
        {
            this.setPageSize(Integer.valueOf(strPageSize));
        }
    }

    public int getPageIndex()
    {
        return pageIndex;
        // return totalCount > 0 ? pageIndex + 1 : pageIndex;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
        // if (pageIndex >= 1)
        // {
        // this.pageIndex = pageIndex - 1;
        // }
        // else
        // {
        // this.pageIndex = 0;
        // }
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
        int totalPage = this.getTotalPage();
        if (totalPage > 0 && totalPage < this.pageIndex + 1)
        {
            this.setPageIndex(totalPage);
        }
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        // if (pageSize >= 1)
        // {
        // this.pageSize = pageSize;
        // }
        this.pageSize = pageSize;
    }

    public int getStartIndex()
    {
        return pageSize * (pageIndex - 1);
    }

    public int getIndex()
    {
        return pageSize * pageIndex + ++index;
    }

    public int getEndIndex()
    {
        return pageSize * (pageIndex + 1);
    }

    public int getTotalPage()
    {
        return (int) Math.ceil((float) getTotalCount() / (float) pageSize);
    }

    /**
     * @return
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + pageIndex;
        result = prime * result + pageSize;
        result = prime * result + totalCount;
        return result;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Paginator other = (Paginator) obj;
        if (index != other.index)
            return false;
        if (pageIndex != other.pageIndex)
            return false;
        if (pageSize != other.pageSize)
            return false;
        if (totalCount != other.totalCount)
            return false;
        return true;
    }

    /**
     * 详细的描述
     * 
     * @return
     */
    public String toString()
    {
        String desc = "PageSize=" + this.getPageSize() + ", PageIndex ="
                + this.getPageIndex() + ", TotalPage=" + this.getTotalPage()
                + ", TotalCount=" + this.getTotalCount();
        return desc;
    }
}
