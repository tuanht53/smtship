package com.app.smt.shiper.ui.qrcode;

import android.text.TextUtils;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;
import com.app.smt.shiper.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class ScanPresenter extends BasePresenter<ScanMvpView> {
    private Disposable mDisposable;

    @Inject
    public ScanPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(ScanMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void callApiFindOrderByCode(String code) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
//        getMvpView().showLoading();
        getDataManager().apiFindOrderByCode(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<Order>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<Order> response) {
                        if (TextUtils.isEmpty(response.getMessage())) {
                            getMvpView().onScanCodeSuccess(response.getData());
                        } else {
                            getMvpView().onScanCodeFail(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().onScanCodeFail("");
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