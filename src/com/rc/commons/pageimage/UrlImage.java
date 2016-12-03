package com.rc.commons.pageimage;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

/**  
* @Title: Main.java
* @Description: 生成网站快照
* @author yinbinhome@163.com
* @date 2013-11-25 上午11:24:38
* @version V1.0  
*/
public class UrlImage extends JPanel {
	/**
  * 
  */
	private static final long serialVersionUID = 1L;

	// 行分隔符
	final static public String LS = System.getProperty("line.separator", "\n");

	// 文件分割符
	final static public String FS = System.getProperty("file.separator", "\\");

	// 以javascript脚本获得网页全屏后大小
	final static StringBuffer jsDimension;

	static {
		jsDimension = new StringBuffer();
		jsDimension.append("var width = 0;").append(LS);
		jsDimension.append("var height = 0;").append(LS);
		jsDimension.append("if(document.documentElement) {").append(LS);
		jsDimension.append("  width = Math.max(width, document.documentElement.scrollWidth);").append(LS);
		jsDimension.append("  height = Math.max(height, document.documentElement.scrollHeight);").append(LS);
		jsDimension.append("}").append(LS);
		jsDimension.append("if(self.innerWidth) {").append(LS);
		jsDimension.append("  width = Math.max(width, self.innerWidth);").append(LS);
		jsDimension.append("  height = Math.max(height, self.innerHeight);").append(LS);
		jsDimension.append("}").append(LS);
		jsDimension.append("if(document.body.scrollWidth) {").append(LS);
		jsDimension.append("  width = Math.max(width, document.body.scrollWidth);").append(LS);
		jsDimension.append("  height = Math.max(height, document.body.scrollHeight);").append(LS);
		jsDimension.append("}").append(LS);
		jsDimension.append("return width + ':' + height;");
	}

	public UrlImage(final String url, final int maxWidth, final int maxHeight,String filepath) {
		super(new BorderLayout());
		JPanel webBrowserPanel = new JPanel(new BorderLayout());
	//	final String fileName = "d:/"+System.currentTimeMillis() + ".jpg";
		final String fileName = filepath;
		final JWebBrowser webBrowser = new JWebBrowser(null);
		webBrowser.setBarsVisible(false);
		webBrowser.navigate(url);
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		add(webBrowserPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));

		webBrowser.addWebBrowserListener(new WebBrowserAdapter() {

			// 监听加载进度
			public void loadingProgressChanged(WebBrowserEvent e) {
				// 当加载完毕时
				if (e.getWebBrowser().getLoadingProgress() == 100) {
					String result = (String) webBrowser.executeJavascriptWithResult(jsDimension.toString());
					int index = result == null ? -1 : result.indexOf(":");
					NativeComponent nativeComponent = webBrowser.getNativeComponent();
					Dimension originalSize = nativeComponent.getSize();
					Dimension imageSize = new Dimension(Integer.parseInt(result.substring(0, index)), Integer.parseInt(result.substring(index + 1)));
					imageSize.width = Math.max(originalSize.width, imageSize.width + 50);
					imageSize.height = Math.max(originalSize.height, imageSize.height + 50);
					nativeComponent.setSize(imageSize);
					BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
					nativeComponent.paintComponent(image);
					nativeComponent.setSize(originalSize);
					// 当网页超出目标大小时
//					if (imageSize.width > maxWidth || imageSize.height > maxHeight) {
//						// 截图部分图形
//						image = image.getSubimage(0, 0, maxWidth, maxHeight);
//						/*
//						 * 此部分为使用缩略图 int width = image.getWidth(), height =
//						 * image .getHeight(); AffineTransform tx = new
//						 * AffineTransform(); tx.scale((double) maxWidth /
//						 * width, (double) maxHeight / height);
//						 * AffineTransformOp op = new AffineTransformOp(tx,
//						 * AffineTransformOp.TYPE_NEAREST_NEIGHBOR); //缩小 image
//						 * = op.filter(image, null);
//						 */
//					}
					try {
						// 输出图像
						ImageIO.write(image, "jpg", new File(fileName));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					// 退出操作
					System.exit(0);
				}
			}
		}

		);
		add(panel, BorderLayout.SOUTH);

	}

	/**
	 * 说明:此方法仅在windowns环境下使用
	 * @param url
	 * @param filePath
	 */
	public static void createUrlImage_win(final String url,final String filePath){

		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// SWT组件转Swing组件，不初始化父窗体将无法启动webBrowser
				JFrame frame = new JFrame("以DJ组件保存指定网页截图");
				// 加载google，最大保存为640x480的截图
				frame.getContentPane().add(new UrlImage(url, 640, 480,filePath), BorderLayout.CENTER);
				frame.setSize(800, 600);
				// 仅初始化，但不显示
				frame.invalidate();
				frame.pack();
				frame.setVisible(false);
			}
		});
		NativeInterface.runEventPump();
	
	}
	
	/**
	 * 说明:linux下使用，但是需要和wkhtmltoimage配合使用
	 * @param url
	 * @param filePath
	 */
	public static void createUrlImage(final String url,final String filePath){

		String cmd = "wkhtmltoimage "+url+" "+filePath;

		try {
			Process process = Runtime.getRuntime().exec(cmd);
			System.out.println(cmd);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
		} catch (java.io.IOException e) {
			System.err.println("IOException " + e.getMessage());
		}
	
	}
	public static void main(String[] args) {
/*		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// SWT组件转Swing组件，不初始化父窗体将无法启动webBrowser
				JFrame frame = new JFrame("以DJ组件保存指定网页截图");
				// 加载google，最大保存为640x480的截图
				frame.getContentPane().add(new UrlImage("http://item.jd.com/1000215.html", 640, 480,"D:/asdf.jpg"), BorderLayout.CENTER);
				frame.setSize(800, 600);
				// 仅初始化，但不显示
				frame.invalidate();
				frame.pack();
				frame.setVisible(false);
			}
		});
	//	NativeInterface.runEventPump();
*/		File file = new File("D:/asd");
		file.mkdir();
		createUrlImage("http://item.jd.com/1000215.html","D:/asd/asdasdfsad2.jpg");

	}
}