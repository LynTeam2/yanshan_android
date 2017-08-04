package cn.gov.bjys.onlinetrain;

import android.content.Context;

import com.mcxiaoke.packer.helper.PackerNg;
import com.umeng.analytics.MobclickAgent;
import com.ycl.framework.base.FrameApplication;

/**
 * Created by dodozhou on 2017/8/2.
 */
public class BaseApplication extends FrameApplication {
    public static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        init360WithUmeng();
    }


    public static Context getBaseApplication(){
        return  ctx;
    }
    public static Context getAppContext() {
        return ctx;
    }

    private void init360WithUmeng(){
        // 如果没有使用 PackerNg 打包添加渠道，默认返回的是""
        // com.mcxiaoke.packer.helper.PackerNg
        final String market = PackerNg.getMarket(this);
        // 或者使用 PackerNg.getMarket(Context,defaultValue);
        // 之后就可以使用了，比如友盟可以这样设置
        MobclickAgent. startWithConfigure( new MobclickAgent.UMAnalyticsConfig(getBaseApplication(), "5959a1c204e2053cda000474", market, MobclickAgent.EScenarioType.E_UM_NORMAL,true));
    }
}
