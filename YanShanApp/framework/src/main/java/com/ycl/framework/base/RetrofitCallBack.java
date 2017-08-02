package com.ycl.framework.base;


/**
 * Retrofit框架//异步特殊处理后回调
 */

public interface RetrofitCallBack<T> {
    void onSuccess(T baseResp);

    void onFailure(String error);
}
