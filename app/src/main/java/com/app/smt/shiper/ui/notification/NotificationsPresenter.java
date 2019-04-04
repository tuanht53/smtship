package com.app.smt.shiper.ui.notification;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class NotificationsPresenter extends BasePresenter<NotificationsMvpView> {
    private Disposable mDisposable;

    @Inject
    public NotificationsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(NotificationsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}