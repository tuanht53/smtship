package com.app.smt.shiper.data.network;


import com.app.smt.shiper.data.model.DataResponse;
import com.app.smt.shiper.data.model.login.LoginRequest;
import com.app.smt.shiper.data.model.login.LoginResponse;
import com.app.smt.shiper.data.model.user.UserProfile;

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

    @GET("/shipper/findByUserName")
    Observable<DataResponse<UserProfile>> apiGetUserProfile(@Header("X-Auth-Token") String authorization, @Query("userName") String userName);

    @POST("/shipper/save")
    Observable<String> apiUpdateUserProfile(@Header("X-Auth-Token") String authorization, @Body UserProfile userProfile);

    @Multipart
    @POST("/pricingsku/customers/upload-avatar")
    Observable<UserProfile> apiUploadAvatar(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

}
