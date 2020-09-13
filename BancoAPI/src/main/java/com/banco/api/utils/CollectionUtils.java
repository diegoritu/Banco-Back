package com.banco.api.utils;

import java.util.List;

public class CollectionUtils {

    public static <T> void safeAdd(List<T> list, T item) {
         if (item != null) {
             list.add(item);
         }
    }
}
