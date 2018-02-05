package cn.gov.bjys.onlinetrain.api;

import com.ycl.framework.utils.util.BaseResp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dodozhou on 2017/9/25.
 */
public interface HomeApi {
    @POST("/consume/buy_package.ucs")
    Observable<String> getHomeBannerDatas(@Body RequestBody body);

    @Streaming
    @GET
    Observable <ResponseBody> downZipPacket(@Url String url);

    @GET("api/news")
    Observable <BaseResponse<String>> getAnjianDatas(@Query("page") int page,@Query("size") int size);

    @GET("api/news/{id}")
    Observable <BaseResponse<String>> getAnjianContent(@Path("id") long id);
}
