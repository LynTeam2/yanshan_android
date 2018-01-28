package cn.gov.bjys.onlinetrain.fragment.PracticeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.framework.base.FrameFragment;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public abstract  class PracticeBaseFragment extends FrameFragment {
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return null;
    }


    public abstract void bindData();
}
