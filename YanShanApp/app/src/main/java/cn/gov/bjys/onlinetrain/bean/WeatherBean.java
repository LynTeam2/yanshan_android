package cn.gov.bjys.onlinetrain.bean;

import java.util.List;

/**
 * Created by dodo on 2018/3/7.
 */

public class WeatherBean {
    private String date;//: "20180307",
    private String message;// "Success !",
    private int status;// 200,
    private String city;// "杭州",
    private int count;// 914,
    private WeatherData data;//

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

    public WeatherData getData() {
        return data;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }

    public static class WeatherData {
        private String shidu;// "89%",
        private float pm25;// 22.0,
        private float pm10;// 44.0,
        private String quality;// "优",
        private String wendu;// "6",
        private String ganmao;// "各类人群可自由活动",
        private WeatherDay yesterday;//
        private List<WeatherDay> forecast;//

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public float getPm25() {
            return pm25;
        }

        public void setPm25(float pm25) {
            this.pm25 = pm25;
        }

        public float getPm10() {
            return pm10;
        }

        public void setPm10(float pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public WeatherDay getYesterday() {
            return yesterday;
        }

        public void setYesterday(WeatherDay yesterday) {
            this.yesterday = yesterday;
        }

        public List<WeatherDay> getForecast() {
            return forecast;
        }

        public void setForecast(List<WeatherDay> forecast) {
            this.forecast = forecast;
        }
    }

    public static class WeatherDay {
        private String date;//: "07日星期三",
        private String sunrise;//: "06:21",
        private String high;//: "高温 10.0℃",
        private String low;// "低温 6.0℃",
        private String sunset;// "18:02",
        private float aqi;// 39.0,
        private String fx;// "东风",
        private String fl;//: "<3级",
        private String type;// "中雨",
        private String notice;// "记得随身携带雨伞哦"

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
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

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public float getAqi() {
            return aqi;
        }

        public void setAqi(float aqi) {
            this.aqi = aqi;
        }

        public String getFx() {
            return fx;
        }

        public void setFx(String fx) {
            this.fx = fx;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }
    }
}
