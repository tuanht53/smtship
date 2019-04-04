package com.app.smt.shiper.di.component;

import android.app.Application;
import android.content.Context;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.db.DatabaseHelper;
import com.app.smt.shiper.data.network.ApiHelper;
import com.app.smt.shiper.data.prefs.PreferencesHelper;
import com.app.smt.shiper.di.ApplicationContext;
import com.app.smt.shiper.di.module.ApplicationModule;
import com.app.smt.shiper.di.module.NetModule;
import com.app.smt.shiper.service.MyFirebaseMessagingService;
import com.app.smt.shiper.service.SyncService;
import com.app.smt.shiper.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class})
public interface ApplicationComponent {

    void inject(SyncService syncService);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);

    @ApplicationContext
    Context context();
    Application application();
    ApiHelper ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
