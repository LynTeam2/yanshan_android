package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.ClientVideoPlayer;
import cn.gov.bjys.onlinetrain.act.view.DooQuestionAnalysisLayout;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.fragment.PracticeFragment.PracticeBaseFragment;
import cn.gov.bjys.onlinetrain.utils.PracticeHelper;
import cn.gov.bjys.onlinetrain.utils.webview.JsInterface;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by dodozhou on 2017/9/27.
 */
public class VideoExaminationFragment extends PracticeBaseFragment {
    public final static String TAG = VideoExaminationFragment.class.getSimpleName();


    public static VideoExaminationFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(TAG, position);
        VideoExaminationFragment fragment = new VideoExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static VideoExaminationFragment newInstance() {

        Bundle args = new Bundle();

        VideoExaminationFragment fragment = new VideoExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Bind(R.id.start_req)
    Button mStartReq;

    @Bind(R.id.analysis_layout)
    DooQuestionAnalysisLayout mDooQuestionAnalysisLayout;


    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_video_examination_layout, container, false);
        return view;
    }

    @Override
    public void bindData() {
      CourseBean bean = PracticeHelper.getInstance().getmCourseBean();
      String content = bean.getContent();

//        mWebView.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
//      ClientVideoPlayer jzVideoPlayerStandard = (ClientVideoPlayer) findViews(viewRoot, R.id.video_player);
//      jzVideoPlayerStandard.setUp(content
//                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频题");
        initJS2Java();
        Log.d("dodoVideoPath", bean.getVideo());
        mWebView.loadUrl("file:///android_asset/video.html"+"?"+"url="+bean.getVideo());
    }

    public void initJS2Java() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        JsInterface jsInterface = new JsInterface();
        CourseBean bean = PracticeHelper.getInstance().getmCourseBean();
        jsInterface.setmUrl(bean.getVideo());
        mWebView.addJavascriptInterface(jsInterface, "Java2JS");
    }

    @OnClick({R.id.start_req})
    public void OnTableClick(View v){
     switch (v.getId()) {
         case R.id.start_req:
             Bundle mBundle = new Bundle();
             mBundle.putInt(PracticeActivity.TAG, PracticeActivity.KESHI);
             startAct(PracticeActivity.class, mBundle);
             getActivity().finish();
             break;
     }
    }

    @Override
    protected void initViews() {
        super.initViews();
//        ClientVideoPlayer jzVideoPlayerStandard = (ClientVideoPlayer) findViews(viewRoot, R.id.video_player);
//        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
//                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频题");
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

//        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }


    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(!visible){
            //不可见
        }
    }

    @Override
    public void onResume() {
        if(mWebView != null){
            mWebView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(mWebView != null) {
            mWebView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(mWebView != null){
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
