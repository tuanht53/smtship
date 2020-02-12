package com.app.smt.shiper.ui.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.smt.shiper.R;
import com.app.smt.shiper.di.component.ActivityComponent;
import com.app.smt.shiper.ui.base.BaseFragment;
import com.app.smt.shiper.ui.order.orderconfirm.OrderConfirmFragment;
import com.app.smt.shiper.ui.order.orderdelivering.OrderDeliveringFragment;
import com.app.smt.shiper.ui.order.orderdeliveryfail.OrderDeliveryFailFragment;
import com.app.smt.shiper.ui.order.orderreceiver.OrderReceiverFragment;
import com.app.smt.shiper.ui.qrcode.ScanActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderManagerFragment extends BaseFragment implements OrderManagerMvpView {
    // TODO: Rename parameter arguments, choose names that match

    public static final String TAG = OrderManagerFragment.class.getSimpleName();

    @BindView(R.id.notification_tabs)
    public TabLayout mTabLayout;

    @BindView(R.id.notification_viewpager)
    public ViewPager mViewPager;

    @Inject
    OrderManagerPresenter mPresenter;

    public OrderManagerFragment() {
    }

    public static OrderManagerFragment newInstance() {
        OrderManagerFragment fragment = new OrderManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_manager, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            ButterKnife.bind(this, view);
            mPresenter.attachView(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onDetach() {
        mPresenter.detachView();
        super.onDetach();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OrderReceiverFragment(), getResources().getString(R.string.tab_order_receiver));
        adapter.addFragment(new OrderDeliveringFragment(), getResources().getString(R.string.tab_order_delivering));
        adapter.addFragment(new OrderDeliveryFailFragment(), getResources().getString(R.string.tab_order_delivery_fail));
        adapter.addFragment(new OrderConfirmFragment(), getResources().getString(R.string.tab_order_wait_confirm));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @OnClick(R.id.fab_scan)
    public void onClickBtnFabScan() {
        startActivity(ScanActivity.getStartIntent(getBaseActivity()));
    }
}
