package cn.gov.bjys.onlinetrain.act.pop;

import android.view.View;
import android.view.ViewGroup;

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
        super.showLocation(gravity);
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
