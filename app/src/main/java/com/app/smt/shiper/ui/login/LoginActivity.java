package com.app.smt.shiper.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.login.LoginRequest;
import com.app.smt.shiper.ui.base.BaseActivity;
import com.app.smt.shiper.ui.main.MainActivity;
import com.app.smt.shiper.util.SecurityUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginPresenter mPresenter;

    @BindView(R.id.phone)
    EditText mPhoneView;

    @BindView(R.id.password)
    EditText mPasswordView;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPresenter.attachView(this);
        setup();

    }

    private void setup() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.contains("0");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void onClickBtnLogin() {
        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
        }

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserCode(phone);
        loginRequest.setPassword(SecurityUtil.SHA1(password));
        mPresenter.apiLogin(loginRequest);
    }

    @Override
    public void loginSuccess() {
        mPresenter.apiGetUserInfo(mPhoneView.getText().toString());
    }

    @Override
    public void getUserInfoSuccess() {
        openMainActivity();
    }
}
