package com.eim.dao.helper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DataFormatHelper {

    /**
     * ResultSet转换为JavaBean
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> List<T> format(ResultSet rs, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            List<String> columnNameList = new ArrayList<String>();
            for (int i = 0; i < columnCount; i++) {
                columnNameList.add(rsMetaData.getColumnLabel(i + 1).toLowerCase());
            }

            Field[] fieldArr = getWholeFields(clazz);

            while(rs.next()) {
                T entity = clazz.newInstance();
                for (Field field : fieldArr) {
                    field.setAccessible(true);
                    if (columnNameList.contains(field.getName().toLowerCase())) {
                        Object value = rs.getObject(field.getName());
                        field.set(entity, value);
                    }
                }

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取所以的类变量，包括该类的父类的类变量
     *
     * @param clazz
     * @return
     */
    public static Field[] getWholeFields(Class<?> clazz) {
        Field[] result = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            Field[] tempField = superClass.getDeclaredFields();
            Field[] tempResult = new Field[result.length + tempField.length];
            for (int i = 0; i < result.length; i++) {
                tempResult[i] = result[i];
            }
            for (int i = 0; i < tempField.length; i++) {
                tempResult[result.length + i] = tempField[i];
            }
            result = tempResult;
            superClass = superClass.getSuperclass();
        }
        return result;
    }
}