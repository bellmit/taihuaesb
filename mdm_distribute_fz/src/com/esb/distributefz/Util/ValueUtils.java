package com.esb.distributefz.Util;

import java.util.Collection;
import java.util.Map;

import net.sf.json.JSONNull;

public class ValueUtils {
    /**
     * 将值转换为String类型
     *
     * @param value 要转换的值
     * @return 类型为String的值
     */
    public static String getString(Object value) {
        String retValue = null;
        if (value == null) {
            return null;
        }

        retValue = value.toString().trim();
        return retValue;
    }


    public static int getInt(Object value){
        if(value==null){
            return 0;
        }
        return Integer.valueOf(value.toString().trim());
    }

    public static <T> boolean isEmpty(T t) {
        if (t == null) {
            return true;
        } else if (t instanceof String) {
            return "".equals(t.toString());
        } else if (t instanceof Map) {
            return ((Map)t).isEmpty();
        } else if (t instanceof JSONNull) {
            return true;
        } else {
            return t instanceof Collection ? ((Collection)t).isEmpty() : false;
        }
    }
}
