package cn.gov.bjys.onlinetrain.api;

import com.ycl.framework.utils.util.BaseResp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dodozhou on 2017/9/20.
 */
public interface WeatherApi {
    @GET("open/api/weather/json.shtml")
    Observable<String> getWeatherInfos(@Query("city") String cityName);
}
