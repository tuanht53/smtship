package com.app.smt.shiper.ui.photo;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class PhotosPresenter extends BasePresenter<PhotosMvpView> {
    private Disposable mDisposable;

    @Inject
    public PhotosPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(PhotosMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}
