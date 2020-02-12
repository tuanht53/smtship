package com.app.smt.shiper.ui.report;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.report.ReportTotal;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;
import com.app.smt.shiper.util.RxUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class ReportPresenter extends BasePresenter<ReportMvpView> {
    private Disposable mDisposable;
    private Disposable mDisposableTotal;

    @Inject
    public ReportPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(ReportMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
        if (mDisposableTotal != null) mDisposableTotal.dispose();
    }

    public void apiGetListOrderReport(String workflow, long fromDate, long toDate, int page, final boolean forceRefresh) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        if (forceRefresh)
            getMvpView().showLoading();
        getDataManager().apiGetListOrderReport(page, workflow, getDataManager().getUserID(), fromDate, toDate)
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

    public void apiGetAllTotalReport(String workflow, long fromDate, long toDate) {
        checkViewAttached();
        RxUtil.dispose(mDisposableTotal);
        getMvpView().showLoading();
        getDataManager().apiGetAllTotalReport(workflow, getDataManager().getUserID(), fromDate, toDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableTotal = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<String> response) {
                        try {
                            ReportTotal reportTotal = new Gson().fromJson(response.getData(), ReportTotal.class);
                            getMvpView().showTotal(reportTotal);
                        } catch (Exception e) {
                            getMvpView().showTotal(new ReportTotal());
                            e.printStackTrace();
                        }
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
}
