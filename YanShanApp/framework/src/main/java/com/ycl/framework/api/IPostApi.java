package com.ycl.framework.api;

import com.ycl.framework.bean.BannerBean;
import com.ycl.framework.utils.util.HRetrofitNetHelper;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Retrofit 测试Api Post模板
 */
public interface IPostApi {

    @POST("add")
//直接把对象通过ConverterFactory转化成对应的参数
    Call<List<BannerBean>> addUser(@Body BannerBean user);


    @Headers("Content-Type: multipart/form-data; boundary=" + HRetrofitNetHelper.boundary)
    @POST("add")
//直接把对象通过ConverterFactory转化成对应的参数
    Call<List<BannerBean>> addGS(@Body RequestBody user, @PartMap String ss);

    @POST("login")
    @FormUrlEncoded
//读参数进行urlEncoded
    Call<BannerBean> login(@Field("userId") String username, @Field("password") String password);


    @FormUrlEncoded   // @FormUrlEncoded,@FieldMap使用
    @POST("RegisterDataServlet")
    Call<BannerBean> createUser(@FieldMap Map<String, String> params);


    @Multipart  //@Multipart,@Part使用
    @POST("login")
    Call<BannerBean> regiseter(@Part("userId") String userId, @Part("password") String password);


    @Headers("Content-Type: multipart/form-data; boundary=" + HRetrofitNetHelper.boundary)
    @POST()
    Observable<String> testMT(@Body RequestBody user,@Part("Content-Length") String ss);


//    @Headers("Content-Type: multipart/form-data; boundary=" + HRetrofitNetHelper.boundary)
    @Multipart
    @POST("api")
    Call<String> testMT22(@Part("Content-Disposition form-data; name= data_type") RequestBody ss1,@Part("Content-Disposition form-data; name= data") RequestBody ss);

     Call<String> registerUser(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);


}
