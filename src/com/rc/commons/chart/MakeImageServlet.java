package com.rc.commons.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class MakeImageServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "image/jpeg;charset=GB2312";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("image/jpeg");
		DefaultPieDataset data = getDataSet();
		JFreeChart chart =
			ChartFactory.createPieChart("test", data, true, false, false);

		ChartUtilities
			.writeChartAsJPEG(response.getOutputStream(), 100, chart, 400,
		//横坐标长
		300, //纵坐标长
		null);
	}
	/**
	 * 给定范围获得随机颜色
	 * @param fc int
	 * @param bc int
	 * @return Color
	 */
	private static DefaultPieDataset getDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("中国", 100);
		dataset.setValue("america", 200);
		dataset.setValue("asd", 300);
		dataset.setValue("asdf", 400);
		return dataset;
	}
	public void destroy() {
	}
}
