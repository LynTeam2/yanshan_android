package com.ycl.framework.api;

import com.ycl.framework.bean.BannerBean;
import com.ycl.framework.utils.util.BaseResp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Retrofit 测试Api Get模板
 */
public interface IGetApi {
    @GET("users")//不带参数get请求
    Call<List<BannerBean>> getUsers();


    @GET("LoginDataServlet")  //拼接参数 @Query使用
    @Headers("Cache-Control: public, max-age=300")   //Cache-Control缓存控制
    Call<BaseResp<BannerBean>> userLogin(@Query("username") String username, @Query("password") String password);

    @GET("users/{groupId}")//动态路径get请求  @QueryMAp使用
    Call<List<BannerBean>> getUsers(@Path("userId") String userId, @QueryMap HashMap<String, String> paramsMap);


    @GET("/user")
    Observable<BannerBean> getUser(@Query("userId") String userId);

}
