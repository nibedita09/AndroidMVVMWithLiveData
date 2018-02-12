package com.android.nytimesmvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.android.nytimesmvvm.common.network.RestRequestListener;
import com.android.nytimesmvvm.interactor.NYArticleServiceInteractor;
import com.android.nytimesmvvm.model.Article;
import com.android.nytimesmvvm.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Nibedita on 12/02/2018.
 */

public class ArticleListViewModel extends ViewModel {

    private final NYArticleServiceInteractor mNYArticleServiceInteractor;
    private static MutableLiveData<List<Article>> data = new MutableLiveData<List<Article>>();
    private static MutableLiveData<List<Result>> results = new MutableLiveData<List<Result>>();

    public ArticleListViewModel(NYArticleServiceInteractor nyArticleServiceInteractor){
        mNYArticleServiceInteractor = nyArticleServiceInteractor;
    }

    public LiveData<List<Article>> getData(){

        return Transformations.map(results, new android.arch.core.util.Function<List<Result>, List<Article>>() {
            @Override
            public List<Article> apply(List<Result> input) {
                List<Article> articles=new ArrayList<>();
                for (Result result : results.getValue()) {
                    Article article = new Article();
                    article.setTitle(result.getTitle());
                    article.setSubTitle(result.getPublishedDate());
                    article.setImageUrl(result.getMedia().get(0).getMediaMetadata().get(0).getUrl());
                    article.setUrl(result.getUrl());
                    articles.add(article);
                }
                return articles;
            }
        });
    }
    public void  getArticleList(){
        mNYArticleServiceInteractor.getArticles("7", new RestRequestListener<List<Result>>() {
            @Override
            public void onSuccess(List<Result> list) {
                results.setValue(list);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
