package com.android.nytimesmvvm.view;

import com.android.nytimesmvvm.model.Result;

import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface ArticleListFragmentView{
    void updateList(List<Result> articleList);
    void navigateTo(String url);
    void showLoadingSpinner();
    void dismissLoadingSpinner();
    void showNetworkErrorDialog();
}
