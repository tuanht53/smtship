package com.app.smt.shiper.ui.order.orderdelivering;


import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.ui.base.MvpView;

import java.util.List;

public interface OrderDeliveringMvpView extends MvpView {

    void showListOrder(List<Order> list, boolean forceRefresh);

    void loadDataError();

    void showListStore(List<Store> list);

    void updateOrderSuccess(int index);

    void showListShipper(List<UserProfile> list, int page);

    void changePrioritySuccess();

}
