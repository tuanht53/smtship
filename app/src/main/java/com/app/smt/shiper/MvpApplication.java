package com.app.smt.shiper;

import android.app.Application;
import android.content.Context;

import com.app.smt.shiper.di.component.ApplicationComponent;
import com.app.smt.shiper.di.component.DaggerApplicationComponent;
import com.app.smt.shiper.di.module.ApplicationModule;
import com.app.smt.shiper.util.AppLogger;

import java.net.URISyntaxException;

import javax.inject.Inject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MvpApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        AppLogger.init();

        if (BuildConfig.DEBUG) {
//            Fabric.with(this, new Crashlytics());
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public static MvpApplication get(Context context) {
        return (MvpApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    private Socket mSocket;
    {
        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};
            opts.path = "/socket/ws";
            mSocket = IO.socket(BuildConfig.SOCKET_URL, opts);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
