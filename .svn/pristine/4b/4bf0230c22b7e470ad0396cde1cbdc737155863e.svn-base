package com.hengzhiyi.it.pic.vo;

public class PagedVO<T> extends BaseVO
{
	private static final long serialVersionUID = 8653468207086314995L;

	/**
	 * 数据
	 */
	private T rows;

	/**
	 * 总数据条目
	 */
	private int total;

	/**
	 * 当前页,默认第一页
	 */
	private int currPage = 1;

	/**
	 * 页大小，默认每页20条
	 */
	private int pageSize = 20;

	/**
	 * 总页数
	 */
	private int pages;

	private String keyWord;

	private String userId;
	
	/**
	 * 响应状态(区分 成功 和 失败)
	 */
	private int resStatus;
	
	private Object params;
	
	public PagedVO()
	{
		
	}
	public PagedVO(int resStatus)
	{
		this.resStatus = resStatus;
	}

	/**
	 * 获取当前页的开始位置
	 * 
	 * @return
	 */
	public int getBeginIndex()
	{
		return (this.currPage - 1) * this.pageSize + 1;
	}

	/**
	 * 获取当前页的结束位置
	 * 
	 * @return
	 */
	public int getEndIndex()
	{
		return this.currPage * this.pageSize;
	}

	public T getRows()
	{
		return rows;
	}

	public void setRows(T rows)
	{
		this.rows = rows;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public int getCurrPage()
	{
		return currPage;
	}

	public void setCurrPage(int currPage)
	{
		this.currPage = currPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getPages()
	{
		// 计算总页数
		this.calcPages();
		return pages;
	}

	public void setPages(int pages)
	{
		this.pages = pages;
	}

	public String getKeyWord()
	{
		return keyWord;
	}

	public void setKeyWord(String keyWord)
	{
		this.keyWord = keyWord;
	}

	public int getResStatus()
	{
		return resStatus;
	}

	public void setResStatus(int resStatus)
	{
		this.resStatus = resStatus;
	}

	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public Object getParams()
	{
		return params;
	}
	public void setParams(Object params)
	{
		this.params = params;
	}
	/**
	 * 计算总页数
	 */
	private void calcPages()
	{
		if (this.total % this.pageSize == 0)
		{
			this.pages = this.total / this.pageSize;
		} else
		{
			this.pages = (this.total / this.pageSize) + 1;
		}
	}

	/**
	 * 响应状态
	 * 
	 * @author liutianlong
	 *
	 */
	public static class PagedResponseStatus
	{

		public final static int SUCCESS = 200; // 成功

		public final static int ERROR = 100; // 失败
	}

}
