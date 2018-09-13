package cn.gov.bjys.onlinetrain.bean.event;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */

public class LoginEvent {
    int type = 0;//0失败 1成功

    public LoginEvent(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }

}
