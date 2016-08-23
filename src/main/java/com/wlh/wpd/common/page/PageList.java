package com.wlh.wpd.common.page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页列表基类
 */
public class PageList<E> extends ArrayList<E>
{
    private static final long serialVersionUID = -7420832397299066231L;

    private int totalRows = 0;

    private int totalPages = 0;

    private int pageSize = 10;

    private int currentPage = 1;

    private boolean hasPrevious = false;

    private boolean hasNext = false;

    private boolean showPageLine = false;

    public boolean isShowPageLine()
    {
        return showPageLine;
    }

    public void setShowPageLine(boolean showPageLine)
    {
        this.showPageLine = showPageLine;
    }

    public PageList()
    {
        super();
    }

    public PageList(Collection<? extends E> c)
    {
        super(c);
    }

    public PageList(int currentPage, int pageSize, int totalRows)
    {
        super(pageSize);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
        totalPages = totalRows / pageSize;
        int mod = totalRows % pageSize;
        if (mod > 0)
        {
            totalPages++;
        }
        refresh();
    }

    public void reset()
    {
        this.currentPage = 1;
        refresh();
    }

    public void init(int totalRows)
    {
        this.totalRows = totalRows;
        totalPages = ((totalRows + pageSize) - 1) / pageSize;
        refresh();
    }

    public void init(int totalRows, int pageSize)
    {
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        totalPages = ((totalRows + pageSize) - 1) / pageSize;
        refresh();
    }

    public int getCurrentPage()
    {

        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;

        refresh();
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
        refresh();
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
        refresh();
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
        refresh();
    }

    public void first()
    {
        currentPage = 1;
        this.setHasPrevious(false);
        refresh();
    }

    public void previous()
    {
        currentPage--;
        refresh();
    }

    public void next()
    {
        if (currentPage < totalPages)
        {
            currentPage++;
        }
        refresh();
    }

    public void last()
    {
        currentPage = totalPages;
        this.setHasNext(false);
        refresh();
    }

    public boolean isHasNext()
    {
        return hasNext;
    }

    public void setHasNext(boolean hasNext)
    {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious()
    {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious)
    {
        this.hasPrevious = hasPrevious;
    }

    public void refresh()
    {
        if (totalPages <= 1)
        {
            hasPrevious = false;
            hasNext = false;
        }
        else if (currentPage == 1)
        {
            hasPrevious = false;
            hasNext = true;
        }
        else if (currentPage == totalPages)
        {
            hasPrevious = true;
            hasNext = false;
        }
        else
        {
            hasPrevious = true;
            hasNext = true;
        }

        if (currentPage <= 0)
        {
            currentPage = 1;
        }

        // 有下一页，且当每页条数大于10，则显示上面的分页栏
        if (hasNext && pageSize > 10)
        {
            this.showPageLine = true;
        }

        // 没有下一页，而且当每页条数小于10，则不显示上面的分页栏
        if (!hasNext && pageSize < 10)
        {
            this.showPageLine = false;
        }

        // 没有下一页，而且每页条数大于10，则显示上面的分页栏
        if (!hasNext && pageSize > 10)
        {
            this.showPageLine = true;
        }
    }
}
