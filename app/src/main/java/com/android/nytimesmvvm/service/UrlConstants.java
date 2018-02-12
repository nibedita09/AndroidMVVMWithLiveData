package com.android.nytimesmvvm.service;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface UrlConstants {

   String BASE_URL = "http://api.nytimes.com";
   String API_KEY = "328c8770deff4e44b77b935618667461";
   String MOST_VIEWED_URL = "/svc/mostpopular/v2/mostviewed/all-sections/{period}.json?apikey=" + API_KEY;

}
