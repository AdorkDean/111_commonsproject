package com.rc.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>公共方法类</p>
 * <p>保存常量数对象的HashMap，并且按照使用频率排序,
 * 最近使用的其索引最大</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class TransiantHashMap implements java.io.Serializable {
        int MAXLENGTH = 20;
        List keys = null;
        List values = null;

        public TransiantHashMap() {
                this(20);
        }

        /**
         * 保存常量数对象的HashMap，并且按照使用频率排序,最近使用的其索引最大
         * @param size - 最大长度
         */
        public TransiantHashMap(int size) {
                MAXLENGTH = size;
                keys = new ArrayList(size);
                values = new ArrayList(size);
        }
        /**
         * 向HashMap存入一个值
         * @param key key值
         * @param value 对象
         */
        public void put(Object key, Object value) {
                if (keys.size() >= MAXLENGTH) {
                        remove(0);
                }
                remove(key);
                keys.add(key);
                values.add(value);
        }

        /**
         * 向HashMap获取一个值
         * @param key key值
         * @return 对象
         */
        public Object get(Object key) {
                if (null == key)
                        return null;
                Iterator iterValues = values.iterator();
                Object iterKey = null;
                Object iterValue = null;

                for (Iterator iterKeys = keys.iterator(); iterKeys.hasNext();) {
                        Object tmpKey = iterKeys.next();
                        Object tmpValue = iterValues.next();
                        if (key.equals(tmpKey)) {
                                iterKey = tmpKey;
                                iterValue = tmpValue;
                                break;
                        }
                }
                remove(iterKey);
                put(iterKey, iterValue);
                return iterValue;

        }

        /**
         * 获取所有key值的列表
         * @return 所有的对象的key值
         */
        public List keySet() {
                return keys;
        }

        /**
         * 获取所有value的列表
         * @return 所有的对象
         */
        public List values() {
                return values;
        }

        /**
         * 移除一个值
         * @param key key值
         */
        public void remove(Object key) {
                if (null == key)
                        return;
                Iterator iterValues = values.iterator();
                Object removeKey = null;
                Object removeValue = null;
                for (Iterator iterKeys = keys.iterator(); iterKeys.hasNext();) {
                        Object tmpKey = iterKeys.next();
                        Object tmpValue = iterValues.next();
                        if (key.equals(tmpKey)) {
                                removeKey = tmpKey;
                                removeValue = tmpValue;
                                break;
                        }
                }
                keys.remove(removeKey);
                values.remove(removeValue);
        }

        /**
         * 移除一个值
         * @param index - 索引
         */
        public void remove(int index) {
                if ((index < 0) || (index > keys.size()))
                        return;
                keys.remove(index);
                values.remove(index);
        }

}
