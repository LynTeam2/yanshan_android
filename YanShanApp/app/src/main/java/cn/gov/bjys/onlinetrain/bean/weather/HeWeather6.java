package cn.gov.bjys.onlinetrain.bean.weather;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class HeWeather6 {

    private List<Forecast> daily_forecast;//
    private List<Hourly> hourly;//
    private List<LifeStyle> lifestyle;//
    private WeatherNow  now;//
    private Basic basic;//

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<Forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<LifeStyle> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<LifeStyle> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public WeatherNow getNow() {
        return now;
    }

    public void setNow(WeatherNow now) {
        this.now = now;
    }

    public static class WeatherJson{
        List<HeWeather6> HeWeather6;//

        public List<cn.gov.bjys.onlinetrain.bean.weather.HeWeather6> getHeWeather6() {
            return HeWeather6;
        }

        public void setHeWeather6(List<cn.gov.bjys.onlinetrain.bean.weather.HeWeather6> heWeather6) {
            HeWeather6 = heWeather6;
        }
    }

}
