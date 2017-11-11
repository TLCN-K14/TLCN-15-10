package com.hcmute.trietthao.yourtime.service;

import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.response.InsertUserResponse;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xuant on 13/05/2017.
 */

public interface Service { //Định nghĩa các REST API (Api Services) cho Retrofit
   // Hàm  get user by email and passw
    @GET("/getuser")
    Call<ArrayList<NguoiDungModel>> getUserByEmail(@Query("email") String email);
    @GET("/getalluser")
    Call<ArrayList<NguoiDungModel>> getListUser();

    @FormUrlEncoded
    @POST("/insertuser")
    Call<InsertUserResponse> insertUser(@Field("idnguoidung") int idnguoidung, @Field("tennguoidung") String tennguoidung, @Field("anhdaidien") String anhdaidien,
                                        @Field("username") String username, @Field("passw") String passw);

//    // Hàm  lấy review theo itemid
//    @GET("/getreview")
//    Call<ArrayList<ReviewWhere>> getReview_ByItem(@Query("itemid") int itemid);
//    // Hàm  lấy list user
//    @GET("/getlistuser")
//    Call<ArrayList<User>> getListUser();
//    // Hàm  lấy user theo mail
//    @GET("/getuser")
//    Call<ArrayList<User>> getUser(@Query("mail") String mail);
//    // Hàm  lấy item what
//    @FormUrlEncoded
//    @POST("/insertitemwhat")
//    Call<ArrayList<ItemWhat>> insertItemWhat(@Field("address") String address, @Field("name") String name,
//                                             @Field("foodname") String foodname, @Field("img") String img,
//                                             @Field("districtid") int districtid, @Field("typeid") int typeid,
//                                             @Field("username") String username, @Field("useravatar") String useravatar,
//                                             @Field("cityid") int cityid, @Field("userid") int userid);
//    // Cập nhật lại ảnh user
//    @FormUrlEncoded
//    @POST("/updatetimguser")
//    Call<ArrayList<User>> updateImgUser(@Field("img") String img,
//                                        @Field("mail") String mail);
//   // Thay đổi pass user
//    @FormUrlEncoded
//    @POST("/updatetpassuser")
//    Call<ArrayList<User>> updatePassUser(@Field("pass") String pass,
//                                         @Field("mail") String mail);
//
//
//    // Hàm  update thông tin user
//    @FormUrlEncoded
//    @POST("/updatetuser")
//    Call<ArrayList<User>> updateUser(@Field("username") String username, @Field("name") String name,
//                                     @Field("lastname") String lastname, @Field("status") String status,
//                                     @Field("sex") String sex, @Field("birthday") String birthday,
//                                     @Field("mail") String mail);
//    // thêm user mới
//    @FormUrlEncoded
//    @POST("/insertuser")
//    Call<ArrayList<User>> insertUser(@Field("mail") String mail, @Field("username") String username,
//                                     @Field("pass") String pass);
//    // Hàm  lấy item what
//    @GET("/getitemwhat")
//    Call<ArrayList<ItemWhat>> getItemWhat(@Query("categoryid") int categoryID, @Query("typeid") int typeID, @Query("districtid") int districtID, @Query("cityid") int cityid);

}
