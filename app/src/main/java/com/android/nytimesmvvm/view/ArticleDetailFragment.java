package com.android.nytimesmvvm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.android.nytimesmvvm.R;
import com.android.nytimesmvvm.common.ui.BaseFragment;
import com.android.nytimesmvvm.common.utility.AppWebViewClient;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleDetailFragment extends BaseFragment {

    public final String TAG = ArticleDetailFragment.class.getSimpleName();
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null)
            mView = inflater.inflate(R.layout.article_detail, null);
        else
            return mView;

        ProgressBar progressBar = mView.findViewById(R.id.progressBar);
        String url = getArguments().getString("url");
        WebView webView = mView.findViewById(R.id.web);
        webView.setWebViewClient(new AppWebViewClient(progressBar));
        webView.loadUrl(url);
        return mView;
    }
}
