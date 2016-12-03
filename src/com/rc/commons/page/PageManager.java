package com.rc.commons.page;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PageManager {
	public PageManager() {
	}

	public static PageWraper getPageWraper(String iSql, int page, int pageSize,
			Session session) throws Exception {
		// Transaction tx = null;
		PageInfo pageInfo = new PageInfo();
		PageWraper pageWraper = new PageWraper();
		// Vector vResults = new Vector(2);
		String sql = iSql.toString().trim();
		String sqlWithOutOderBy = delOrderBy(sql);

		if (page <= 0)
			page = 1;
		try {

			int fromPosition = sqlWithOutOderBy.toUpperCase().indexOf(" FROM ") + 1;
			String cou_sql = "";
			if (fromPosition > 0) {
				cou_sql = "select count(*) "
						+ sqlWithOutOderBy.substring(fromPosition);
			} else {
				cou_sql = "select count(*) " + sqlWithOutOderBy;
			}

			// 取得总条数
			int count = getPageCount(cou_sql, session);

			// 请求的页面范围，如果传入的pageSize=0则返回全部
			int start = 0;
			int end = 0;
			int prePage = 0;
			int nextPage = 0;
			int pages = 0;

			if (pageSize != 0) {
				start = (page - 1) * pageSize;
				end = page * pageSize;
				pages = count / pageSize;
				// if(pages==0)pages=1;
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
					start = (page - 1) * pageSize;
					page = pages;
					prePage = pages - 1;
					nextPage = pages;
				}
				if (start > count) {
					/*
					 * start = 0; end = start + pageSize; if (end > count) { end =
					 * count; } page = 1; prePage = 1; if (pages > 1) { nextPage =
					 * 2; } else { nextPage = 1; }
					 */
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
			// FileLogger.info("page :"+page+" pageSize :"+pageSize+" pages
			// :"+pages+" start :"+start+" end :"+end);
			// 取出页面列表
			List results = getPageList(sql.toString(), start, end, pageSize,
					session);
			// SessionUtil.closeSession(session);

			// 保存翻页信息对象
			pageInfo.setPage(page);
			pageInfo.setPageSize(pageSize);
			pageInfo.setCount(count);
			pageInfo.setPages(pages);
			pageInfo.setPrePage(prePage);
			pageInfo.setNextPage(nextPage);
			pageInfo.setStart(start + 1);
			pageInfo.setEnd(end);

			// 生成Vector
			pageWraper.setPageInfo(pageInfo);
			pageWraper.setResult(results);

		} catch (Exception he) {
			// if (tx != null) {
			// tx.rollback();
			// he.printStackTrace();
			// }
			throw he;
		} finally {
			try {
				if (session != null) {
					// session.close();
				}
			} catch (Exception e) {
			}
		}
		// 返回对象
		return pageWraper;
	}

	// private static int getPageCounttimes=1;
	// private static int getPageListtimes=1;

	private static int getPageCount(String sql, Session s) throws Exception {
		// FileLogger.info("getPageCounttimes :"+getPageCounttimes++);
		// Transaction tx = null;
		int count = 0;
		try {
			// tx = s.beginTransaction();
			Query q = s.createQuery(sql);
			List list = q.list();
			if (list.size() > 0) {
				count = Integer.parseInt(list.get(0).toString());
			} else {
				count = 0;
			}
			// tx.commit();
		} catch (Exception he) {
			// if (tx != null) {
			// tx.rollback();
			// }
			// FileLogger.printStackTrace(he);
			// he.printStackTrace();
			throw he;
		} finally {
		}
		return count;
	}

	private static List getPageList(String sql, int start, int end,
			int pageSize, Session s) throws HibernateException {
		// FileLogger.info("getPageListtimes :"+getPageListtimes++);
		// Transaction tx = null;
		List results = null;
		try {
			// tx = s.beginTransaction();
			Query q = s.createQuery(sql);
			// 如果传入的pageSize=0则返回全部
			if (pageSize != 0) {
				// System.out.println(start + "*to*" + end);
				q.setFirstResult(start);
				q.setMaxResults(end - start);
			}
			results = q.list();
			// System.out.println("results size:" + results.size());
			// tx.commit();
		} catch (HibernateException he) {
			// if (tx != null) {
			// tx.rollback();
			he.printStackTrace();
			// }
			throw he;
		} finally {
		}
		return results;
	}

	/**
	 * 删除order by
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	private static String delOrderBy(String sql) {
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
	
	public static void main(String[] args) {
		String str = " select  fromEmail from email ";
		System.out.println(str
				.substring(str.toUpperCase().indexOf(" FROM ") + 1));
	}
	
}
