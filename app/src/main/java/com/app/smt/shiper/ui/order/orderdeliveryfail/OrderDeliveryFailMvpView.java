package com.app.smt.shiper.ui.order.orderdeliveryfail;

import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.ui.base.MvpView;

import java.util.List;

public interface OrderDeliveryFailMvpView extends MvpView {

    void showListOrder(List<Order> list, boolean forceRefresh);

    void loadDataError();

    void showListStore(List<Store> list);

    void updateOrderSuccess(int index);

}

