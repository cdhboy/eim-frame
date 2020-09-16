package com.eim.utils;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {
    /**
     * 对Null的对象进行默认值转换
     *
     * @param obj
     * @param defaultObj
     * @return
     */
    public static Object formatNullObject(Object obj, Object defaultObj) {
        Object newObj = obj;

        if (obj == null)
            newObj = defaultObj;

        return newObj;
    }

    public static String formatEmptyText(String text, String defaultText) {
        String newText = text;
        if (StringUtils.isEmpty(text))
            newText = defaultText;

        return newText;
    }
    /**
     * 返回字符串格式的日期
     *
     * @param pattern
     *            日期格式
     * @return
     */
    public static String getCurrentTime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 产生唯一识别符
     *
     * @return
     */
    public static String generateSUID() {
        return UUID.randomUUID().toString();
    }
}
