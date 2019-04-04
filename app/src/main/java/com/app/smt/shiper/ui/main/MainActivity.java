package com.app.smt.shiper.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.smt.shiper.R;
import com.app.smt.shiper.ui.base.BaseActivity;
import com.app.smt.shiper.ui.movie.MoviesFragment;
import com.app.smt.shiper.ui.notification.NotificationsFragment;
import com.app.smt.shiper.ui.photo.PhotosFragment;
import com.app.smt.shiper.ui.setting.SettingsFragment;
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
            showHomeMap();
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
                    showHomeMap();
                    return true;
                case R.id.navigation_favourite:
                    lastSelectedNavigationItem = R.id.navigation_favourite;
                    showFavourite();
                    return true;
                case R.id.navigation_notifications:
                    lastSelectedNavigationItem = R.id.navigation_notifications;
                    showPromotion();
                    return true;
                case R.id.navigation_profile:
                    lastSelectedNavigationItem = R.id.navigation_profile;
                    showMenuProfile();
                    return true;
            }

            return false;
        }
    };

    public void showHomeMap() {
        newFragment = SettingsFragment.newInstance("", "");
        changeFragment(newFragment, SettingsFragment.TAG);
    }

    private void showPromotion() {
        newFragment = MoviesFragment.newInstance("", "");
        changeFragment(newFragment, MoviesFragment.TAG);
    }

    private void showMenuProfile() {
        newFragment = NotificationsFragment.newInstance("", "");
        changeFragment(newFragment, NotificationsFragment.TAG);
    }

    private void showFavourite() {
        newFragment = PhotosFragment.newInstance("", "");
        changeFragment(newFragment, PhotosFragment.TAG);
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
