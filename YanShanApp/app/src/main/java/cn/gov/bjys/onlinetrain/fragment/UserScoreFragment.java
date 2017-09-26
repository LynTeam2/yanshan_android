package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooClassRecycleAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooMultiItemQuickAdapter;
import cn.gov.bjys.onlinetrain.bean.ClassBean;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class UserScoreFragment extends FrameFragment {

    @Bind(R.id.rv1)
    RecyclerView rv1;
    @Bind(R.id.rv2)
    RecyclerView rv2;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_score_layout, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRv1();
        initRv2();
    }


    private void initRv1() {
        List<ClassBean> names = prepaDatas(50);
        DooClassRecycleAdapter adapter = new DooClassRecycleAdapter(R.layout.item_classitem_layout, names);
        rv1.setAdapter(adapter);
        rv1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        }

    private void initRv2(){
        final List<ClassBean> names = prepaDatas(50);
        DooMultiItemQuickAdapter adapter = new DooMultiItemQuickAdapter(names);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv2.setLayoutManager(manager);
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return names.get(position).getSpanSize();
            }
        });
        rv2.setAdapter(adapter);
    }

    private ArrayList<ClassBean> prepaDatas(int i) {
        ArrayList<ClassBean> names = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            ClassBean b = new ClassBean();
            b.setClassName("name is" + j);
            names.add(b);
        }
        return names;
    }
}
