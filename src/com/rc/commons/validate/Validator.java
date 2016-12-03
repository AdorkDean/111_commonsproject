package com.rc.commons.validate;

import java.io.Serializable;

/**
 * <p>公共方法类</p>
 * <p>校验接口</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public interface  Validator extends Serializable{
        /**
         * 初始化
         */
        public void init();
       
        /**
         *校验指定的值,将校验完的错误描述信息（String类型)返回
         * @param  value 待校验的值
         * @return  String
         */
        public String validate(Object value);
}
