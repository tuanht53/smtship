package com.app.smt.shiper.ui.order.orderdelivering;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.ApiError;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.order.SaleOrderCallLog;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.data.model.user.UserProfile;
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
public class OrderDeliveringPresenter extends BasePresenter<OrderDeliveringMvpView> {

    private Disposable mDisposable;

    private Disposable mDisposableStore;

    @Inject
    public OrderDeliveringPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(OrderDeliveringMvpView mvpView) {
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
        getDataManager().apiGetListOrder(page, storeId, AppConstants.ORDER_WORKFLOW_DELIVERING, null, getDataManager().getUserID())
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
        getDataManager().apiGetListStore(page, AppConstants.ORDER_WORKFLOW_DELIVERING, null, getDataManager().getUserID())
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

    public void apiOrderDeliveryFail(final int index, String idOrder, String note) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiOrderDeliveryFail(idOrder, note)
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

    public void apiPostCallLog(SaleOrderCallLog saleOrderCallLog) {
        checkViewAttached();
        RxUtil.dispose(mDisposableStore);
        getDataManager().apiPostCallLog(saleOrderCallLog)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableStore = d;
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void apiOrderDeliveryFinish(final int index, String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiOrderDeliveryFinish(idOrder)
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

    void apiGetListShipper(final int page) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiGetListShipper(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<List<UserProfile>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<List<UserProfile>> response) {

                        getMvpView().showListShipper(response.getData(), page);
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

    void callApiAssignOrderForOtherShipper(final int index, String idOrder, String toShip) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiAssignOrderForOtherShipper(idOrder, getDataManager().getUserID(), toShip)
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

    void callApiChangePriority(String idOrder, int priority) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiChangePriority(idOrder, priority)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String response) {

                        getMvpView().changePrioritySuccess();
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
