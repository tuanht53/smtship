package com.app.smt.shiper.ui.order;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class OrderManagerPresenter extends BasePresenter<OrderManagerMvpView> {
    private Disposable mDisposable;

    @Inject
    public OrderManagerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(OrderManagerMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}