package com.mina.npay.gateway.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * Gson工具类
 * 
 * @author daimingdong
 */
@Slf4j
public class GsonUtils {

    /**
     * 空的 {@code JSON} 数据 - <code>"{}"</code>。
     */
    private static final String EMPTY_JSON           = "{}";
    /**
     * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
     */
    private static final String EMPTY_JSON_ARRAY     = "[]";
    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
     * <code>"[]"</code> </strong>
     * 
     * @param target 目标对象。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target) {
        return toJson(target, null, null);
    }

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
     * <code>"[]"</code> </strong>
     * 
     * @param target 目标对象。
     * @param targetType 目标对象的类型。
     * @param datePattern 日期字段的格式化模式。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType, String datePattern) {
        if (target == null) {
            return EMPTY_JSON;
        }
        GsonBuilder builder = new GsonBuilder();
        if (datePattern == null || datePattern.length() < 1) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);

        Gson gson = builder.create();
        String result = EMPTY_JSON;
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception ex) {
            log.warn(String.format("error info : target = %s  targetType = %s", target, targetType));
            log.warn("目标对象转换为json异常 errMessage{}", ex.getMessage(), ex);
            if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            }
        }
        return result;
    }

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
     * <code>"[]"</code> </strong>
     * 
     * @param target 目标对象。
     * @param targetType 目标对象的类型。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     * 
     * @param <T> 要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (json == null || json.length() < 1) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (datePattern == null || datePattern.length() < 1) {
            datePattern = DEFAULT_DATE_PATTERN;
        }

        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return (T) gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            log.warn("json 信息 {}", json);
            log.warn("json 转换为对象失败 errMessage{}", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定类型的对象
     * 
     * @param json 给定的 {@code JSON } 字符串
     * @param type 指定的类型对象
     * @param datePattern 日期格式模式
     * @return 给定{@code JSON} 字符串表示的指定类型对象
     */
    public static Object fromJson(String json, Type type, String datePattern) {
        if (json == null || json.length() < 1) {
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        if (datePattern == null || datePattern.length() < 1) {
            datePattern = DEFAULT_DATE_PATTERN;
        }

        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception ex) {
            log.warn(json + " 无法转换为对象", ex);
            return null;
        }
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定类型的对象
     * 
     * @param json 给定的 {@code JSON } 字符串
     * @param type 指定的类型对象
     * @return 给定{@code JSON} 字符串表示的指定类型对象
     */
    public static Object fromJson(String json, Type type) {
        return fromJson(json, type, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     * 
     * @param <T> 要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * 
     * @param <T> 要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @param clazz 要转换的目标类。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if (json == null || json.length() < 1) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (datePattern == null || datePattern.length() < 1) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
            log.warn(json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
            return null;
        }
    }

    /**
     * @param json
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map jsonToMap(String json) {
        return jsonToMap(json, null);
    }

    /**
     * @param json
     * @param datePattern
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map jsonToMap(String json, String datePattern) {
        if (json == null || json.length() < 1) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (datePattern == null || datePattern.length() < 1) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        @SuppressWarnings("unused")
        Gson gson = builder.create();
        try {

            return (Map<String, String>) GsonUtils.fromJson(json, new TypeToken<Map<String, String>>() {
            }.getType());

        } catch (Exception ex) {
            log.warn(json + " 无法转换为 对象!", ex);
            return null;
        }
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * 
     * @param <T> 要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @param clazz 要转换的目标类。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }
}
