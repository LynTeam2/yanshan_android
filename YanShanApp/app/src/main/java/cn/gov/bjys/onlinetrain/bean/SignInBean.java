package cn.gov.bjys.onlinetrain.bean;


public class SignInBean {

        public final static int FINISH = 1;
        public final static int ING = 2;
        public final static int FUTURE = 3;
        public final static int SUC = 4;

        private String mouth;
        private String day;
        private int type;

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
