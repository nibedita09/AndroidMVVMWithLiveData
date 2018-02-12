package com.android.nytimesmvvm.interactor;


import com.android.nytimesmvvm.common.network.RestRequestListener;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface NYArticleServiceInteractor {

    void getArticles(String period, RestRequestListener restRequestListener);
}
