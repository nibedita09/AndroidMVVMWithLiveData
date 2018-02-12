package com.android.nytimesmvvm.interactor;

import com.android.nytimesmvvm.common.network.RestRequestListener;
import com.android.nytimesmvvm.model.AllItems;
import com.android.nytimesmvvm.service.NYArticleServiceHelper;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class NYArticleServiceInteractorImpl implements NYArticleServiceInteractor {

    @Override
    public void getArticles(String period, final RestRequestListener restRequestListener) {
        NYArticleServiceHelper.getAllArticles(period, new RestRequestListener<AllItems>() {
            @Override
            public void onSuccess(AllItems allItems) {
                if (allItems != null) {
                    if (restRequestListener != null)
                        restRequestListener.onSuccess(allItems.getResults());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (restRequestListener != null)
                    restRequestListener.onFailure(t);
            }
        });
    }
}
