package com.app.smt.shiper.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.db.AppDatabase;
import com.app.smt.shiper.data.network.ApiHelper;
import com.app.smt.shiper.di.ApplicationContext;
import com.app.smt.shiper.di.DatabaseInfo;
import com.app.smt.shiper.util.AppConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiHelper provideCaroService(@Named("smt_retrofit") Retrofit retrofit) {
        return  retrofit.create(ApiHelper.class);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName) {
        return Room.databaseBuilder(mApplication, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/San-Francisco-Pro-Fonts/SF-Pro-Display-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

}
