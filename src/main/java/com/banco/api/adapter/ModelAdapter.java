package com.banco.api.adapter;

public class ModelAdapter {

    public static <M extends Internalizable<V>, V> M fromExternal(Class<M> internalClazz, V view) {
        if (view != null) {
            M internal;
            try {
                internal = internalClazz.newInstance();
                internal.fromView(view);
                return internal;
            } catch (IllegalAccessException | InstantiationException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            return null;
        }
    }
}
