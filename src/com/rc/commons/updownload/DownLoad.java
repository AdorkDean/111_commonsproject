package com.rc.commons.updownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Weiwenqi
 * @version 1.0
 * 
 */

public class DownLoad {
	public DownLoad() {
	}
	
	/**
	 * 下载
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            String
	 * @param isOnLine
	 *            boolean
	 * @throws Exception
	 */
	public void downLoad(HttpServletResponse response, String filePath,
			boolean isOnLine) throws Exception {
		File f = new File(filePath);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
        
		response.reset(); // 非常重要
		if (isOnLine) {   // 在线打开方式
			URL u = new URL("file:///" + filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename="
					+ f.getName());
			// 文件名应该编码成UTF-8
		} else {
			// 纯下载方式
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}
}
