Android Architecture Components Basic Sample with MVVM and LiveData
===================================================================

This sample showcases the following Architecture Components:

* [ViewModels](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)
* [DataBinding](https://developer.android.com/topic/libraries/data-binding/index.html)

Introduction
-------------

### Features

This sample contains two screens: a list of articles and detail view, that shows the detail of the article.
Using datatbinding apis to implement MVVM Architectural pattern.

#### View Layer
* A main activity that handles navigation between the fragments.
* A fragment to display the list of articles.
* A fragment to display a article detail(Basically it has a webview, that loads the url in it)

#### Presentation layer
* A ViewModel

The app uses a Model-View-ViewModel (MVVM) architecture for the presentation layer. Each of the fragments corresponds to a MVVM View. The View and ViewModel communicate  using LiveData and the following design principles:

* ViewModel objects don't have references to activities, fragments, or Android views. That would cause leaks on configuration changes, such as a screen rotation, because the system retains a ViewModel across the entire lifecycle of the corresponding view.

* ViewModel objects expose data using `LiveData` objects. `LiveData` allows you to observe changes to data across multiple components of your app without creating explicit and rigid dependency paths between them.

* Views, including the fragments used in this sample, subscribe to corresponding `LiveData` objects. Because `LiveData` is lifecycle-aware, it doesn’t push changes to the underlying data if the observer is not in an active state, and this helps to avoid many common bugs. This is an example of a subscription:

```java
        // Update the list of articles when the underlying data changes.
        mArticleListViewModel.getData().observe(getActivity(), new Observer<List<Article>>() {
                    @Override
                    public void onChanged(@Nullable List<Article> articles) {
                        if(articles != null) {
                            mFragmentCallback.dismissSpinner();
                            mBinding.setIsLoading(false);
                            mArticleListAdapter.setArticleList(articles);
                        }else{
                            mBinding.setIsLoading(true);
                        }
                        mBinding.executePendingBindings();
                    }
                });
```

#### Data layer

* Service APIs(NY Times Most Popular Articles API), that returns the data in json format on network service call.
* Article Observable Class
* LiveData holds the Observables, that holds the actual data received from the Service. Once the data changes, LiveData Callbacks gets calls that dispatch the events to the active registered observers if any.


#### UnitTesting
*Unit Testing(JUnit) as the Viewmodel(Presentation Layer) holds LiveData which is Android architectural component.
*Instrumentation test cases can be written using Espress



License
--------

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.



