package cn.gov.bjys.onlinetrain.fragment.PracticeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.ycl.framework.base.FrameFragment;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.ClientVideoPlayer;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.utils.PracticeHelper;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2017/12/31 0031.
 */

//阅读题
public class ArticlePracticeFragment  extends PracticeBaseFragment {

    public final static String TAG = ArticlePracticeFragment.class.getSimpleName();

    public static ArticlePracticeFragment newInstance() {
        
        Bundle args = new Bundle();
        ArticlePracticeFragment fragment = new ArticlePracticeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_article_practice_layout, container, false);
        return view;
    }

    @Override
    public void bindData() {
        CourseBean bean = PracticeHelper.getInstance().getmCourseBean();
        String content = bean.getContent();
        web_view.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
    }

    @Bind(R.id.start_req)
    Button mStartReq;

    @Bind(R.id.web_view)
    WebView web_view;

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



}
