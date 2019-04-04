/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.app.smt.shiper.ui.splash;

import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.di.ConfigPersistent;
import com.app.smt.shiper.ui.base.BasePresenter;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


@ConfigPersistent
public class SplashPresenter extends BasePresenter<SplashMvpView> {
    private Disposable mDisposable;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(SplashMvpView mvpView) {
        super.attachView(mvpView);
        getMvpView().startSyncService();
        decideNextActivity();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void decideNextActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (getDataManager().getAccessToken() == null) {
                    if (getMvpView() != null)
                        getMvpView().openLoginActivity();
                } else {
                    if (getMvpView() != null)
                        getMvpView().openMainActivity();
                }
            }
        }, 1500);
    }
}
