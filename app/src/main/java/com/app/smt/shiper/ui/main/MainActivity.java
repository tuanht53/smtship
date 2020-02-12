package com.app.smt.shiper.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.smt.shiper.R;
import com.app.smt.shiper.ui.base.BaseActivity;
import com.app.smt.shiper.ui.notification.NotificationsFragment;
import com.app.smt.shiper.ui.order.OrderManagerFragment;
import com.app.smt.shiper.ui.report.ReportFragment;
import com.app.smt.shiper.ui.user.MenuUserFragment;
import com.app.smt.shiper.util.AppLogger;
import com.app.smt.shiper.util.dialog.DialogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private FragmentManager fragmentManager;

    private Fragment newFragment;

    private int lastSelectedNavigationItem;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.attachView(this);

        fragmentManager = getSupportFragmentManager();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (newFragment == null) {
            showHome();
        }

        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                AppLogger.d("Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        mPresenter.syncTokenFirebase();

        setup();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

    }

    private void setup() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (lastSelectedNavigationItem == item.getItemId()) return true;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    lastSelectedNavigationItem = R.id.navigation_home;
                    showHome();
                    return true;
                case R.id.navigation_notificaiton:
                    lastSelectedNavigationItem = R.id.navigation_notificaiton;
                    showNotification();
                    return true;
                case R.id.navigation_report:
                    lastSelectedNavigationItem = R.id.navigation_report;
                    showReport();
                    return true;
                case R.id.navigation_profile:
                    lastSelectedNavigationItem = R.id.navigation_profile;
                    showUser();
                    return true;
            }

            return false;
        }
    };

    public void showHome() {
        newFragment = OrderManagerFragment.newInstance();
        changeFragment(newFragment, OrderManagerFragment.TAG);
    }

    private void showNotification() {
        newFragment = NotificationsFragment.newInstance("", "");
        changeFragment(newFragment, NotificationsFragment.TAG);
    }

    private void showReport() {
        newFragment = ReportFragment.newInstance("","");
        changeFragment(newFragment, ReportFragment.TAG);
    }

    private void showUser() {
        newFragment = MenuUserFragment.newInstance();
        changeFragment(newFragment, MenuUserFragment.TAG);
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0)
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = fragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.frame_container, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showError() {
        DialogUtils.showDialogDefault(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showRibotsEmpty() {
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }
}
