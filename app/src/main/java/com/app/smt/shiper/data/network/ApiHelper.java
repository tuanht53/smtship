package com.app.smt.shiper.data.network;


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

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiHelper {

    @POST("/auth/v2/signin")
    Observable<LoginResponse> apiLogin(@Body LoginRequest tokenRequest);

    @POST("/auth/signout")
    Observable<String> apiLogout(@Header("X-Auth-Token") String authorization);

    @GET("/shipper/findByUserName")
    Observable<DataResponse<UserProfile>> apiGetUserProfile(@Header("X-Auth-Token") String authorization, @Query("userName") String userName);

    @POST("/shipper/save")
    Observable<String> apiUpdateUserProfile(@Header("X-Auth-Token") String authorization, @Body UserProfile userProfile);

    @GET("/stores/getListStoreHasSaleOrder")
    Observable<DataResponse<List<Store>>> apiGetListStore(@Header("X-Auth-Token") String authorization, @Query("page") int page, @Query("workflow") String workflow, @Query("shipperReceiverId") String shipperReceiverId, @Query("shipperDeliveryId") String shipperDeliveryId);

    @GET("/stock/sale-order/getList")
    Observable<DataResponse<List<Order>>> apiGetListOrder(@Header("X-Auth-Token") String authorization, @Query("page") int page, @Query("storeId") String storeId, @Query("workflow") String workflow, @Query("storeShipperReceiveId") String storeShipperReceiveId, @Query("storeShipperDeliveryId") String storeShipperDeliveryId);

    @GET("/stock/sale-order/confirmAndDelivery")
    Observable<DataResponse<String>> apiOrderConfirmAndDelivery(@Header("X-Auth-Token") String authorization, @Query("id") String id, @Query("shipperDeliveryId") String shipperDeliveryId);

    @GET("/stock/sale-order/confirmAndMoveStock")
    Observable<DataResponse<String>> apiOrderConfirmAndMoveStock(@Header("X-Auth-Token") String authorization, @Query("id") String id);

    @POST("/stock/sale-order/finishSaleOrder")
    Observable<String> apiOrderDeliveryFinish(@Header("X-Auth-Token") String authorization, @Body FinishOrderRequest finishOrderRequest);

    @POST("/stock/sale-order/saleOrderDeliveryFail")
    Observable<String> apiOrderDeliveryFail(@Header("X-Auth-Token") String authorization, @Body FailOrderRequest failOrderRequest);

    @GET("/stock/sale-order/changeWorkflow")
    Observable<DataResponse<String>> apiChangeWorkflow(@Header("X-Auth-Token") String authorization, @Query("id") String id, @Query("workflow") String workflow);

    @GET("/stock/sale-order/findByCode")
    Observable<DataResponse<Order>> apiFindOrderByCode(@Header("X-Auth-Token") String authorization, @Query("code") String code);

    @Multipart
    @POST("/pricingsku/customers/upload-avatar")
    Observable<UserProfile> apiUploadAvatar(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    @GET("/stock/sale-order/getList")
    Observable<DataResponse<List<Order>>> apiGetListOrderReport(@Header("X-Auth-Token") String authorization, @Query("page") int page, @Query("workflow") String workflow, @Query("storeShipperDeliveryId") String storeShipperDeliveryId, @Query("fromDate") long fromDate, @Query("toDate") long toDate);

    @GET("/stock/sale-order/store/getAllTotalMoney")
    Observable<DataResponse<String>> apiGetAllTotalMoney(@Header("X-Auth-Token") String authorization, @Query("workflow") String workflow, @Query("shipperDeliveryId") String shipperDeliveryId, @Query("fromDate") long fromDate, @Query("toDate") long toDate);

    @GET("/shipper/getList?status=1")
    Observable<DataResponse<List<UserProfile>>> apiGetListShipper(@Header("X-Auth-Token") String authorization, @Query("page") int page);

    @GET("/stock/sale-order/assignOrderForOtherShipper")
    Observable<String> apiAssignOrderForOtherShipper(@Header("X-Auth-Token") String authorization, @Query("id") String idOrder, @Query("fromShip") String fromShip, @Query("toShip") String toShip);

    @GET("/stock/sale-order/rejectOrderForOtherShipper")
    Observable<String> apiRejectOrderForOtherShipper(@Header("X-Auth-Token") String authorization, @Query("id") String idOrder);

    @GET("/stock/sale-order/rejectChangeMoney")
    Observable<String> apiRejectChangeMoney(@Header("X-Auth-Token") String authorization, @Query("id") String idOrder);

    @POST("/stock/sale-order/changePrioritySaleOrder")
    Observable<String> apiChangePriority(@Header("X-Auth-Token") String authorization, @Body PriorityResquest priorityResquest);

    @POST("/stock/sale-order/updateCallLog")
    Observable<String> apiPostCallLog(@Header("X-Auth-Token") String authorization, @Body SaleOrderCallLog saleOrderCallLog);
}
