package com.wlh.wpd.common.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页组件的JavaBean
 */
public class PaginatedBean
{
    /** 记录总数 */
    private int totalRows = 0;

    /** 总页数 */
    private int totalPages = 0;

    /** 每页显示的记录数 */
    private int pageSize = 5;

    /** 当前页 */
    private int currentPage = 1;

    /** 请求的url */
    private String url;

    /** 查询字符串 */
    private String queryString;

    /** 分页标签的ID号 */
    private String id;

    /** 分页的记录数 */
    private PageList<Object> pageList;

    /** 隐藏域数 */
    private Map<String, String> hiddenParams;

    /** 每页显示大小的下拉框 */
    private List<String> pageSelected;

    /** 用于页面显示“每页”的标签说明 */
    private String pageSizeLabel;

    /** 用于页面显示“总共”的标签说明 */
    private String totalRowlLabel;

    /** 用于页面显示“页数”的标签说明 */
    private String totalPagesLabel;

    /** 用于页面显示“条”的标签说明 */
    private String pageSizeLabelPostfix;

    /** 用于页面显示“转到”的标签说明 */
    private String jumpLabel;

    public PaginatedBean()
    {
    }

    public PaginatedBean(String id, PageList<Object> pageList, String url)
    {
        if (pageList != null)
        {
            this.pageList = pageList;
            this.totalPages = pageList.getTotalPages();
            this.totalRows = pageList.getTotalRows();
            this.pageSize = pageList.getPageSize();
            this.currentPage = pageList.getCurrentPage();
        }
        else
        {
            this.pageList = new PageList<Object>();
        }
        this.id = id;
        this.url = url;
        this.hiddenParams = new HashMap<String, String>();
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public PageList<Object> getPageList()
    {
        return pageList;
    }

    public void setPageList(PageList<Object> pageList)
    {
        this.pageList = pageList;
    }

    public Map<String, String> getHiddenParams()
    {
        return hiddenParams;
    }

    public void setHiddenParams(Map<String, String> hiddenParams)
    {
        this.hiddenParams = hiddenParams;
    }

    public List<String> getPageSelected()
    {
        return pageSelected;
    }

    public void setPageSelected(List<String> pageSelected)
    {
        this.pageSelected = pageSelected;
    }

    public String getPageSizeLabel()
    {
        return pageSizeLabel;
    }

    public void setPageSizeLabel(String pageSizeLabel)
    {
        this.pageSizeLabel = pageSizeLabel;
    }

    public String getTotalRowlLabel()
    {
        return totalRowlLabel;
    }

    public void setTotalRowlLabel(String totalRowlLabel)
    {
        this.totalRowlLabel = totalRowlLabel;
    }

    public String getPageSizeLabelPostfix()
    {
        return pageSizeLabelPostfix;
    }

    public void setPageSizeLabelPostfix(String pageSizeLabelPostfix)
    {
        this.pageSizeLabelPostfix = pageSizeLabelPostfix;
    }

    public String getJumpLabel()
    {
        return jumpLabel;
    }

    public void setJumpLabel(String jumpLabel)
    {
        this.jumpLabel = jumpLabel;
    }

    public String getTotalPagesLabel()
    {
        return totalPagesLabel;
    }

    public void setTotalPagesLabel(String totalPagesLabel)
    {
        this.totalPagesLabel = totalPagesLabel;
    }

    public String getQueryString()
    {
        return queryString;
    }

    public void setQueryString(String queryString)
    {
        this.queryString = queryString;
    }
}
