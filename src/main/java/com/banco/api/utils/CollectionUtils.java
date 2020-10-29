package com.banco.api.utils;

import java.util.Collection;

public class CollectionUtils {

    public static <T> void safeAdd(Collection<T> collection, T item) {
         if (item != null) {
             collection.add(item);
         }
    }
}
