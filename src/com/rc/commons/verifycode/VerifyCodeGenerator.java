package com.rc.commons.verifycode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * <p>公共方法类</p>
 * <p>图形校验码生成辅助类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 */

public class VerifyCodeGenerator {
    /**
     * Comment for <code>codes</code>
     * 校验码
     */
    private int[] codes;
    /**
     * Comment for <code>image</code>
     * 根据校验码生成的图形
     */
    private RenderedImage image;
//    private static Random random=new Random();
    public VerifyCodeGenerator() {
        codes=genVerifyCode();
        image=genCodeImage(codes);
    }

    /**
     * 生成校验码
     * @return int[]
     */
    private int[] genVerifyCode() {
        int[] codes=new int[4];
        for (int i=0;i<codes.length;i++) {
            codes[i]=(int)(Math.random()*10);
        }
        return codes;
    }


    /**
     * 生成校验码图片
     * @param data - 校验码
     * @return - 图形
     */
    private RenderedImage genCodeImage(int[] data) {
        BufferedImage image=new BufferedImage(50,20,BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics g=image.getGraphics();
        g.setColor(Color.BLACK);
        int x=5;
        for (int i=0;i<codes.length;i++) {
            g.drawString(String.valueOf(codes[i]),x,15);
            x+=10;
        }
        return image;
    }

    /**
     * 获取校验码图形
     * @return RenderedImage
     */
    public RenderedImage getCodeImage() {
        return image;
    }

    /**
     * 将校验码输出为PNG图形格式
     * @param out 输出流
     * @throws IOException
     */
    public void exportPngImage(OutputStream out) throws IOException {
        exportImage("png",out);
    }

    /**
     * ��校验码输出为指定图形格式
     * @param format  图形格式，如"png"，"jpeg"等
     * @param out 输出流
     * @throws IOException
     */
    public void exportImage(String format, OutputStream out) throws IOException {
        ImageIO.write(image,format,out);
    }

    /**
     * @return Returns the codes.
     */
    public int[] getCodes() {
        return codes;
    }

    /**
     * 获取校验码的字符串形式
     * @return String
     */
    public String getVerifyCode() {
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<codes.length;i++) {
            sb.append(codes[i]);
        }
        return sb.toString();
    }
}
