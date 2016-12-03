package com.rc.commons.verifycode;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.commons.Constants;

/**
 * <p>公共方法类</p>
 * <p>生成图形校验码Servlet</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 */

public class VerifyCodeServlet extends HttpServlet {
	
	/**
	 * 更新Session中的图形校验码，并返回校验码图片PNG
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		VerifyCodeGenerator gener = new VerifyCodeGenerator();

		req.getSession().setAttribute(
			Constants.VERIFY_CODE,
			gener.getVerifyCode());

		res.setHeader("Cache-Control", "no-store");
		res.setContentType("image/png");
		ServletOutputStream out = res.getOutputStream();
		gener.exportPngImage(out);
		out.flush();
		out.close();
	}
	/**
	 * 类的销毁
	 */
	public void destroy() {
		super.destroy();
	}
	/**
	 * 初始化
	 * @param cfg ServletConfig
	 * @throws ServletException
	 */
	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
	}
}
