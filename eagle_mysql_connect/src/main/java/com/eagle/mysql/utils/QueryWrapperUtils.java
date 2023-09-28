package com.eagle.mysql.utils;

import lombok.Data;

import java.util.Locale;

@Data
public class QueryWrapperUtils {

    public static String getLimitOffset(int currentPage, int pageSize) {
        return String.format(Locale.ROOT, "limit %s offset %s", pageSize, (currentPage - 1) * pageSize);
    }
}
