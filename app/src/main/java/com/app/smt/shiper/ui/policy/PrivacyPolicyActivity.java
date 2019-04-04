package com.app.smt.shiper.ui.policy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.app.smt.shiper.R;
import com.app.smt.shiper.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class PrivacyPolicyActivity extends BaseActivity implements PrivacyPolicyMvpView {

    @Inject
    PrivacyPolicyPresenter mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PrivacyPolicyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_privacy_policy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
