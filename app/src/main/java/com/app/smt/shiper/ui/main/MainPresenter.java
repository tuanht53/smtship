package com.app.smt.shiper.ui.main;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;
import com.app.smt.shiper.util.AppLogger;
import com.app.smt.shiper.util.RxUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {
    private Disposable mDisposable;
    private Disposable mDisposableCheck;
    private Disposable mDisposableTotalPromo;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
        if (mDisposableCheck != null) mDisposableCheck.dispose();
        if (mDisposableTotalPromo != null) mDisposableTotalPromo.dispose();
    }

    public void syncTokenFirebase() {
        final UserProfile userProfile = getDataManager().getUserProfile();
        if (userProfile == null) {
            return;
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            AppLogger.w("getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        userProfile.setFcmDeviceId(token);
                        RxUtil.dispose(mDisposable);
                        getDataManager().apiUpdateUserProfile(userProfile)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        mDisposable = d;
                                    }

                                    @Override
                                    public void onNext(@NonNull String response) {

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        AppLogger.w(e, "Error syncing.");
                                        handleApiError(e);
                                    }

                                    @Override
                                    public void onComplete() {
                                        AppLogger.i("Synced successfully!");
                                    }
                                });
                    }
                });
    }

    public LatLng getLastLocation() {
        return getDataManager().getLastLocation();
    }

    public String getLastLocationAddress() {
        return getDataManager().getLastLocationAddress();
    }

}
