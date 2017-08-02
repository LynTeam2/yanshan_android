package com.ycl.framework.utils.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJSONParser {
    /**
     * @return T 获得对象 二次处理
     */
    public static <T> T getBean(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (t == null) {
                try {
                    t = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return t;
    }

    /**
     * @return List<T>  获得数组<T>
     */
    public static <T> List<T> getBeanList(String jsonString, Class<T> clazz) {
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null)
            list = new ArrayList<T>();
        return list;
    }

    public static List<Map<String, String>> listKeymaps(String jsonString) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //转成 key - values 的Map类型   不完善  T泛型  编译器 只支持一层传递
//    public static <T> Map<String, T> getMapsBean(String jsonString) {
//        Map<String, T> beanMap;
//        try {
//            beanMap = JSON.parseObject(jsonString, new TypeReference<Map<String, T>>() {
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            beanMap = new HashMap<String, T>();
//        }
//        return beanMap;
//    }

    public static <T> Map<String, List<T>> getMapsList(String jsonString, Map<String, List<T>> beanMap) {
        try {
            beanMap = JSON.parseObject(jsonString, new TypeReference<Map<String, List<T>>>() {
            });
        } catch (Exception e) {
            beanMap = new HashMap<String, List<T>>();
            e.printStackTrace();
        }
        return beanMap;
    }

    /**
     * @return String  获得String
     */
    public static <T> String getJsonString(T... clazz) {
        String jsonStr = null;
        try {
            for (T element : clazz) {
                jsonStr = JSON.toJSONString(element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * @return String  获得List<T>转成String
     */
    public static <T> String getListJsonString(List<T> datas) {
        String jsonStr = null;
        try {
            jsonStr = JSON.toJSONString(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


    /**
     * @return String  获得String 类型 "["",""]"
     */
    public static <T> String getJsonListString(String... clazz) {
        String jsonStr = null;
        try {
            jsonStr = JSON.toJSONString(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static String convertObjToJson(Object ob) {
        String json = null;
        try {
            json = JSON.toJSONString(ob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
