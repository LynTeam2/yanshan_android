package com.zls.www.mulit_file_download_lib.multi_file_download.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


public interface DownLoadApi {
    @Streaming
    @GET
    Observable <ResponseBody> downLoad(@Header("RANGE") String start, @Url String url);
}
