package cn.gov.bjys.onlinetrain.api;

import com.ycl.framework.utils.util.BaseResp;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by dodozhou on 2017/9/25.
 */
public interface HomeApi {
    @POST("/consume/buy_package.ucs")
    Observable<String> getHomeBannerDatas(@Body RequestBody body);
}
