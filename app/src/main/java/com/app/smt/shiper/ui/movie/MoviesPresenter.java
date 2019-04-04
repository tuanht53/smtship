package com.app.smt.shiper.ui.movie;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class MoviesPresenter extends BasePresenter<MoviesMvpView> {
    private Disposable mDisposable;

    @Inject
    public MoviesPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(MoviesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
}
