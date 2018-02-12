package com.android.nytimesmvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nytimesmvvm.Injection;
import com.android.nytimesmvvm.R;
import com.android.nytimesmvvm.ViewModelFactory;
import com.android.nytimesmvvm.common.ui.BaseFragment;
import com.android.nytimesmvvm.common.ui.FragmentCallback;
import com.android.nytimesmvvm.common.utility.CommonUtils;
import com.android.nytimesmvvm.databinding.ArticleListBinding;
import com.android.nytimesmvvm.model.Article;
import com.android.nytimesmvvm.viewmodel.ArticleListViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleListFragment extends BaseFragment{

    private FragmentCallback mFragmentCallback;
    private FragmentManager mFragmentManager;
    private ArticleListAdapter mArticleListAdapter;
    private ArticleListViewModel mArticleListViewModel;
    private ArticleListBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getActivity());
        mArticleListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ArticleListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.article_list, container, false);
        mFragmentManager = getActivity().getSupportFragmentManager();
        mArticleListAdapter = new ArticleListAdapter(Picasso.with(getActivity()), mArticleClickCallback);

        mBinding.listView.addItemDecoration(CommonUtils.getListDivider(getActivity()));
        mBinding.listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.listView.setAdapter(mArticleListAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentCallback.showSpinner(getString(R.string.loading));
        mArticleListViewModel.getArticleList();

        mArticleListViewModel.getData().observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if(articles != null) {
                    mFragmentCallback.dismissSpinner();
                    mBinding.setIsLoading(false);
                    mArticleListAdapter.setArticleList(articles);

                    if(mFragmentCallback.isTwoPane()) {
                        final Fragment detailFragment = new ArticleDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", articles.get(0).getUrl());
                        detailFragment.setArguments(bundle);
                        mFragmentCallback.updateRightPane(detailFragment);
                    }
                }else{
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null && context instanceof FragmentCallback){
            mFragmentCallback = (FragmentCallback) context;
        }
    }

    private final ArticleClickCallback mArticleClickCallback = new ArticleClickCallback() {
        @Override
        public void onClick(String url) {
            final Fragment detailFragment = new ArticleDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            detailFragment.setArguments(bundle);
            if(mFragmentCallback.isTwoPane()){
               mFragmentCallback.updateRightPane(detailFragment);
            }else{
                mFragmentCallback.navigateTo(detailFragment);
            }
        }
    };


}
