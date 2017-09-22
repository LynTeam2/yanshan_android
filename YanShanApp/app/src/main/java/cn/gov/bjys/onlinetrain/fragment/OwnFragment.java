package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;

import java.util.ArrayList;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.AnswerLayout;
import cn.gov.bjys.onlinetrain.act.view.DooLinear;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class OwnFragment extends FrameFragment {

    @Bind(R.id.doolinear1)
    DooLinear mDooLinear;
    @Bind(R.id.answer_layout)
    AnswerLayout answerLayout;
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_ownpage, container, false);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mDooLinear.setImgIcon(R.drawable.menu_turnnext);
        mDooLinear.setImgNext(R.mipmap.ic_launcher);
        mDooLinear.setTvContent("点击进入下一页");
        mDooLinear.setCustomClick(R.id.next_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("进入下一页");
            }
        });
        ArrayList<String> list = new ArrayList<>();
        list.add("吃饭");
        list.add("睡觉");
        list.add("打东东");
        list.add("洗澡");
        answerLayout.bindDatas(list);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            //fragment展示
            if (YSUserInfoManager.getsInstance().isLogin()) {
                //登录显示
            } else {

            }
        }
    }
}
