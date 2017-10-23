package cn.gov.bjys.onlinetrain.act.pop;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ycl.framework.base.BasePopu;
import com.ycl.framework.base.FrameActivity;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/16 0016.
 */
public class EndExamPop  extends BasePopu{

    public EndExamPop(FrameActivity act) {
        super(act, R.layout.pop_end_exam_layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void init() {
        findViewsId(R.id.cancel,true);
        findViewsId(R.id.sure,true);

    }

    @Override
    public void showLocation(int gravity) {
        if (mActivity.isFinishing())
            return;
        setAnimationStyle(com.ycl.framework.R.style.Popup_Animation_UpDown);
        beforeshow();
        showAtLocation(mActivity.getWindow().getDecorView(), gravity, 0, heightNavigationBar);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f; //屏幕变暗 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
        update();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.cancel:
            dismiss();
            break;
        case R.id.sure:

            break;
        default:
            break;
    }
    }
}
