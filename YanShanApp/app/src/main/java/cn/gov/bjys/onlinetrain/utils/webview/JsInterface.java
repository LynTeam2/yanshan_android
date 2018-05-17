package cn.gov.bjys.onlinetrain.utils.webview;

import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import com.ycl.framework.base.FrameActivity;

/**
 * Created by yuchaoliang on 17/10/12.
 */

public class JsInterface {

    public interface  Js2JavaInterface{
        public void setTitle(String str);
    }

    private Js2JavaInterface js2JavaInterface;
    private String token;
    private String uri;
    private String mUrl;
    private FrameActivity activity;

    public JsInterface(){
    }
    public JsInterface(String token){
        this.token = token;
    }

    public JsInterface(String token, FrameActivity activity) {
        this.token = token;
        this.activity = activity;
    }

    public JsInterface(String token, Js2JavaInterface lis) {
        this.token = token;
        this.js2JavaInterface = lis;
    }

    public void setmUrl(String mUrl){
        this.mUrl = mUrl;
    }

    @JavascriptInterface
    public String getToken() {
        return token;
    }

    @JavascriptInterface
    public String getLoadUrl() {
        return mUrl;
    }


    @JavascriptInterface
    public void setUri(String uri) {
        this.uri = uri;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void setTitle(String title) {
        if(js2JavaInterface != null){
            js2JavaInterface.setTitle(title);
        }
    }



}
