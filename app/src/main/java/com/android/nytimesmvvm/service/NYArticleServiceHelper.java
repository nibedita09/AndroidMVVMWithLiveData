package com.android.nytimesmvvm.service;
import com.android.nytimesmvvm.common.error.BaseURLEmptyException;
import com.android.nytimesmvvm.common.network.ApiClientProvider;
import com.android.nytimesmvvm.common.network.RestRequestListener;
import com.android.nytimesmvvm.model.AllItems;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class NYArticleServiceHelper {

    public static void getAllArticles(String period, final RestRequestListener<AllItems> restRequestListener) {
        try {
            NYArticleService nyArticleService = new ApiClientProvider(UrlConstants.BASE_URL, null).provideApiClient(false).create(NYArticleService.class);
            Call<AllItems> call = nyArticleService.getAllArticles(period);
            call.enqueue(new Callback<AllItems>() {
                @Override
                public void onResponse(Call<AllItems> call, Response<AllItems> response) {
                    if (response != null) {
                        AllItems allItems = response.body();
                        if (restRequestListener != null)
                            restRequestListener.onSuccess(allItems);
                    }

                }

                @Override
                public void onFailure(Call<AllItems> call, Throwable t) {
                    if (restRequestListener != null)
                        restRequestListener.onFailure(t);
                }
            });
        } catch (BaseURLEmptyException e) {
            e.printStackTrace();
        }

    }

}
