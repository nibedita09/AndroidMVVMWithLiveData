package com.android.nytimesmvvm.common.network;

/**
 * Created by 436645 on 5/16/2017.
 */
public interface RestRequestListener<T> {

    void onSuccess(T t);
    void onFailure(Throwable t);
}
