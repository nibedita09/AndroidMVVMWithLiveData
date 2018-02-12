package com.android.nytimesmvvm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.android.nytimesmvvm.common.ui.FragmentCallback;
import com.android.nytimesmvvm.view.ArticleDetailFragment;
import com.android.nytimesmvvm.view.ArticleListFragment;

public class MainActivity extends AppCompatActivity implements FragmentCallback {

    private boolean twoPane = false;
    private FragmentManager mFragmentManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null)
            return;

        FrameLayout detailLayout = this.findViewById(R.id.container_detail);
        if(detailLayout != null)
            twoPane = true;

        //Adding List Fragment
        final Fragment articleListFragment = new ArticleListFragment();
        mFragmentManager.beginTransaction().add(R.id.container, articleListFragment).commit();

        if(twoPane){
            //Adding List Fragment
            Fragment detailFragment = new ArticleDetailFragment();
            mFragmentManager.beginTransaction().add(R.id.container_detail, detailFragment).commit();
        }

    }


    @Override
    public void showSpinner(String message) {

        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(message);
        }
        this.mProgressDialog.show();
    }

    @Override
    public void dismissSpinner() {

        if(this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog.cancel();
            this.mProgressDialog = null;
        }
    }

    @Override
    public void showErrorDialog(String title, String message, final ErrorDialogAction action) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                       if(action != null)
                           action.onClickOk();
                    }
                }).setNeutralButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if(action != null)
                    action.onClickRetry();
            }
        }).show();
    }

    @Override
    public void navigateTo(Fragment targetFragment) {

        mFragmentManager.beginTransaction().add(R.id.container, targetFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean isTwoPane() {
        return twoPane;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
