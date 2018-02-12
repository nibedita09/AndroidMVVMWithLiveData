package com.android.nytimesmvvm.common.ui;

import android.support.v4.app.Fragment;

/**
 * Created by Nibedita on 11/02/2018.
 */

public interface FragmentCallback {

    void showSpinner(String message);
    void dismissSpinner();
    void showErrorDialog(String title, String message, ErrorDialogAction action);
    void navigateTo(Fragment targetFragment);
    boolean isTwoPane();

    interface ErrorDialogAction {
        void onClickOk();
        void onClickRetry();
    }
}
