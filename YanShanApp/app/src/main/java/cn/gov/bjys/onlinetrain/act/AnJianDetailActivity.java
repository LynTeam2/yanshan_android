package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.ToastUtil;

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

        String header = "<!-- Include stylesheet -->\n" +
                "<link href=\"https://cdn.quilljs.com/1.3.6/quill.bubble.css\" rel=\"stylesheet\">\n" +
                "\n" +
                "<!-- Create the editor container -->\n" +
                "<div class=\"ql-container ql-bubble\" style=\"height:100%;width:100%\">\n" +
                "\t<div class=\"ql-editor\" style=\"height:100%;width:100%\">";
        String footer = "</div>\n" +
                "</div>";
        content.loadDataWithBaseURL(null,header+mHomeAnJianBean.getContent()+footer,"text/html","utf-8",null);

        WebSettings webSettings= content.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);//支持js

        content.setWebChromeClient(new WebChromeClient(){

        });


        content.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
            }

        });
    }

    private void imgReset() {
        content.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()");
    }

    public static class Java2Js{

        public Java2Js(){
        }


        @JavascriptInterface
        public void doEditorContent(){
        }

    }


    private void initWebJs(){
        content.getSettings().setJavaScriptEnabled(true);
        content.loadUrl("file:///android_asset/news.html");
        content.addJavascriptInterface(new Java2Js(), "Java2Js");
        content.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectJs();
            }
        });

        content.setWebChromeClient(new WebChromeClient(){
            //加载进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    private void injectJs(){
        content.loadUrl("javascript:" +
                "function  changeEditor(){" +
//                        "window.alert('"+mHomeAnJianBean.getContent()+"');"+
                "document.getElementById('editor').innerHTML = '"+mHomeAnJianBean.getContent()+"';" +
                "}");
        content.postDelayed(new Runnable() {
            @Override
            public void run() {
                content.loadUrl("javascript:changeEditor()");
            }
        },10);
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
