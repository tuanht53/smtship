package com.app.smt.shiper.data;

import com.app.smt.shiper.data.db.DatabaseHelper;
import com.app.smt.shiper.data.db.model.User;
import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.login.LoginRequest;
import com.app.smt.shiper.data.model.login.LoginResponse;
import com.app.smt.shiper.data.model.order.FailOrderRequest;
import com.app.smt.shiper.data.model.order.FinishOrderRequest;
import com.app.smt.shiper.data.model.order.Order;
import com.app.smt.shiper.data.model.order.SaleOrderCallLog;
import com.app.smt.shiper.data.model.priority.PriorityResquest;
import com.app.smt.shiper.data.model.store.Store;
import com.app.smt.shiper.data.model.user.UserProfile;
import com.app.smt.shiper.data.network.ApiHelper;
import com.app.smt.shiper.data.prefs.PreferencesHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

@Singleton
public class DataManager {

    private final ApiHelper mApiHelper;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ApiHelper apiHelper, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mApiHelper = apiHelper;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public void clearUser() {
        mPreferencesHelper.clear();
    }

    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    public void setUserID(String userID) {
        mPreferencesHelper.setUserID(userID);
    }

    public String getUserID() {
        return mPreferencesHelper.getUserID();
    }

    public void setUserProfile(UserProfile userProfile) {
        mPreferencesHelper.setUserProfile(userProfile);
    }

    public UserProfile getUserProfile() {
        return mPreferencesHelper.getUserProfile();
    }

    private String getAuthorization() {
        return mPreferencesHelper.getAccessToken();
    }

    public void setLastLocation(LatLng location) {
        mPreferencesHelper.setLastLocation(location);
    }

    public LatLng getLastLocation() {
        return mPreferencesHelper.getLastLocation();
    }

    public void setLastLocationAddress(String address) {
        mPreferencesHelper.setLastLocationAddress(address);
    }

    public String getLastLocationAddress() {
        return mPreferencesHelper.getLastLocationAddress();
    }

    public void setNotificationBookingID(String bookingID) {
        mPreferencesHelper.setNotificationBookingID(bookingID);
    }

    public String getNotificationBookingID() {
        return mPreferencesHelper.getNotificationBookingID();
    }

    public Observable<Boolean> insertUser(User user) {
        return mDatabaseHelper.insertUser(user);
    }

    public Observable<List<User>> getAllUsers() {
        return mDatabaseHelper.getAllUsers();
    }

    public Observable<LoginResponse> apiLogin(LoginRequest loginRequest) {
        return mApiHelper.apiLogin(loginRequest);
    }

    public Observable<String> apiLogout() {
        return mApiHelper.apiLogout(getAuthorization());
    }

    public Observable<DataResponse<UserProfile>> apiGetUserProfile(String userName) {
        return mApiHelper.apiGetUserProfile(getAuthorization(), userName);
    }

    public Observable<String> apiUpdateUserProfile(UserProfile userProfile) {
        return mApiHelper.apiUpdateUserProfile(getAuthorization(), userProfile);
    }

    public Observable<UserProfile> apiUploadAvatar(@Part MultipartBody.Part file) {
        return mApiHelper.apiUploadAvatar(getAuthorization(), file);
    }

    public Observable<DataResponse<List<Store>>> apiGetListStore(int page, String workflow, String shipperReceiverId, String shipperDeliveryId) {
        return mApiHelper.apiGetListStore(getAuthorization(), page, workflow, shipperReceiverId, shipperDeliveryId);
    }

    public Observable<DataResponse<List<Order>>> apiGetListOrder(int page, String storeId, String workflow, String storeShipperReceiveId, String storeShipperDeliveryId) {
        return mApiHelper.apiGetListOrder(getAuthorization(), page, storeId, workflow, storeShipperReceiveId, storeShipperDeliveryId);
    }

    public Observable<DataResponse<List<Order>>> apiGetListOrderReport(int page, String workflow, String storeShipperDeliveryId, long fromDate, long toDate) {
        return mApiHelper.apiGetListOrderReport(getAuthorization(), page, workflow, storeShipperDeliveryId, fromDate, toDate);
    }

    public Observable<DataResponse<String>> apiGetAllTotalReport(String workflow, String shipperDeliveryId, long fromDate, long toDate) {
        return mApiHelper.apiGetAllTotalMoney(getAuthorization(), workflow, shipperDeliveryId, fromDate, toDate);
    }

    public Observable<DataResponse<String>> apiOrderConfirmAndDelivery(String idOrder) {
        return mApiHelper.apiOrderConfirmAndDelivery(getAuthorization(), idOrder, getUserID());
    }

    public Observable<DataResponse<String>> apiOrderConfirmAndMoveStock(String idOrder) {
        return mApiHelper.apiOrderConfirmAndMoveStock(getAuthorization(), idOrder);
    }

    public Observable<String> apiOrderDeliveryFinish(String idOrder) {
        FinishOrderRequest finishOrderRequest = new FinishOrderRequest();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(idOrder);
        finishOrderRequest.setSaleOrderIds(arrayList);
        return mApiHelper.apiOrderDeliveryFinish(getAuthorization(), finishOrderRequest);
    }

    public Observable<String> apiOrderDeliveryFail(String idOrder, String note) {
        FailOrderRequest failOrderRequest = new FailOrderRequest();
        failOrderRequest.setSaleOrderId(idOrder);
        failOrderRequest.setNote(note);
        return mApiHelper.apiOrderDeliveryFail(getAuthorization(), failOrderRequest);
    }

    public Observable<DataResponse<String>> apiChangeWorkflow(String idOrder, String workflow) {
        return mApiHelper.apiChangeWorkflow(getAuthorization(), idOrder, workflow);
    }

    public Observable<DataResponse<Order>> apiFindOrderByCode(String code) {
        return mApiHelper.apiFindOrderByCode(getAuthorization(), code);
    }

    public Observable<DataResponse<List<UserProfile>>> apiGetListShipper(int page) {
        return mApiHelper.apiGetListShipper(getAuthorization(), page);
    }

    public Observable<String> apiAssignOrderForOtherShipper(String idOrder, String fromShip, String toShip) {
        return mApiHelper.apiAssignOrderForOtherShipper(getAuthorization(), idOrder, fromShip, toShip);
    }

    public Observable<String> apiRejectOrderForOtherShipper(String idOrder) {
        return mApiHelper.apiRejectOrderForOtherShipper(getAuthorization(), idOrder);
    }

    public Observable<String> apiRejectChangeMoney(String idOrder) {
        return mApiHelper.apiRejectChangeMoney(getAuthorization(), idOrder);
    }

    public Observable<String> apiChangePriority(String idOrder, int priority) {
        PriorityResquest priorityResquest = new PriorityResquest(idOrder, priority);
        return mApiHelper.apiChangePriority(getAuthorization(), priorityResquest);
    }

    public Observable<String> apiPostCallLog(SaleOrderCallLog saleOrderCallLog) {
        return mApiHelper.apiPostCallLog(getAuthorization(), saleOrderCallLog);
    }

}
