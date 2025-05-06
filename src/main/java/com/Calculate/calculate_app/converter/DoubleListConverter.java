package com.Calculate.calculate_app.converter;

import java.util.List;
import java.util.stream.Collectors;

public class DoubleListConverter {

    /**
     * 将 Double 列表转换为指定分隔符连接的字符串
     * @param numbers Double 列表（允许 null 或空列表）
     * @param delimiter 分隔符（默认逗号 ","）
     * @return 转换后的字符串（空列表或 null 时返回空字符串 ""）
     */
    public static String convertListToString(List<Double> numbers, String delimiter) {
        // 处理 null 和空列表
        if (numbers == null || numbers.isEmpty()) {
            return "";
        }
        // 使用 Java 8 流转换并拼接
        return numbers.stream()
                .map(String::valueOf)  // 将每个 Double 转为字符串
                .collect(Collectors.joining(delimiter));  // 用分隔符连接
    }

    /**
     * 重载函数：默认使用逗号作为分隔符
     */
    public static String convertListToString(List<Double> numbers) {
        return convertListToString(numbers, ",");  // 调用带分隔符的版本，默认逗号
    }
}