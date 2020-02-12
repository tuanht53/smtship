package com.app.smt.shiper.ui.order.orderdelivering;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.di.component.ActivityComponent;
import com.app.smt.shiper.ui.base.BaseFragment;
import com.app.smt.shiper.ui.order.orderdelivering.adapter.OrderDeliveringAdapter;
import com.app.smt.shiper.ui.order.orderdelivering.adapter.ShiperAdapter;
import com.app.smt.shiper.ui.order.orderreceiver.adapter.StoreOptionAdapter;
import com.app.smt.shiper.ui.orderdetail.OrderDetailActivity;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.RecyclerViewOnScrollListener;
import com.app.smt.shiper.util.dialog.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDeliveringFragment extends BaseFragment implements OrderDeliveringMvpView, OrderDeliveringAdapter.OrderAdapterListener,
        StoreOptionAdapter.StoreOptionAdapterListener, SwipeRefreshLayout.OnRefreshListener, ShiperAdapter.ShiperListener {

    public static final String TAG = OrderDeliveringFragment.class.getSimpleName();

    @Inject
    OrderDeliveringPresenter mPresenter;

    @BindView(R.id.view_empty)
    View mViewEmpty;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_select_store)
    TextView mTvStoreSelect;

    @BindView(R.id.view_list_store)
    View mViewListStore;

    @BindView(R.id.recyclerViewStore)
    RecyclerView mRecyclerViewStore;

    private OrderDeliveringAdapter mAdapter;

    private StoreOptionAdapter mAdapterStore;

    private RecyclerViewOnScrollListener mRecyclerViewOnScrollListener;

    private LinearLayoutManager mLayoutManager;

    private boolean hasNext = true;

    private Store mStoreSelect;

    private AlertDialog mAlertDialog;

    private BottomSheetDialog mBottomSheetDialog;

    private ShiperAdapter mAdapterShiper;

    private int mIndexSwap;
    private Order mOrderSwap;

    public OrderDeliveringFragment() {

    }

    public static OrderDeliveringFragment newInstance() {
        OrderDeliveringFragment fragment = new OrderDeliveringFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

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

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mAdapter = new OrderDeliveringAdapter(getBaseActivity(), this);
        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //set load more listener for the RecyclerView adapter
        mRecyclerViewOnScrollListener = new RecyclerViewOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                fetchData(current_page, false);
            }
        };
        mRecyclerView.addOnScrollListener(mRecyclerViewOnScrollListener);

        fetchData(1, true);

        mAdapterStore = new StoreOptionAdapter(getBaseActivity(), this);
        mRecyclerViewStore.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mRecyclerViewStore.setAdapter(mAdapterStore);
        mViewListStore.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        mPresenter.detachView();
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String message) {
        if (AppConstants.EVENTBUS_LIST_ORDER_DELIVERING.equals(message)) {
            fetchData(1, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData(1, true);
    }

    @Override
    public void onRefresh() {
        fetchData(1, true);
    }

    private void fetchData(int page, final boolean forceRefresh) {
        if (page == 1) {
            mRecyclerViewOnScrollListener.reset();
        }

        if (!isAdded() || (!hasNext && !forceRefresh)) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        if (mRecyclerViewOnScrollListener != null) {
            mRecyclerViewOnScrollListener.setCanNextPage(false);
            mRecyclerViewOnScrollListener.current_page = page;
        }

        mSwipeRefreshLayout.setRefreshing(true);

        mPresenter.apiGetListOrder(page, mStoreSelect == null ? null : mStoreSelect.getTxtId(), forceRefresh);
    }

    @Override
    public void loadDataError() {
        if (mRecyclerViewOnScrollListener != null)
            mRecyclerViewOnScrollListener.setCanNextPage(true);

        mViewEmpty.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            mViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showListOrder(List<Order> list, boolean forceRefresh) {
        if (list.size() == 0) {
            hasNext = false;
        }
        if (mRecyclerViewOnScrollListener != null) {
            mRecyclerViewOnScrollListener.setCanNextPage(true);
        }

        mSwipeRefreshLayout.setRefreshing(false);
        if (forceRefresh) {
            if (list.size() == 0) {
                mViewEmpty.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mViewEmpty.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.updateData(list);
            }
        } else {
            mViewEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addData(list);
        }
    }

    @Override
    public void onClickStoreOptionItem(Store store) {
        mViewListStore.setVisibility(View.GONE);
        mStoreSelect = store;
        mTvStoreSelect.setText(mStoreSelect.getStoreName());
        fetchData(1, true);
    }

    @OnClick(R.id.btn_select_store)
    public void onClickBtnShowListStore() {
        if (mViewListStore.getVisibility() == View.VISIBLE) {
            mViewListStore.setVisibility(View.GONE);
        } else {
            mPresenter.apiGetListStore(1);
        }
    }

    @OnClick(R.id.view_list_store)
    public void onClickViewStore() {
        mViewListStore.setVisibility(View.GONE);
    }

    @Override
    public void showListStore(List<Store> list) {
        if (list.size() > 0) {
            mViewListStore.setVisibility(View.VISIBLE);
            Store optionAll = new Store();
            optionAll.setTxtId(null);
            optionAll.setStoreName(getString(R.string.store_all_defaul));
            list.add(0, optionAll);
            if (mStoreSelect != null) {
                mAdapterStore.setSelectedID(mStoreSelect.getTxtId());
            }
            mAdapterStore.updateData(list);
        } else {
            showMessage(R.string.store_empty);
        }
    }

    /************************************** listener adapter order ******************************************/
    @Override
    public void onClickOrderItem(int index, Order order) {
        startActivity(OrderDetailActivity.getStartIntent(getBaseActivity(), order));
    }

    @Override
    public void onDeliveryOrderSuccess(final int index, final Order order) {
        mAlertDialog = DialogUtils.showDialogDefault(getActivity(), getResources().getString(R.string.delivery_order_success_confirm),
                getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new DialogUtils.DialogCallback() {
                    @Override
                    public void cancelDialog() {
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void okDialog() {
                        mAlertDialog.dismiss();
                        mPresenter.apiOrderDeliveryFinish(index, order.getTxtId());
                    }
                });
        mAlertDialog.show();
    }

    @Override
    public void onDeliveryOrderFail(final int index, final Order order) {
        /*if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getBaseActivity(),
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            return;
        }*/
        mAlertDialog = DialogUtils.showDialogFailOrder(getBaseActivity(), new DialogUtils.DialogInputCallback() {
            @Override
            public void onConfirm(String text) {
                hideKeyboard();
                mAlertDialog.dismiss();
                mPresenter.apiOrderDeliveryFail(index, order.getTxtId(), text);

                /*SaleOrderCallLog saleOrderCallLog = new SaleOrderCallLog();
                saleOrderCallLog.setSaleOrderId(order.getTxtId());
                saleOrderCallLog.setCallLog(AppUtils.getCallLog(getBaseActivity(), order.getCustomerInformation().getCustUserName().substring(3)));
                mPresenter.apiPostCallLog(saleOrderCallLog);*/
            }

            @Override
            public void onCancel() {
                hideKeyboard();
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    @Override
    public void onSwapShipper(int index, Order order) {
        mIndexSwap = index;
        mOrderSwap = order;
        mPresenter.apiGetListShipper(1);
    }

    @Override
    public void onChangePriority(Order order, int priority) {
        mPresenter.callApiChangePriority(order.getTxtId(), priority);
    }

    @Override
    public void changePrioritySuccess() {
        fetchData(1, true);
    }

    /************************************** mvp ******************************************/


    @Override
    public void updateOrderSuccess(int index) {
        mAdapter.removeItem(index);
        EventBus.getDefault().post(AppConstants.EVENTBUS_LIST_ORDER_FAIL);
    }

    @Override
    public void showListShipper(List<UserProfile> list, int page) {
        if (page == 1 && list.size() == 0) {
            showMessage(R.string.message_shiper_empty);
            return;
        }
        if (page > 1) {
            mAdapterShiper.addData(list);
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_list_shiper, null);
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        RecyclerView mRecyclerViewPromo = view.findViewById(R.id.recycler_view_dialog_promotion);
        mRecyclerViewPromo.setLayoutManager(mLayoutManager);
        mAdapterShiper = new ShiperAdapter(getBaseActivity(), this);
        mRecyclerViewPromo.setAdapter(mAdapterShiper);

        //set load more listener for the RecyclerView adapter
        RecyclerViewOnScrollListener mRecyclerViewOnScrollListener = new RecyclerViewOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mPresenter.apiGetListShipper(current_page);
            }
        };
        mRecyclerViewPromo.addOnScrollListener(mRecyclerViewOnScrollListener);
        mAdapterShiper.updateData(list);

        mBottomSheetDialog = new BottomSheetDialog(getBaseActivity(), R.style.SheetDialog);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
    }

    @Override
    public void onSelectSwapShipper(UserProfile userProfile) {
        mPresenter.callApiAssignOrderForOtherShipper(mIndexSwap, mOrderSwap.getTxtId(), userProfile.getTxtId());
        if (mBottomSheetDialog != null)
            mBottomSheetDialog.dismiss();
    }
}
