package com.android.nytimesmvvm.view;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.nytimesmvvm.R;
import com.android.nytimesmvvm.databinding.ArticleListItemBinding;
import com.android.nytimesmvvm.model.Article;
import com.android.nytimesmvvm.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

/**
 * Created by Nibedita on 11/02/2018.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private List<Article> mData;
    private final Picasso mPicasso;
    private final ArticleClickCallback mArticleClickCallback;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ArticleListItemBinding mArticleListItemBinding;

        public ViewHolder(ArticleListItemBinding articleListItemBinding) {
            super(articleListItemBinding.getRoot());
            mArticleListItemBinding = articleListItemBinding;
        }
    }

    public ArticleListAdapter(Picasso picasso, ArticleClickCallback articleClickCallback){
        mPicasso = picasso;
        mArticleClickCallback = articleClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.article_list_item,
                        parent, false);
        binding.setCallback(mArticleClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArticleListAdapter.ViewHolder holder, int position) {
        holder.mArticleListItemBinding.setArticle(this.mData.get(position));
        holder.mArticleListItemBinding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        if(mData != null)
            return mData.size();
        else
            return 0;
    }

    public void setArticleList(List<Article> articles){
        if (mData == null) {
            mData = articles;
            notifyItemRangeInserted(0, mData.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return mData.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mData.get(oldItemPosition).getTitle().equalsIgnoreCase(
                            mData.get(newItemPosition).getTitle());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Article newProduct = mData.get(newItemPosition);
                    Article oldProduct = mData.get(oldItemPosition);
                    return mData.get(oldItemPosition).getTitle().equalsIgnoreCase(
                            mData.get(newItemPosition).getTitle())
                            && mData.get(oldItemPosition).getSubTitle().equalsIgnoreCase(
                            mData.get(newItemPosition).getSubTitle())
                            && mData.get(oldItemPosition).getImageUrl().equalsIgnoreCase(
                            mData.get(newItemPosition).getImageUrl());
                }
            });
            mData = articles;
            result.dispatchUpdatesTo(this);
        }
    }

}
