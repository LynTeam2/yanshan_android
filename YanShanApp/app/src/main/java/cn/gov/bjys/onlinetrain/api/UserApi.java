package cn.gov.bjys.onlinetrain.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * Created by dodozhou on 2017/8/22.
 */
public interface UserApi {
    @Streaming
    @POST("Video/2017/02/18/mp4/170218171317773949.mp4")
    Call<ResponseBody> getFileDownLoad();

}
