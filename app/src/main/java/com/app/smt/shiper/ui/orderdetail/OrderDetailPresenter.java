package com.app.smt.shiper.ui.orderdetail;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.ApiError;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.order.SaleOrderCallLog;
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
public class OrderDetailPresenter extends BasePresenter<OrderDetailMvpView> {
    private Disposable mDisposable;

    private Disposable mDisposableStore;

    @Inject
    public OrderDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(OrderDetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
        if (mDisposableStore != null) mDisposableStore.dispose();
    }

    public void apiOrderConfirmDelivery(String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiOrderConfirmAndDelivery(idOrder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<String> response) {
                        getMvpView().updateOrderSuccess();
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

    public void apiOrderConfirmMoveStock(String idOrder) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiOrderConfirmAndMoveStock(idOrder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<String> response) {
                        getMvpView().updateOrderSuccess();
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

    public void apiOrderDeliveryFail(String idOrder, String note) {
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
                        getMvpView().updateOrderSuccess();
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

    void apiOrderDeliveryFinish(String idOrder) {
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
                        getMvpView().updateOrderSuccess();
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

    void apiOrderMoveDelevering(String idOrder) {
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

    void callApiAssignOrderForOtherShipper(String idOrder, String toShip) {
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

                        getMvpView().updateOrderSuccess();
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

    void calApiRejectOrderForOtherShipper(String idOrder) {
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
                        getMvpView().updateOrderSuccess();
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

    void callApiRejectChangeMoney(String idOrder) {
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
                        getMvpView().updateOrderSuccess();
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

    void callApiChangeWorkflow(String idOrder) {
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
                        getMvpView().updateOrderSuccess();
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
