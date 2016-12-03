package com.rc.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <p>公共方法类</p>
 * <p>文件操作</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class FileUtil {
    /**
     *FileUtil的构造函数
     */
    public FileUtil() {
    }
    /**
     * 按照分隔符的分隔 得到文本类型的2维数组
     * @param is InputStream 输入流
     * @param sepor String 分隔符
     * @throws IOException IO异常
     * @return String[][]
     */
    public static String[][] getTextContent(InputStream is, String sepor) throws IOException {
        String strText = getString(is);
        return ArrayUtil.getStrArr2DSepByLine(strText, sepor);
    }
    /**
     * 从Excel文件中 得到文本类型的2维数组
     * @param is InputStream
     * @throws IOException
     * @return String[][]
     */
    public static String[][] getExcelContent(InputStream is) throws IOException {
        // 在一个输入流内得到一个工作簿
        HSSFWorkbook workBook = new HSSFWorkbook(is);
        HSSFSheet sheet = workBook.getSheetAt(0);
        HSSFRow curRow = null;
        HSSFCell curCell = null;

        String[][] strArr2D = null;
        final int rowSize = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;

        curRow = sheet.getRow(sheet.getFirstRowNum());
        if (curRow == null) {
            return null;
        }
        else {
            //以第一行列数为二维数组第二维(测试表明lastCellNum总是多一个，所以不再读取lastCellNum)
            final int colSize = curRow.getLastCellNum() - curRow.getFirstCellNum();
            strArr2D = new String[rowSize][colSize];
        }

        DecimalFormat format = new DecimalFormat();
        format.setGroupingSize(0);

        for (int i = sheet.getFirstRowNum(), ii = 0; i <= sheet.getLastRowNum(); i++, ii++) {
            curRow = sheet.getRow(i);
            if(curRow != null) {
                for (short j = curRow.getFirstCellNum(), jj = 0;
                     j < curRow.getLastCellNum() && jj < strArr2D[0].length; j++, jj++) {
                    curCell = curRow.getCell(j);
                    if (curCell != null) {
                        switch (curCell.getCellType()) {
                            case HSSFCell.CELL_TYPE_BLANK:
                                strArr2D[ii][jj] = "";
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                strArr2D[ii][jj] = "ERROR";
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                strArr2D[ii][jj] = String.valueOf(curCell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                strArr2D[ii][jj] = String.valueOf(curCell.getNumericCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(curCell)) {
                                    Date date = curCell.getDateCellValue();
                                    strArr2D[ii][jj] = DateUtil.dateToString(date,"yyyy-MM-dd");
                                }
                                else {
                                    //String format = HSSFDataFormat.getBuiltinFormat( curCell.getCellStyle().getDataFormat() );
                                    strArr2D[ii][jj] = format.format(curCell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                strArr2D[ii][jj] = curCell.getStringCellValue();
                                break;
                            default:
                                throw new IOException("非法的cellType:" + curCell.getCellType());
                        }
                    }
                }
            }
        }

        return strArr2D;
    }

    /**
     * 从文件中读出字符串
     * @param is 输入流
     * @return String
     * @exception IOException
     */
    public static String getString(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer("");
        while (true) {
            byte[] bytes = new byte[100];
            int iLen = is.read(bytes);
            if (iLen <= 0) {
                break;
            }
            sb.append(new String(bytes, 0, iLen, "GBK"));
        }
        return sb.toString();
    }
}
