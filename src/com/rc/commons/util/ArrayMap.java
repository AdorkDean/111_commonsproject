package com.rc.commons.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>公共方法类</p>
 * <p>ArrayMap类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class ArrayMap extends AbstractMap {
    protected ArrayListSet arrSet = new ArrayListSet();
    /**     * ArrayMap的构造函数
     */
    public ArrayMap() {
        super();
    }
    /**
     * 获得Set
     * @return Set 返回值
     */
    public Set entrySet() {
        return arrSet;
    }
    /**
     * 通过索引值得到Object
     * @param index int
     * @return Object
     */
    public Object get(int index) {
        Entry entry = getEntry(index);
        return entry != null ? entry.getValue() : null;
    }
    /**
     * 通过键值得到索引值
     * @param key Object
     * @return int
     */
    public int getIndex(Object key) {
        Entry entry = getEntry(key);
        return arrSet.indexOf(entry);
    }
    /**
     * 通过索引得到键值
     * @param index int
     * @return Object
     */
    public Object getKey(int index) {
        Entry entry = getEntry(index);
        return entry != null ? entry.getKey() : null;
    }
    /**
     * 向ArrayMap放入Key-Value对并返回对象
     * @param key Object
     * @param value Object
     * @return Object
     */
    public Object put(Object key, Object value) {
        Object oldVal = null;
        Entry entry = getEntry(key);
        if (entry != null) {
            oldVal = entry.getValue();
            entry.setValue(value);
        }
        else {
            entry = new Entry(key, value);
            arrSet.add(entry);
        }
        return oldVal;
    }
    /**
     * 是否存在键值
     * @param key Object
     * @return boolean
     */
    public boolean containsKey(Object key) {
        Entry entry = getEntry(key);
        return entry != null;
    }
    /**
     * 取得键值对应的对象
     * @param key Object
     * @return Object
     */
    public Object get(Object key) {
        Entry entry = getEntry(key);
        return entry != null ? entry.getValue() : null;
    }
    /**
     * 取得键值对应的Entry
     * @param key Object
     * @return Entry
     */
    protected Entry getEntry(Object key) {
        Iterator i = entrySet().iterator();
        if (key == null) {
            while (i.hasNext()) {
                Entry e = (Entry) i.next();
                if (e.getKey() == null) {
                    return e;
                }
            }
        }
        else {
            while (i.hasNext()) {
                Entry e = (Entry) i.next();
                if (key.equals(e.getKey())) {
                    return e;
                }
            }
        }
        return null;
    }
    /**
     * 取得index对应的Entry
     * @param index int
     * @return Entry
     */
    protected Entry getEntry(int index) {
        if (index >= 0 && index < arrSet.size()) {
            return (Entry) arrSet.get(index);
        }
        else {
            return null;
        }
    }
    /**
     * 排序
     * @param cp Comparator
     */
    public void doOrder(Comparator cp) {
        List values = new ArrayList(this.values());

        synchronized (arrSet) {
            SortUtil.sortBy(values, arrSet, cp, true);
        }
    }
    /**
     * <p>公共方法类</p>
     * <p>ArrayListSet类</p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: NineTowns</p>
     * @author Weiwenqi
     * @version 1.0
     *
     */
    static class ArrayListSet extends ArrayList implements Set {
    }
    public static final Object NULL_KEY = new Object();
    /**
     * <p>公共方法类</p>
     * <p>Entry类</p>
     * <p>Copyright: Copyright (c) 2005</p>
     * <p>Company: NineTowns</p>
     * @author Weiwenqi
     * @version 1.0
     *
     */
    public static class Entry implements Map.Entry {
        final Object key;
        Object value;
        Entry next;

        /**
         * 建立新Entry
         * @param k Object
         * @param v Object
         */
        Entry(Object k, Object v) {
            value = v;
            key = (k == null ? NULL_KEY : k);
        }
        /**
         * 得到键值
         * @return Object
         */
        public Object getKey() {
            return key == NULL_KEY ? null : key;
        }
        /**
         * 得到值
         * @return Object
         */
        public Object getValue() {
            return value;
        }
        /**
         * 设置值
         * @param newValue Object
         * @return Object
         */
        public Object setValue(Object newValue) {
            Object oldValue = value;
            value = newValue;
            return oldValue;
        }
        /**
         * 是否相等
         * @param o Object
         * @return boolean
         */
        public boolean equals(Object o) {
            if (! (o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }
        /**
         * 得到hashCode
         * @return int
         */
        public int hashCode() {
            return (key == NULL_KEY ? 0 : key.hashCode()) ^
                (value == null ? 0 : value.hashCode());
        }
        /**
         * 转换为String类型
         * @return String
         */
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }
}
