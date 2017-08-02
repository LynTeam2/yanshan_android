package com.ycl.framework.utils.util;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ycl.framework.base.FrameApplication;

/**
 * 分享等helper by joeYu on 16/10/28.
 */

public class HelperUtil {

    public static final String WX_APP_ID = "wxe046e8d39046ab8f";

    //sinaSDK
    public static final String SINA_APP_KEY = "2357776436";
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";


    public static IWXAPI getIWXAPI(Context context) {
        return WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
    }

}
