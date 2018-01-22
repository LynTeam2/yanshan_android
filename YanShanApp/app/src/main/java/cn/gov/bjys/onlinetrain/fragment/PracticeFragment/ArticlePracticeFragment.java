package cn.gov.bjys.onlinetrain.fragment.PracticeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ycl.framework.base.FrameFragment;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;

/**
 * Created by Administrator on 2017/12/31 0031.
 */
public class ArticlePracticeFragment  extends FrameFragment {

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

    @Bind(R.id.start_req)
    Button mStartReq;


    @OnClick({R.id.start_req})
    public void OnTableClick(View v){
        switch (v.getId()) {
            case R.id.start_req:
                    startAct(PracticeActivity.class);
                break;
        }
    }

}
