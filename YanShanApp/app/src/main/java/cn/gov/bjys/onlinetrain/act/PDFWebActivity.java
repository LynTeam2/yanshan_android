package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dodo on 2018/5/2.
 */

public class PDFWebActivity extends FrameActivity {

    public static String TAG = PDFWebActivity.class.getSimpleName();

    public static String URL_TAG = "url_tag";
    public static String HEAD_TAG = "head_tag";

    @Bind(R.id.header)
    TitleHeaderView header;

    @Bind(R.id.pdfView)
    PDFView pdfView;

    @Bind(R.id.fail_layout)
    RelativeLayout fail_layout;

    @Bind(R.id.loading_btn)
    TextView loading_btn;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_pdf_layout);
    }

    private String mUrl;
    private String mTitle;
    OkHttpClient mClient;
    Request mRequest;
    @Override
    public void initViews() {
        super.initViews();
        final Intent recIntent = getIntent();
        Bundle bundle = recIntent.getExtras();
        mTitle = bundle.getString(HEAD_TAG);
        mUrl = bundle.getString(URL_TAG);
        header.setTitleText(mTitle);
//        initPDF();

        mClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(mUrl)//请求接口。如果需要传参拼接到接口后面。
                .build();//创建Request 对象
        loadingDatas();
    }

    private void loadingDatas(){
        mClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showSuc(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    showSuc(true);
                    initPDF(response.body().bytes());
                }else{
                    showSuc(false);
                }
            }
        });
    }

    private void showSuc(final boolean isSuc){
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                fail_layout.setVisibility(isSuc?View.GONE:View.VISIBLE);
                pdfView.setVisibility(isSuc?View.VISIBLE:View.GONE);
            }
        });
    }

    @OnClick({R.id.loading_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.loading_btn:
                //重新加载
                loadingDatas();
                break;
            default:
                break;
        }
    }


    private void initPDF(byte[] datas) {
        pdfView.fromBytes(datas)
//        or
//        pdfView.fromFile(File)
//        or
//        pdfView.fromBytes(byte[])
//        or
//        pdfView.fromStream(InputStream) // stream is written to bytearray - native code cannot use Java Streams
//        or
//        pdfView.fromSource(DocumentSource)
//        or
//        pdfView.fromAsset(String)
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
//                .onDraw(onDrawListener)
//                // allows to draw something on all pages, separately for every page. Called only for visible pages
//                .onDrawAll(onDrawListener)
//                .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
//                .onPageChange(onPageChangeListener)
//                .onPageScroll(onPageScrollListener)
//                .onError(onErrorListener)
//                .onPageError(onPageErrorListener)
//                .onRender(onRenderListener) // called after document is rendered for the first time
//                // called on single tap, return true if handled, false to toggle scroll handle visibility
//                .onTap(onTapListener)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
//                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//                .linkHandler(DefaultLinkHandler)
//                .pageFitPolicy(FitPolicy.WIDTH)
//                .pageSnap(true) // snap pages to screen boundaries
//                .pageFling(false) // make a fling change only a single page like ViewPager
                .load();
    }
}
