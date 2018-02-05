package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;

import com.ycl.framework.base.BaseWebViewActivity;

/**
 * Created by Administrator on 2018/2/3 0003.
 */
public class YSWebActivity extends BaseWebViewActivity {


    private String mTitleName;
    private String mLink;
    @Override
    public void setWebClient() {
        Intent recIntent = getIntent();
        Bundle mBundle = recIntent.getExtras();
        mTitleName = mBundle.getString("title","链接");
        mLink = mBundle.getString("baseUrl","");

        tvTitle.setTitleText(mTitleName);
        webView.loadUrl(mLink);
    }
}
