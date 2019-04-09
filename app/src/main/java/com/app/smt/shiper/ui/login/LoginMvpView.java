package com.app.smt.shiper.ui.login;


import com.app.smt.shiper.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void loginSuccess();

    void getUserInfoSuccess();

    void openMainActivity();

}
