package com.rc.commons.chart;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCheck extends HttpServlet {
	private Font mFont = new Font("宋体", Font.PLAIN, 12); // 设置字体
	
	// 处理post
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg;charset=GB2312");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		
		// 取得一个1000-9999的随机数
		String s = "";

		int intCount = 0;

		intCount = (new Random()).nextInt(9999); //

		if (intCount < 1000)
			intCount += 1000;

		s = intCount + "";

		// 对session付值。
		HttpSession session = request.getSession(true);
		session.setAttribute("getImg", s);
		response.setContentType("image/gif");
		ServletOutputStream out = response.getOutputStream();
		BufferedImage image = new BufferedImage(35, 14,
				BufferedImage.TYPE_INT_RGB);
		Graphics gra = image.getGraphics();
		// 设置背景色
		gra.setColor(new Color(212, 212, 212));
		gra.fillRect(1, 1, 33, 12);
		// 设置字体色
		gra.setColor(Color.black);
		gra.setFont(mFont);
		// 输出数字
		char c;
		
		for (int i = 0; i < 4; i++) {
			c = s.charAt(i);
			gra.drawString(c + "", i * 7 + 4, 11); // 7为宽度，11为上下高度位置
		}
		
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
		// 将认证码存入SESSION
		request.getSession().setAttribute("checkCode", s);
	}
}