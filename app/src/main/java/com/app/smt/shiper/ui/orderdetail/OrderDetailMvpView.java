package com.app.smt.shiper.ui.orderdetail;

import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.ui.base.MvpView;

import java.util.List;

public interface OrderDetailMvpView extends MvpView {

    void updateOrderSuccess();

    void showListShipper(List<UserProfile> list, int page);

}
