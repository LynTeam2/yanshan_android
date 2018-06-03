package cn.gov.bjys.onlinetrain.api;

import com.ycl.framework.utils.util.BaseResp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface WeatherApi {
    @GET("open/api/weather/json.shtml")
    Observable<String> getWeatherInfos(@Query("city") String cityName);

    @GET("s6/weather")
    Observable<String> getNewWeatherInfos(@Query("key") String cityName,@Query("location") String location);
}
