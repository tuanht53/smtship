package com.app.smt.shiper.di.component;

import com.app.smt.shiper.di.PerActivity;
import com.app.smt.shiper.di.module.ActivityModule;
import com.app.smt.shiper.ui.about.AboutActivity;
import com.app.smt.shiper.ui.login.LoginActivity;
import com.app.smt.shiper.ui.main.MainActivity;
import com.app.smt.shiper.ui.order.orderconfirm.OrderConfirmFragment;
import com.app.smt.shiper.ui.report.ReportFragment;
import com.app.smt.shiper.ui.notification.NotificationsFragment;
import com.app.smt.shiper.ui.order.OrderManagerFragment;
import com.app.smt.shiper.ui.order.orderdelivering.OrderDeliveringFragment;
import com.app.smt.shiper.ui.order.orderdeliveryfail.OrderDeliveryFailFragment;
import com.app.smt.shiper.ui.order.orderreceiver.OrderReceiverFragment;
import com.app.smt.shiper.ui.orderdetail.OrderDetailActivity;
import com.app.smt.shiper.ui.qrcode.ScanActivity;
import com.app.smt.shiper.ui.user.MenuUserFragment;
import com.app.smt.shiper.ui.policy.PrivacyPolicyActivity;
import com.app.smt.shiper.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(AboutActivity aboutActivity);

    void inject(PrivacyPolicyActivity privacyPolicyActivity);

    void inject(ReportFragment reportFragment);

    void inject(NotificationsFragment notificationsFragment);

    void inject(MenuUserFragment menuUserFragment);

    void inject(LoginActivity loginActivity);

    void inject(OrderManagerFragment orderManagerFragment);

    void inject(OrderReceiverFragment orderReceiverFragment);

    void inject(OrderDeliveringFragment orderDeliveringFragment);

    void inject(OrderDeliveryFailFragment orderMoveStockFragment);

    void inject(OrderDetailActivity orderDetailActivity);

    void inject(ScanActivity scanActivity);

    void inject(OrderConfirmFragment orderConfirmFragment);

}
