package com.app.smt.shiper.ui.order.orderreceiver;

import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.ui.base.MvpView;

import java.util.List;

public interface OrderReceiverMvpView extends MvpView {

    void showListOrder(List<Order> list, boolean forceRefresh);

    void loadDataError();

    void showListStore(List<Store> list);

    void updateOrderMoveStockSuccess(int index);

    void updateOrderDeliveringSuccess(int index);

}
