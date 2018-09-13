package cn.gov.bjys.onlinetrain.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;


public interface UserApi {
    @Streaming
    @POST("Video/2017/02/18/mp4/170218171317773949.mp4")
    Call<ResponseBody> getFileDownLoad();

    @POST("api/exam")
    Observable<BaseResponse<String>> postExamPager(@Body RequestBody body);

    @POST("login")
    Observable<BaseResponse<String>> userLogin(@Body RequestBody body);

    @GET("logout")
    Observable<BaseResponse<String>> userLogout();

    @PUT("api/user/nickname")
    Observable<BaseResponse<String>> uploadNickName(@Body RequestBody body);

    @PUT("api/user/icon")
    Observable<BaseResponse<String>> uploadUserAvatar(@Body RequestBody body);

    @POST("upload")
    Observable<BaseResponse<String>> remoteAvatar(@Body RequestBody body);

    @PUT("api/user/bean")
    Observable<BaseResponse<String>> upLoadWealthValue(@Body RequestBody body);

    @GET("api/law")
    Observable<BaseResponse<String>> getLaws(@Query("page") int page, @Query("size") int size, @Query("type") long type);

    @GET("api/search")
    Observable<BaseResponse<String>> search(@Query("query") String input);

    @GET("api/exam/{id}")
    Observable<BaseResponse<String>> getExamCounts(@Path("id") long id);//0考试 1补考 2不给考

    @GET("api/exam")
    Observable<BaseResponse<String>> getUserExamHistory();//获取用户历史考试信息

    @POST("api/course/{id}")
    Observable<BaseResponse<String>> postCourseDuration(@Path("id") long id, @Body RequestBody body);

}
