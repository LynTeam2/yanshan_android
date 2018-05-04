package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.HomeAnJianBean;

/**
 * Created by Administrator on 2017/12/31 0031.
 */
public class AnJianDetailActivity extends FrameActivity {

    @Bind(R.id.title_name)
    TextView title_name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    WebView content;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_anjian_layout);
    }


    private HomeAnJianBean mHomeAnJianBean;
    @Override
    public void initViews() {
        super.initViews();
        Intent recIntent = getIntent();
        Bundle recBundle = recIntent.getExtras();
        mHomeAnJianBean = (HomeAnJianBean) recBundle.get("news");
//        getRemoteDatas();

        initContent();
    }
    private void initContent(){
        title_name.setText(mHomeAnJianBean.getTitle());
        time.setText(mHomeAnJianBean.getNewsTime());
        content.loadDataWithBaseURL(null,mHomeAnJianBean.getContent(),"text/html","utf-8",null);
    }

   /* private void getRemoteDatas(){
        rx.Observable<BaseResponse<String>> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).getSpeUrlService(YSConst.BaseUrl.BASE_URL
                , HomeApi.class).getAnjianContent(mId);
        obs .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if("1".equals(stringBaseResponse.getCode())){
                            String res = stringBaseResponse.getResults();
                            AnJianDetailBean.DetailParent p= FastJSONParser.getBean(res, AnJianDetailBean.DetailParent.class);
                            title_name.setText(p.getNews().getTitle());
                            time.setText(p.getNews().getNewsTime());
                            content.setText(p.getNews().getContent());
                        }
                    }
                });
    }*/
}
