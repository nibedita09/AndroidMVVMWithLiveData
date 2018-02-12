/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.nytimesmvvm;

import android.content.Context;

import com.android.nytimesmvvm.interactor.NYArticleServiceInteractor;
import com.android.nytimesmvvm.interactor.NYArticleServiceInteractorImpl;

/**
 * Enables injection of data sources.
 */
public class Injection {

    /**
     * Provides Datasource object to query db
     * @param context
     * @return
     */
    public static NYArticleServiceInteractor provideDataSource(Context context) {
        return new NYArticleServiceInteractorImpl();
    }

    /**
     * Provides ViewModelFactory with the arguments
     * @param context
     * @return
     */
    public static ViewModelFactory provideViewModelFactory(Context context) {
        NYArticleServiceInteractor dataSource = provideDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
