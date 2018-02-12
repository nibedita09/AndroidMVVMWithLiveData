package com.android.nytimesmvvm.model;

import java.util.Observable;

/**
 * Created by Nibedita on 12/02/2018.
 */

public class Article extends Observable {

    public String title;
    public String subTitle;
    public String imageUrl;
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        notifyObservers();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyObservers();
    }



}
