package com.app.smt.shiper.ui.order.orderreceiver;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.di.component.ActivityComponent;
import com.app.smt.shiper.ui.base.BaseFragment;
import com.app.smt.shiper.ui.order.orderreceiver.adapter.OrderReceiverAdapter;
import com.app.smt.shiper.ui.order.orderreceiver.adapter.StoreOptionAdapter;
import com.app.smt.shiper.ui.orderdetail.OrderDetailActivity;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.RecyclerViewOnScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderReceiverFragment extends BaseFragment implements OrderReceiverMvpView, OrderReceiverAdapter.OrderAdapterListener,
        StoreOptionAdapter.StoreOptionAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = OrderReceiverFragment.class.getSimpleName();

    @Inject
    OrderReceiverPresenter mPresenter;

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

    private OrderReceiverAdapter mAdapter;

    private StoreOptionAdapter mAdapterStore;

    private RecyclerViewOnScrollListener mRecyclerViewOnScrollListener;

    private LinearLayoutManager mLayoutManager;

    private boolean hasNext = true;

    private Store mStoreSelect;

    public OrderReceiverFragment() {
    }

    public static OrderReceiverFragment newInstance(String param1, String param2) {
        OrderReceiverFragment fragment = new OrderReceiverFragment();
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

        mAdapter = new OrderReceiverAdapter(getBaseActivity(), this);
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
    public void onClickOrderDelivery(int index, Order order) {
        mPresenter.apiOrderConfirmDelivery(index, order.getTxtId());
    }

    @Override
    public void onClickOrderMoveStock(int index, Order order) {
        mPresenter.apiOrderConfirmMoveStock(index, order.getTxtId());
    }

    /************************************** mvp ******************************************/

    @Override
    public void updateOrderMoveStockSuccess(int index) {
        mAdapter.removeItem(index);
        EventBus.getDefault().post(AppConstants.EVENTBUS_LIST_ORDER_FAIL);
    }

    @Override
    public void updateOrderDeliveringSuccess(int index) {
        mAdapter.removeItem(index);
        EventBus.getDefault().post(AppConstants.EVENTBUS_LIST_ORDER_DELIVERING);
    }

}
