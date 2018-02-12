package com.android.nytimesmvvm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.android.nytimesmvvm.interactor.NYArticleServiceInteractor;
import com.android.nytimesmvvm.viewmodel.ArticleListViewModel;

/**
 * Created by Nibedita on 02/02/2018.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final NYArticleServiceInteractor mDataSource;

    public ViewModelFactory(NYArticleServiceInteractor userDataSource){
        mDataSource = userDataSource;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ArticleListViewModel.class))
            return (T) new ArticleListViewModel(mDataSource);
        return null;
    }
}
