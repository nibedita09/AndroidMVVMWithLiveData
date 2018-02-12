package com.android.nytimesmvvm.service;


import com.android.nytimesmvvm.model.AllItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface NYArticleService {

    @GET(UrlConstants.MOST_VIEWED_URL)
    Call<AllItems> getAllArticles(@Path("period") String period);
}
