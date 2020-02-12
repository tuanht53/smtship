package com.app.smt.shiper.ui.qrcode;

import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.ui.base.MvpView;

public interface ScanMvpView extends MvpView {

    void onScanCodeFail(String message);

    void onScanCodeSuccess(Order order);

}
