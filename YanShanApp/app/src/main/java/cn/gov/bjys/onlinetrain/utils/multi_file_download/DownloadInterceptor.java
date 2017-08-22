package cn.gov.bjys.onlinetrain.utils.multi_file_download;

import java.io.IOException;

import cn.gov.bjys.onlinetrain.utils.multi_file_download.api.DownloadProgressListener;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by dodozhou on 2017/8/22.
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}