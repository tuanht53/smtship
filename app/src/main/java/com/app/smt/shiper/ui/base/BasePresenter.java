package com.app.smt.shiper.ui.base;


import com.app.smt.shiper.R;
import com.app.smt.shiper.data.DataManager;
import com.app.smt.shiper.data.model.ApiError;
import com.app.smt.shiper.util.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import retrofit2.HttpException;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    private final DataManager mDataManager;

    @Inject
    public BasePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    @Override
    public void handleApiError(Throwable error) {
        if (error instanceof HttpException) {
            if (((HttpException) error).response() == null) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            if (((HttpException) error).code() == AppConstants.API_STATUS_CODE_LOCAL_ERROR) {
                getMvpView().onError(R.string.connection_error);
                return;
            }

            if (((HttpException) error).code() == AppConstants.API_STATUS_CODE_LOCAL_ERROR) {
                getMvpView().onError(R.string.api_retry_error);
                return;
            }

            final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();
            try {
                ApiError apiError = gson.fromJson(((HttpException) error).response().errorBody().string(), ApiError.class);

                if (apiError == null || apiError.getMessage() == null) {
                    getMvpView().onError(R.string.api_default_error);
                    return;
                }

                switch (((HttpException) error).code()) {
                    case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    case HttpsURLConnection.HTTP_FORBIDDEN:
                        setUserAsLoggedOut();
                        getMvpView().openActivityOnTokenExpire();
                    case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    case HttpsURLConnection.HTTP_NOT_FOUND:
                    default:
                        getMvpView().onError(apiError.getMessage());
                }
            } catch (JsonSyntaxException | NullPointerException | IOException ex) {
                getMvpView().onError(R.string.api_default_error);
            }
        } else {
            getMvpView().onError(R.string.connection_error);
        }
    }

    @Override
    public void setUserAsLoggedOut() {
        mDataManager.clearUser();
    }
}

