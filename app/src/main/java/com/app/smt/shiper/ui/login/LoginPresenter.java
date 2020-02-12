package com.app.smt.shiper.ui.login;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.login.LoginRequest;
import com.app.smt.shiper.data.model.login.LoginResponse;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.ui.base.BasePresenter;
import com.app.smt.shiper.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private Disposable mDisposable;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void apiLogin(LoginRequest loginRequest) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiLogin(loginRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse response) {
                        if (response != null) {
                            getDataManager().setAccessToken(response.getToken());
                        }
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
                        getMvpView().loginSuccess();
                    }
                });
    }

    public void apiGetUserInfo(String userName) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        getMvpView().showLoading();
        getDataManager().apiGetUserProfile(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DataResponse<UserProfile>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull DataResponse<UserProfile> response) {
                        if (response != null) {
                            getDataManager().setUserID(response.getData().getTxtId());
                            getDataManager().setUserProfile(response.getData());
                            getMvpView().getUserInfoSuccess();
                        }
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