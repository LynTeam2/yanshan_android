package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ycl.framework.base.FrameFragment;
import com.zls.www.check_version_lib.UpdateChecker;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class HomeFragment extends FrameFragment {

    @Bind(R.id.check_version_btn)
    Button mCheckVersionBtn;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @OnClick({R.id.check_version_btn})
     public void onTabClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.check_version_btn:
                UpdateChecker.checkForDialog(getContext());
                break;
        }
    }
}
