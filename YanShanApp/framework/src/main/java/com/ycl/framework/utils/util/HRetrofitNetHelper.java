package com.ycl.framework.utils.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.webkit.WebSettings;

import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.base.RetrofitCallBack;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by joeYu on 16/12/5.
 */

public class HRetrofitNetHelper implements HttpLoggingInterceptor.Logger, Interceptor {
    //HRetrofitNetHelper 实现单例
    public static HRetrofitNetHelper mInstance;
    //缓存对象
    private final Cache cache;
    public Retrofit mRetrofit;
    public OkHttpClient mOkHttpClient;
    //请求日志拦截器
    public HttpLoggingInterceptor mHttpLogInterceptor;
    //基本参数拦截器
//    private BasicParamsInterceptor mBaseParamsInterceptor;
    //缓存和特殊Url拦截处理拦截器
    private Interceptor mUrlInterceptor;
    //    //自定义tag 拦截器
//    private Interceptor mTagInterceptor;
    private Context mContext;
    //接口baseurl
    public static final String BASE_URL = "http://xlc.gosotech.com/";
    //测试
    //public static final String BASE_URL = "http://192.168.50.58:8088/";


    public Map<String, Retrofit> mMapRetrofits = new HashMap<String, Retrofit>();

    private Action1<String> onNextAction;

    private HRetrofitNetHelper(Context context) {
        this.mContext = context;
        //提供Action，供特殊Url拦截然后Toast
        createSubscriberByAction();
        mHttpLogInterceptor = new HttpLoggingInterceptor(this);
        //打印http的body体
        mHttpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //基本参数
//        Map<String, String> tempParams = getBaseParams(context);
//        mBaseParamsInterceptor = new BasicParamsInterceptor.Builder()
//                .addParamsMap(tempParams)
//                .build();
        mUrlInterceptor = this;
        //创建缓存路径
        File cacheFile = new File(context.getCacheDir(), "HttpCache");
        YisLoger.logTag("zgx", "cacheFile=====" + cacheFile.getAbsolutePath());
        cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(17, TimeUnit.SECONDS)
                .writeTimeout(21, TimeUnit.SECONDS)
                .readTimeout(21, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(mHttpLogInterceptor)//2
//                .addInterceptor(mBaseParamsInterceptor)
                .addInterceptor(mUrlInterceptor)  //1
                .addInterceptor(new UserAgentInterceptor()) //添加 userAgent拦截器 3
                .addInterceptor(new ReadCookiesInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                .cache(cache)
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        mMapRetrofits.put(BASE_URL, mRetrofit);
    }

    public synchronized Retrofit getNewRetrofit(String url) {
        Retrofit retrofit = mMapRetrofits.get(url);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            mMapRetrofits.put(url, retrofit);
        }
        return retrofit;
    }

    public <T> T getSpeUrlService(String BASE_URL, Class<T> service) {
        return getNewRetrofit(BASE_URL).create(service);
    }

    public static HRetrofitNetHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HRetrofitNetHelper.class) {
                if (mInstance == null) {
                    mInstance = new HRetrofitNetHelper(context);
                }
            }
        }
        return mInstance;
    }

    //更新url
    public void updateBaseUrl(String url) {
        mRetrofit.newBuilder().baseUrl("http://192.168.13.46:8280/").build();
    }


    public static final MediaType JSONs
            = MediaType.parse("application/json; charset=utf-8");

    public String post(String url, Map<String, Object> params, String tags) throws IOException {
        RequestBody body = createReqJsonBody(params);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .tag(tags)
                .build();
        okhttp3.Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            return "";
        }
    }


    //获取相应的APIService对象
    public <T> T getAPIService(Class<T> service) {
        return mRetrofit.create(service);
    }


    @Override
    public void log(String message) {
        YisLoger.logTag("zgx", "OkHttp: " + message);
    }

    //提供一些常用的基本参数
    public Map<String, String> getBaseParams(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("version", "1.0");
        return params;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //缓存 无网络时  获取缓存 可以注释
        if (NetUtil.checkNetwork(mContext) == NetUtil.NO_NETWORK) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            YisLoger.logTag("zgx", "no network");
        }
//        okhttp3.Response response = chain.proceed(request);
//        request.newBuilder();
//        request.newBuilder()
//                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
//                .header("Content-Length", String.valueOf(request.body().contentLength()))
//                .post(request.body()).build();
//        request.newBuilder().tag("2222").build();
        return chain.proceed(request);
//        String requestUrl = response.request().url().uri().getPath();
//        if (!TextUtils.isEmpty(requestUrl)) {
//            if (requestUrl.contains("LoginDataServlet")) {
//                if (Looper.myLooper() == null) {
//                    Looper.prepare();
//                }
//                createObservable("现在请求的是登录接口");
//            }
//        }
        //缓存响应
//        if (NetUtil.checkNetwork(mContext) != NetUtil.NO_NETWORK) {
//            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//            String cacheControl = request.cacheControl().toString();
//            YisLoger.logTag("zgx", "cacheControl=====" + cacheControl);
//            return response.newBuilder()
//                    .header("Cache-Control", cacheControl)
//                    .removeHeader("Pragma")
//                    .build();
//        } else {
//            return response.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=120")
//                    .removeHeader("Pragma")
//                    .build();
//        }
    }

    private void createSubscriberByAction() {
        onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                YisLoger.logTag("zgx", "s==========" + s);
                ToastUtil.showToast(s);
            }
        };
    }

    //创建事件源
    private void createObservable(String msg) {
        Observable.just(msg).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction);
    }

    public Cache getCache() {
        return cache;
    }

    public void clearCache() throws IOException {
        cache.delete();
    }


    //cookies 持续化处理(cookies 管理)
    public void cookies(OkHttpClient.Builder mBuilder) {
//        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ctx));
//        mOkHttpClient.cookieJar(cookieJar);
        mBuilder.cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
    }

    public static final String boundary = "--------ABCFEFGHIGKLM";

    @Deprecated
    public static RequestBody createReqBody(Map<String, Object> _httpParams) {
        String jsonData = FastJSONParser.convertObjToJson(_httpParams);
        YisLoger.logTag("packet", jsonData);
        RequestBody body = new MultipartBody.Builder(boundary)
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition",
                                "form-data; name= data_type"),
                        RequestBody.create(null, "json"))
                .addPart(
                        Headers.of("Content-Disposition",
                                "form-data; name= data"),
                        RequestBody.create(null, jsonData)).build();

//        RequestBody  body = RequestBody.create(JSONs, jsonData);
        return body;
    }

    public static RequestBody createReqJsonBody(Map<String, Object> _httpParams) {
        String jsonData = FastJSONParser.convertObjToJson(_httpParams);
        YisLoger.logTag("packet", jsonData);
        RequestBody body = RequestBody.create(JSONs, jsonData);
        return body;
    }

    //异步callback，对一些特殊response逻辑处理
    //  获取动态参数时，
    public <D> void enqueueCall(Call<BaseResp<D>> call, final RetrofitCallBack<D> retrofitCallBack) {
        call.enqueue(new Callback<BaseResp<D>>() {
            @Override
            public void onResponse(Call<BaseResp<D>> call, Response<BaseResp<D>> response) {
                BaseResp<D> resp = response.body();
                if (resp == null) {
                    ToastUtil.showToast("暂时没有最新数据!");
                    return;
                }
                if ("8200".equals(resp.getCode())) {
                    if (retrofitCallBack != null)
                        retrofitCallBack.onSuccess(resp.getResult());
                } else {
                    if (retrofitCallBack != null)
                        retrofitCallBack.onFailure(resp.getMsg());
                }
            }

            @Override
            public void onFailure(Call<BaseResp<D>> call, Throwable t) {
                if (retrofitCallBack != null) {
                    retrofitCallBack.onFailure(t.toString());
                }
            }
        });
    }


    //清除网络请求  Request 设置tag
    public void cancelCallsWithTag(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        for (okhttp3.Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (okhttp3.Call call : mOkHttpClient.dispatcher().runningCalls()) {
            Request request = call.request();
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    //获取userAgent
    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(FrameApplication.getFrameContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    private final class UserAgentInterceptor implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        UserAgentInterceptor() {
            this.userAgentHeaderValue = getUserAgent();
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }


    public static String PREF_COOKIES = "Pref_Cookies";

    public static class ReadCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sp = FrameApplication.getFrameContext().getSharedPreferences(PREF_COOKIES, Context.MODE_PRIVATE);
            HashSet<String> cookies = (HashSet<String>) sp.getStringSet(PREF_COOKIES,new HashSet<String>());
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
            }
            return chain.proceed(builder.build());
        }
    }


    public static class SaveCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            HashSet<String> cookiesHint = new HashSet<>();
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookiesHint.add(header);
                }
            }

           String reqUrl = chain.request().url().toString();
            if(reqUrl.contains("login")) {
                //登陆保存session
                SharedPreferences sp = FrameApplication.getFrameContext().getSharedPreferences(PREF_COOKIES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet(PREF_COOKIES, cookiesHint);
                editor.apply();
            }

            return originalResponse;
        }
    }


}