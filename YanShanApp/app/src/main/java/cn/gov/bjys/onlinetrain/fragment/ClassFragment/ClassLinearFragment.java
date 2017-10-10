package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.framework.base.FrameFragment;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
public class ClassLinearFragment extends FrameFragment {

    public static ClassLinearFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ClassLinearFragment fragment = new ClassLinearFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_linear_layout, container, false);
    }
    
    
    
}
