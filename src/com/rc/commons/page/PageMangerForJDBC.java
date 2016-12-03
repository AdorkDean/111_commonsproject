/*
 * 创建日期 2005-12-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.rc.commons.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;

/**
 * @author  
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PageMangerForJDBC {

	public PageMangerForJDBC() {
	}

	public static PageWraperForJDBC getPagePageWraperForJDBC(
		String iSql,
		int page,
		int pageSize,
		Connection connection)
		throws Exception {
		return getPageWraperForJDBC(iSql, page, pageSize, connection, 0);
	}

	public static PageWraperForJDBC getPageWraperForJDBCForGroupBy(
		String iSql,
		int page,
		int pageSize,
		Connection connection)
		throws Exception {
		return getPageWraperForJDBC(iSql, page, pageSize, connection, 1);
	}

	private static PageWraperForJDBC getPageWraperForJDBC(
		String iSql,
		int page,
		int pageSize,
		Connection connection,
		int type)
		throws Exception {
		PageInfo pageInfo = new PageInfo();
		PageWraperForJDBC pageWraperForJDBC = new PageWraperForJDBC();
		String sql = iSql.toString().trim();
		String sqlWithOutOderBy = delOrderBy(sql);
		if (page <= 0) {
			page = 1;
		}
		try {
			int fromPosition =
				sqlWithOutOderBy.toUpperCase().indexOf(" FROM ") + 1;
			String cou_sql = "";
			if (type == 0) {
				if (fromPosition > 0) {
					cou_sql =
						"select count(*) "
							+ sqlWithOutOderBy.substring(fromPosition);
				} else {
					cou_sql = "select count(*) " + sqlWithOutOderBy;
				}
			} else if (type == 1) {
				cou_sql = "select count(*) from (" + sqlWithOutOderBy + ")";
			}
			//		System.out.println("\n\n\n///////// cou_sql :"+cou_sql+"\n\n\n");
			//取得总条数
			int count = getPageCount(cou_sql, connection);

			//请求的页面范围，如果传入的pageSize=0则返回全部
			int start = 0;
			int end = 0;
			int prePage = 0;
			int nextPage = 0;
			int pages = 0;

			if (pageSize != 0) {
				start = (page - 1) * pageSize;
				end = page * pageSize;
				pages = count / pageSize;

				if ((count % pageSize) > 0) {
					pages += 1;
				}
				if (pages <= 0) {
					pages = PageConst.DEF_CURRENT_PAGE;
				}
				if (page > 1) {
					prePage = page - 1;
				} else {
					prePage = page;
				}
				if (page < pages) {
					nextPage = page + 1;
				} else {
					nextPage = page;
				}
				if (end > count) {
					end = count;
					page = pages;
					prePage = pages - 1;
					nextPage = pages;
				}
				if (end > count) {
					end = count;
					start = (page - 1) * pageSize;
					page = pages;
					prePage = pages - 1;
					nextPage = pages;
				}
				if (start > count) {
					/*  start = 0;
					   end = start + pageSize;
					   if (end > count) {
						 end = count;
					   }
					   page = 1;
					   prePage = 1;
					   if (pages > 1) {
						 nextPage = 2;
					   }
					   else {
						 nextPage = 1;
					   }*/
					end = count;
					page = pages;
					start = (page - 1) * pageSize;
					if (pages > 1) {
						prePage = pages - 1;
						nextPage = pages;
					} else {
						prePage = 1;
						nextPage = 1;
					}
				}
			}

			//取出页面列表
			ResultSet results =
				getPageList(sql.toString(), start, end, pageSize, connection);

			//保存翻页信息对象
			pageInfo.setPage(page);
			pageInfo.setPageSize(pageSize);
			pageInfo.setCount(count);
			pageInfo.setPages(pages);
			pageInfo.setPrePage(prePage);
			pageInfo.setNextPage(nextPage);
			pageInfo.setStart(start + 1);
			pageInfo.setEnd(end);

			//生成Vector
			pageWraperForJDBC.setPageInfo(pageInfo);
			pageWraperForJDBC.setResult(results);

		} catch (Exception he) {
			throw he;
		}
		return pageWraperForJDBC;
	}

	private static int getPageCount(String sql, Connection connection)
		throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception he) {
			throw he;
		} finally {
		}
		return count;
	}

	private static ResultSet getPageList(
		String sql,
		int start,
		int end,
		int pageSize,
		Connection connection)
		throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List results = null;
		try {
			//如果传入的pageSize=0则返回全部
			if (pageSize != 0) {
				if (start > 0) {
					sql = getLimitString(sql, true, start, end);
				} else {
					sql = getLimitString(sql, false, start, end);
				}
			}
			//		System.out.println("\n\n\n sql :"+sql+"\n\n\n");
			ps = connection.prepareStatement(sql);
			return ps.executeQuery();
		} catch (Exception he) {
			throw he;
		} finally {
		}
	}

	public static String getLimitString(
		String sql,
		boolean hasOffset,
		int begin,
		int end) {
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
			pagingSelect.append(
				"select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect
				.append(" ) row_ where rownum <= ")
				.append(end)
				.append(") where rownum_ > ")
				.append(begin);
		} else {
			pagingSelect.append(" ) where rownum <= ").append(end);
		}
		return pagingSelect.toString();
	}

	/**
	 * 删除order by
	 * @param sql String
	 * @return String
	 */
	public static String delOrderBy(String sql) {
		StringBuffer temp = new StringBuffer();
		sql = "(" + sql + ")";
		int position;
		String[] strArray = sql.split("order by");
		for (int i = 1; i < strArray.length; i++) {
			strArray[i] = ")";
		}
		for (int i = 0; i < strArray.length; i++) {
			temp.append(strArray[i]);
		}
		String result = temp.toString();
		if (temp.length() >= 2) {
			result = result.substring(1, result.length() - 1);
		}
		return result;
	}

	/**
	* 释放资源
	*/
	public static void closeResource(ResultSet rs, Session session) 
	{
		try 
		{
			if (rs != null)
				rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try
		{
			if (session != null && session.isOpen())
				session.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
