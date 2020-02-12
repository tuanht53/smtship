package com.app.smt.shiper.ui.user;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.user.UserProfile;
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
public class MenuUserPresenter extends BasePresenter<MenuUserMvpView> {
    private Disposable mDisposable;

    @Inject
    public MenuUserPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(MenuUserMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public UserProfile getUserProfile() {
        return getDataManager().getUserProfile();
    }

    public void callApiLogout() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiLogout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String response) {
                        getMvpView().logoutSuccess();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().logoutSuccess();
                        // handle error here
                        //handleApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                    }
                });
    }
}
