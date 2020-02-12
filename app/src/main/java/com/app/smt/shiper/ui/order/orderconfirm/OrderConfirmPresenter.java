package com.app.smt.shiper.ui.order.orderconfirm;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;
import com.app.smt.shiper.util.AppConstants;
import com.app.smt.shiper.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class OrderConfirmPresenter extends BasePresenter<OrderConfirmMvpView> {

    private Disposable mDisposable;

    private Disposable mDisposableStore;

    @Inject
    public OrderConfirmPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(OrderConfirmMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
        if (mDisposableStore != null) mDisposableStore.dispose();
    }

    public void apiGetListOrder(int page, String storeId, final boolean forceRefresh) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        if (forceRefresh)
            getMvpView().showLoading();
        getDataManager().apiGetListOrder(page, storeId, AppConstants.ORDER_WORKFLOW_WAITING_SHIPPER_CONFIRM_ORDER, null, getDataManager().getUserID())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<List<Order>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<List<Order>> response) {
                        getMvpView().showListOrder(response.getData(), forceRefresh);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        // handle error here
                        handleApiError(e);
                        getMvpView().loadDataError();
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }

    public void apiGetListStore(int page) {
        checkViewAttached();
        RxUtil.dispose(mDisposableStore);
        getMvpView().showLoading();
        getDataManager().apiGetListStore(page, AppConstants.ORDER_WORKFLOW_WAITING_SHIPPER_CONFIRM_ORDER, null, getDataManager().getUserID())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<List<Store>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableStore = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<List<Store>> response) {
                        getMvpView().showListStore(response.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        // handle error here
                        handleApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }

    void calApiRejectOrderForOtherShipper(final int index, String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiRejectOrderForOtherShipper(idOrder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        getMvpView().updateOrderSuccess(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        // handle error here
                        handleApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }

    void callApiRejectChangeMoney(final int index, String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiRejectChangeMoney(idOrder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        getMvpView().updateOrderSuccess(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        // handle error here
                        handleApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }

    void callApiChangeWorkflow(final int index, String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiChangeWorkflow(idOrder, AppConstants.ORDER_WORKFLOW_DELIVERING)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<String> response) {
                        getMvpView().updateOrderSuccess(index);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        // handle error here
                        handleApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }
}
