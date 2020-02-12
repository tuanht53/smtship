package com.app.smt.shiper.ui.report;


import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.report.ReportTotal;
import com.app.smt.shiper.ui.base.MvpView;

import java.util.List;

public interface ReportMvpView extends MvpView {

    void showListOrder(List<Order> list, boolean forceRefresh);

    void loadDataError();

    void showTotal(ReportTotal reportTotal);

}

