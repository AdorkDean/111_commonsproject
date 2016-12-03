package com.rc.commons.validate;

/**
 * <p>公共方法类</p>
 * <p>校验身份证号码</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class ValidateCard implements Validator {
        /**
         *初始化
         */
        public void init() {
        }

        /**
         * 是否有效
         * @param value Object
         * @return String
         */
        public String validate(Object value) {
                String card = (String) value;
                return validateIDCard(card);
        }
        /**
         * 身份证号码是否合法
         * @param strValue String
         * @return String
         */
        private String validateIDCard(String strValue) {
                String msg = null;
                if (strValue == null)
                        return null;
                msg = is0AndPosInteger(strValue);
                if (null != msg) {
                        return "身份证号码输入不合法，请重新输入！";
                }
                int format = strValue.length();
                if (format == 15 || format == 18) {
                        if (format == 18) {
                                msg = validateCheckBit(strValue);
                                if (null != msg) {
                                        return  msg;
                                }
                        }
                        msg = checkDate(strValue, format);
                        if (null != msg) {
                                return msg;
                        }
                } else {
                        return "身份证的位数输入不正确，请重新输入！";
                }

                return null;

        }
        /**
         * 身份证最后一位的校验算法
         * @param inputstr String
         * @return String
         */
        private String validateCheckBit(String strValue) {
                int i = (Integer.parseInt(strValue.substring(0, 1)) * 7
                                + Integer.parseInt(strValue.substring(1, 2)) * 9
                                + Integer.parseInt(strValue.substring(2, 3)) * 10
                                + Integer.parseInt(strValue.substring(3, 4)) * 5
                                + Integer.parseInt(strValue.substring(4, 5)) * 8
                                + Integer.parseInt(strValue.substring(5, 6)) * 4
                                + Integer.parseInt(strValue.substring(6, 7)) * 2
                                + Integer.parseInt(strValue.substring(7, 8)) * 1
                                + Integer.parseInt(strValue.substring(8, 9)) * 6
                                + Integer.parseInt(strValue.substring(9, 10)) * 3
                                + Integer.parseInt(strValue.substring(10, 11)) * 7
                                + Integer.parseInt(strValue.substring(11, 12)) * 9
                                + Integer.parseInt(strValue.substring(12, 13)) * 10
                                + Integer.parseInt(strValue.substring(13, 14)) * 5
                                + Integer.parseInt(strValue.substring(14, 15)) * 8
                                + Integer.parseInt(strValue.substring(15, 16)) * 4 + Integer
                                .parseInt(strValue.substring(16, 17)) * 2) % 11;
                int last = 0;
                if (i > 2)
                        last = 12 - i;
                if (i == 2)
                        last = 'X';
                if (i < 2)
                        last = 1 - i;
                if (last == Integer.parseInt(strValue.substring(17)))
                        return null;
                else
                        return "身份证输入不合法！校验位错误，请重新输入！";
        }
        /**
         * 日期是否有效
         * @param date String
         * @return String
         */
        private String validateDate(String date) {
                String temp;
                int year, month, day;
                temp = date.substring(0, 4);
                year = Integer.parseInt(temp);

                temp = date.substring(4, 6);
                month = Integer.parseInt(temp);

                temp = date.substring(6, 8);
                day = Integer.parseInt(temp);
                if (year < 1900 || year > 2200)
                        return "年份应介于1900与2200之间，请重新输入！";

                if (month < 1 || month > 12)
                        return "月份必须介于1与12之间，请重新输入！";

                if ((day == 0) || (day > 31)) {
                        return "日必须介于0与31之间，请重新输入！";
                } else if (day > 28 && day < 31) {
                        if (month == 2) {
                                if (day != 29) {
                                        return year + "年" + month + "月无" + day + "日，请重新输入！";
                                } else {
                                        if ((year % 4) != 0) {
                                                return year + "年" + month + "月无" + day + "日，请重新输入！";
                                        } else {
                                                if ((year % 100 == 0) && (year % 400 != 0)) {
                                                        return year + "年" + month + "月无" + day + "日，请重新输入！";
                                                }
                                        }
                                }
                        }
                } else if (day == 31) {
                        if ((month == 2) || (month == 4) || (month == 6) || (month == 9)
                                        || (month == 11)) {
                                return month + "月无" + day + "日，请重新输入！";
                        }
                }
                return null;
        }
        /**
         * 返回身份证中的日期是否有效
         * @param inputStr String
         * @param format int
         * @return String
         */
        private String checkDate(String inputStr, int format) {
                String date = "";
                if (format == 18) {
                        date = inputStr.substring(6, 14);
                } else if (format == 15) {
                        date = "19" + inputStr.substring(6, 12);
                }
                return validateDate(date);
        }
        /**
         * 身份证是否由有效的字符组成
         * @param strValue String
         * @return String
         */
        private String is0AndPosInteger(String strValue) {
                int format = strValue.length();
                if (format == 18) {
                        char lastChar = strValue.charAt(strValue.length() - 1);
                        if (lastChar == 'X')
                                strValue = strValue.substring(0, strValue.length() - 1);
                }
                for (int i = 0; i < strValue.length(); i++) {
                        char oneChar = strValue.charAt(i);
                        if (oneChar < '0' || oneChar > '9') {
                                return "身份证号码输入不合法，请重新输入！";
                        }
                }
                return null;
        }
}
