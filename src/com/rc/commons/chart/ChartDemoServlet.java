package com.rc.commons.chart;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @version 	1.0
 * @author wind
 */
public class ChartDemoServlet extends HttpServlet implements Servlet
{

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{	
	
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{

	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
//		PrintWriter out=res.getWriter();
//		out.print("abcde");
		res.setContentType("image/jpeg");
		DefaultPieDataset data = getDataSet();
		JFreeChart chart =
			ChartFactory.createPieChart("test", data, true, false, false);
        
		ChartUtilities.writeChartAsJPEG(
			res.getOutputStream(),      		
			100,
			chart,
			400,//横坐标长
			300,//纵坐标长
			null);
		
			
	}
	/**
	 * 饼图所需要的简单数据集对象
	 * @return
	 */
	private static DefaultPieDataset getDataSet()
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("中国", 100);
		dataset.setValue("america", 200);
		dataset.setValue("asd", 300);
		dataset.setValue("asdf", 400);
		return dataset;
	}

}
