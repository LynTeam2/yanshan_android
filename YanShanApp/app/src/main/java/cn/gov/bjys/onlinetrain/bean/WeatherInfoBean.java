package cn.gov.bjys.onlinetrain.bean;

import java.util.List;

/**
 * Created by dodozhou on 2017/9/20.
 */
public class WeatherInfoBean {
    private String city;//城市
    private int count;//
    private String date;//日期
    private String message;//信息
    private int status;//状态 200为成功
    private ListWeatherInfo data;//

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ListWeatherInfo getData() {
        return data;
    }

    public void setData(ListWeatherInfo data) {
        this.data = data;
    }

    public static class ListWeatherInfo{
        private List<detailWeatherInfo> forecast;
        private detailWeatherInfo yesterday;
        private String ganmao;//感冒
        private long pm10;//pm10
        private long pm25;//pm25
        private String quality;//空气质量
        private String shidu;//湿度
        private String wendu;//温度

        public List<detailWeatherInfo> getForecast() {
            return forecast;
        }

        public void setForecast(List<detailWeatherInfo> forecast) {
            this.forecast = forecast;
        }

        public detailWeatherInfo getYesterday() {
            return yesterday;
        }

        public void setYesterday(detailWeatherInfo yesterday) {
            this.yesterday = yesterday;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public long getPm10() {
            return pm10;
        }

        public void setPm10(long pm10) {
            this.pm10 = pm10;
        }

        public long getPm25() {
            return pm25;
        }

        public void setPm25(long pm25) {
            this.pm25 = pm25;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }
    }

    public static class detailWeatherInfo{
        private long aqi;//aqi指数
        private String date;//日期
        private String fl;//风力
        private String fx;//风向
        private String high;//最高温度
        private String low;//最低温度
        private String notice;//提醒注意
        private String sunrise;//太阳升起时间
        private String sunset;//太阳落下时间
        private String type;//天气类型

        public long getAqi() {
            return aqi;
        }

        public void setAqi(long aqi) {
            this.aqi = aqi;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getFx() {
            return fx;
        }

        public void setFx(String fx) {
            this.fx = fx;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }



}
