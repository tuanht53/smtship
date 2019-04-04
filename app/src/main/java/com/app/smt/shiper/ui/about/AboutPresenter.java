package com.app.smt.shiper.ui.about;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class AboutPresenter extends BasePresenter<AboutMvpView> {
    private Disposable mDisposable;

    @Inject
    public AboutPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(AboutMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}
