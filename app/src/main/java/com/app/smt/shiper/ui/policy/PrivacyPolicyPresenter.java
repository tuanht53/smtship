package com.app.smt.shiper.ui.policy;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class PrivacyPolicyPresenter extends BasePresenter<PrivacyPolicyMvpView> {
    private Disposable mDisposable;

    @Inject
    public PrivacyPolicyPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(PrivacyPolicyMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}