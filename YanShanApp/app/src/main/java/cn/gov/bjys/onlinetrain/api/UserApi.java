package cn.gov.bjys.onlinetrain.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by dodozhou on 2017/8/22.
 */
public interface UserApi {
    @Streaming
    @POST("Video/2017/02/18/mp4/170218171317773949.mp4")
    Call<ResponseBody> getFileDownLoad();

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
    Observable <BaseResponse<String>> getLaws(@Query("page") int page, @Query("size") int size,@Query("type") long type);




}
