package com.app.smt.shiper.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.smt.shiper.BuildConfig;
import com.app.smt.shiper.R;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.di.component.ActivityComponent;
import com.app.smt.shiper.ui.base.BaseFragment;
import com.app.smt.shiper.util.dialog.DialogUtils;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuUserFragment extends BaseFragment implements MenuUserMvpView {

    public static final String TAG = MenuUserFragment.class.getSimpleName();

    private static final int UPDATE_USER_REQUEST_CODE = 111;

    @Inject
    MenuUserPresenter mPresenter;

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @BindView(R.id.img_avatar)
    ImageView mAvatar;

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_rating)
    TextView mTvRating;

    private UserProfile mUser;

    private AlertDialog mAlertDialog;

    // TODO: Rename and change types of parameters

    public MenuUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuUserFragment newInstance() {
        MenuUserFragment fragment = new MenuUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_user, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            ButterKnife.bind(this, view);
            mPresenter.attachView(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        mTvVersion.setText(getResources().getString(R.string.menu_version_app, BuildConfig.VERSION_NAME));
        mUser = mPresenter.getUserProfile();
        showUserProfile();
    }

    @Override
    public void onDetach() {
        mPresenter.detachView();
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void showUserProfile() {
        if (!TextUtils.isEmpty(mUser.getFullName())) {
            mTvUserName.setText(mUser.getFullName());
        }
        Glide.with(this)
                .load(mUser.getFullName())
                .error(R.drawable.ic_profile)
                .placeholder(R.drawable.ic_profile)
                .into(mAvatar);
    }

    @OnClick(R.id.img_avatar)
    public void onClickBtnInfoUser() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_edit)
    public void onClickBtnEdit() {
        onClickBtnInfoUser();
    }

    @OnClick(R.id.btn_history)
    public void onClickBtnHistory() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_notification)
    public void onClickBtnNotification() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_payment)
    public void onClickBtnPayment() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_contact_support)
    public void onClickBtnContact() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_support)
    public void onClickBtnSupport() {
        showMessage(R.string.message_service_not_avaiable);
    }

    @OnClick(R.id.btn_logout)
    public void onClickBtnLogout() {
        mAlertDialog = DialogUtils.showDialogDefault(getActivity(), getResources().getString(R.string.close_app_confirm),
                getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new DialogUtils.DialogCallback() {
                    @Override
                    public void cancelDialog() {
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void okDialog() {
                        mAlertDialog.dismiss();
                        mPresenter.callApiLogout();
                    }
                });
        mAlertDialog.show();
    }

    @Override
    public void logoutSuccess() {
        mPresenter.setUserAsLoggedOut();
        openActivityOnTokenExpire();
    }
}
