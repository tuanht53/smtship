package com.app.smt.shiper.ui.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.order.CustomerInformation;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.order.Products;
import com.app.smt.shiper.data.model.order.StoreInformation;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.ui.base.BaseActivity;
import com.app.smt.shiper.ui.order.orderdelivering.adapter.ShiperAdapter;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.AppUtils;
import com.app.smt.shiper.util.RecyclerViewOnScrollListener;
import com.app.smt.shiper.util.dialog.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements OrderDetailMvpView, ShiperAdapter.ShiperListener {

    private static final String ARG_PARAM = "data_model";
    @Inject
    OrderDetailPresenter mPresenter;

    @BindView(R.id.tv_store_name)
    TextView mTvStoreName;

    @BindView(R.id.tv_store_phone)
    TextView mTvStorePhone;

    @BindView(R.id.tv_store_address)
    TextView mTvStoreAddress;

    @BindView(R.id.tv_customer_name)
    TextView mTvCustomerName;

    @BindView(R.id.tv_customer_phone)
    TextView mTvCustomerPhone;

    @BindView(R.id.tv_customer_address)
    TextView mTvCustomerAddress;

    @BindView(R.id.tv_order_content)
    TextView mTvOrderContent;

    @BindView(R.id.tv_order_note)
    TextView mTvOrderNote;

    @BindView(R.id.view_reason_delivery_fail)
    View mViewResonDeliveryFail;

    @BindView(R.id.tv_order_reason)
    TextView mTvReasonFail;

    @BindView(R.id.btn_fail)
    Button mBtnFail;

    @BindView(R.id.btn_success)
    Button mBtnSuccess;

    @BindView(R.id.btn_swap_ship)
    Button mBtnSwapShip;

    @BindView(R.id.view_confirm)
    View mViewConfirm;

    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;

    private Order mOrder;

    private AlertDialog mAlertDialog;

    private BottomSheetDialog mBottomSheetDialog;

    private ShiperAdapter mAdapterShiper;

    public static Intent getStartIntent(Context context, Order order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ARG_PARAM, order);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_order_detail);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);

        mPresenter.attachView(this);

        mOrder = (Order) getIntent().getSerializableExtra(ARG_PARAM);
        setup();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void setup() {
        StoreInformation storeInformation = mOrder.getStoreInformation();
        mTvStoreName.setText(storeInformation.getStoreName());
        mTvStorePhone.setText(storeInformation.getStoreMobile());
        if (storeInformation.getAddress() != null)
            mTvStoreAddress.setText(storeInformation.getAddress().getAddress());

        CustomerInformation customerInformation = mOrder.getCustomerInformation();
        mTvCustomerName.setText(customerInformation.getCustUserFullName());
        mTvCustomerPhone.setText(customerInformation.getCustUserName());
        if (mOrder.getAddressForDelivery() != null)
            mTvCustomerAddress.setText(mOrder.getAddressForDelivery().getAddress());

        if (!TextUtils.isEmpty(mOrder.getNote())) {
            mTvOrderNote.setText(Html.fromHtml(getResources().getString(R.string.order_note, mOrder.getNote())));
        }

        String contentProduct = "";
        for (int i = 0; i < mOrder.getProducts().size(); i++) {
            Products product = mOrder.getProducts().get(i);
            contentProduct += (i + 1) + ". <b>" + product.getName() + "</b>" + " x" + product.getQuantity() + "<br/>";
        }
        mTvOrderContent.setText(Html.fromHtml(getResources().getString(R.string.order_content, contentProduct)));

        if (AppConstants.ORDER_WORKFLOW_SHIPPER_MOVING_TAKE_PRODUCT.equals(mOrder.getWorkflow())) {
            mBtnSuccess.setText(getResources().getString(R.string.btn_order_delivering));
            mBtnFail.setText(getResources().getString(R.string.btn_order_move_stock));
            mBtnSwapShip.setVisibility(View.GONE);
        } else if(AppConstants.ORDER_WORKFLOW_DELIVERING.equals(mOrder.getWorkflow())) {
            mBtnSuccess.setText(getResources().getString(R.string.btn_order_success));
            mBtnFail.setText(getResources().getString(R.string.btn_order_fail));
            mBtnSwapShip.setVisibility(View.VISIBLE);
        } else if(AppConstants.ORDER_WORKFLOW_DELIVERY_DONE.equals(mOrder.getWorkflow())) {
            mBtnSuccess.setVisibility(View.GONE);
            mBtnFail.setVisibility(View.GONE);
            mBtnSwapShip.setVisibility(View.GONE);
        } else if (AppConstants.ORDER_WORKFLOW_WAITING_SHIPPER_CONFIRM_ORDER.equals(mOrder.getWorkflow())) {
            mBtnFail.setVisibility(View.VISIBLE);
            mBtnSwapShip.setVisibility(View.GONE);
            mBtnFail.setText(getResources().getString(R.string.btn_confirm_cancel));
            mBtnSuccess.setText(getResources().getString(R.string.btn_confirm_ok));
            mViewConfirm.setVisibility(View.VISIBLE);
            if (AppConstants.CHANGE_MONEY.equals(mOrder.getSaleOrderInformationCommon().getChangeType())) {
                mTvConfirm.setText(getResources().getString(R.string.confirm_status_money));
            } else {
                mTvConfirm.setText(getResources().getString(R.string.confirm_status_ship));
            }
        } else {
            mBtnFail.setVisibility(View.GONE);
            mBtnSwapShip.setVisibility(View.GONE);
            mBtnSuccess.setText(getResources().getString(R.string.btn_order_delevering_again));
            if (!TextUtils.isEmpty(mOrder.getReason())) {
                mViewResonDeliveryFail.setVisibility(View.VISIBLE);
                mTvReasonFail.setText(mOrder.getReason());
            } else {
                mViewResonDeliveryFail.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btn_store_phone)
    public void onClickBtnPhoneStore() {
        if (TextUtils.isEmpty(mOrder.getStoreInformation().getStoreMobile())) {
            showMessage(R.string.message_phone_empty);
            return;
        }
        AppUtils.callPhone(this, mOrder.getStoreInformation().getStoreMobile());
    }

    @OnClick(R.id.btn_customer_phone)
    public void onClickBtnPhoneCustomer() {
        if (TextUtils.isEmpty(mOrder.getCustomerInformation().getCustUserName())) {
            showMessage(R.string.message_phone_empty);
            return;
        }
        AppUtils.callPhone(this, mOrder.getCustomerInformation().getCustUserName());
    }

    @OnClick(R.id.btn_success)
    public void onClickBtnSuccess(){
        if (AppConstants.ORDER_WORKFLOW_SHIPPER_MOVING_TAKE_PRODUCT.equals(mOrder.getWorkflow())) {
            mPresenter.apiOrderConfirmDelivery(mOrder.getTxtId());
        } else if(AppConstants.ORDER_WORKFLOW_DELIVERING.equals(mOrder.getWorkflow())) {
            mPresenter.apiOrderDeliveryFinish(mOrder.getTxtId());
        } else if (AppConstants.ORDER_WORKFLOW_WAITING_SHIPPER_CONFIRM_ORDER.equals(mOrder.getWorkflow())) {
            mPresenter.callApiChangeWorkflow(mOrder.getTxtId());
        } else {
            mPresenter.apiOrderMoveDelevering(mOrder.getTxtId());
        }
    }

    @OnClick(R.id.btn_fail)
    public void onClickBtnFail(){
        if (AppConstants.ORDER_WORKFLOW_SHIPPER_MOVING_TAKE_PRODUCT.equals(mOrder.getWorkflow())) {
            mPresenter.apiOrderConfirmMoveStock(mOrder.getTxtId());
        } else if(AppConstants.ORDER_WORKFLOW_DELIVERING.equals(mOrder.getWorkflow())) {
            /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                return;
            }*/
            mAlertDialog = DialogUtils.showDialogFailOrder(this, new DialogUtils.DialogInputCallback() {
                @Override
                public void onConfirm(String text) {
                    hideKeyboard();
                    mAlertDialog.dismiss();
                    mPresenter.apiOrderDeliveryFail(mOrder.getTxtId(), text);

                    /*SaleOrderCallLog saleOrderCallLog = new SaleOrderCallLog();
                    saleOrderCallLog.setSaleOrderId(mOrder.getTxtId());
                    saleOrderCallLog.setCallLog(AppUtils.getCallLog(OrderDetailActivity.this, mOrder.getCustomerInformation().getCustUserName().substring(3)));
                    mPresenter.apiPostCallLog(saleOrderCallLog);*/
                }

                @Override
                public void onCancel() {
                    hideKeyboard();
                    mAlertDialog.dismiss();
                }
            });
            mAlertDialog.show();
        } else if (AppConstants.ORDER_WORKFLOW_WAITING_SHIPPER_CONFIRM_ORDER.equals(mOrder.getWorkflow())) {
            if (AppConstants.CHANGE_MONEY.equals(mOrder.getSaleOrderInformationCommon().getChangeType())) {
                mPresenter.callApiRejectChangeMoney(mOrder.getTxtId());
            } else {
                mPresenter.calApiRejectOrderForOtherShipper(mOrder.getTxtId());
            }
        }
    }

    @OnClick(R.id.btn_swap_ship)
    public void onClickBtnSwapShip() {
        mPresenter.apiGetListShipper(1);
    }

    /***************************************** mvp *****************************************/
    @Override
    public void updateOrderSuccess() {
        finish();
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView mRecyclerViewPromo = view.findViewById(R.id.recycler_view_dialog_promotion);
        mRecyclerViewPromo.setLayoutManager(mLayoutManager);
        mAdapterShiper = new ShiperAdapter(this, this);
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

        mBottomSheetDialog = new BottomSheetDialog(this, R.style.SheetDialog);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
    }

    @Override
    public void onSelectSwapShipper(UserProfile userProfile) {
        mPresenter.callApiAssignOrderForOtherShipper(mOrder.getTxtId(), userProfile.getTxtId());
        if (mBottomSheetDialog != null)
            mBottomSheetDialog.dismiss();
    }
}
