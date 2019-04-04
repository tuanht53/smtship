package com.app.smt.shiper.di.component;

import com.app.smt.shiper.di.PerActivity;
import com.app.smt.shiper.di.module.ActivityModule;
import com.app.smt.shiper.ui.about.AboutActivity;
import com.app.smt.shiper.ui.login.LoginActivity;
import com.app.smt.shiper.ui.main.MainActivity;
import com.app.smt.shiper.ui.movie.MoviesFragment;
import com.app.smt.shiper.ui.notification.NotificationsFragment;
import com.app.smt.shiper.ui.photo.PhotosFragment;
import com.app.smt.shiper.ui.policy.PrivacyPolicyActivity;
import com.app.smt.shiper.ui.setting.SettingsFragment;
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

    void inject(SettingsFragment settingsFragment);

    void inject(MoviesFragment moviesFragment);

    void inject(NotificationsFragment notificationsFragment);

    void inject(PhotosFragment photosFragment);

    void inject(LoginActivity loginActivity);

}
