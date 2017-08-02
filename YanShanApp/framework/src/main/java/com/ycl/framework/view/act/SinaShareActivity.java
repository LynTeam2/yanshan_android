package com.ycl.framework.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.HelperUtil;

/**
 * Sina分享 by joeYu on 16/6/20.
 */
public class SinaShareActivity extends FrameActivity implements IWeiboHandler.Response {
    private IWeiboShareAPI mWeiboShareAPI;

    @Override
    public void setRootView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getApplicationContext(), HelperUtil.SINA_APP_KEY);
        mWeiboShareAPI.registerApp();

        mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * sina分享回调，必须是接受回调的Activity
     *
     * @param baseResp
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    showToast("分享成功");
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    showToast("分享取消");
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    showToast("分享失败");
                    break;
            }
        }
        finish();
    }
}
