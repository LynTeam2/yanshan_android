package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.framework.base.FrameFragment;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class OwnFragment extends FrameFragment {



    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_ownpage,container,false);
    }
}
