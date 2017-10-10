package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycl.framework.base.FrameFragment;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooClassRecycleAdapter;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
public class ClassGridFragment extends FrameFragment {

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_linear_layout, container, false);
    }

    private RecyclerView mRecyclerView;

    @Override
    protected void initViews() {
        super.initViews();

    }
}
