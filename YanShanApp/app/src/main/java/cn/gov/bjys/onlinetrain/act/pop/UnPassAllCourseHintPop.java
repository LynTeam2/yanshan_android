package cn.gov.bjys.onlinetrain.act.pop;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycl.framework.base.BasePopu;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundBackgroundColorSpan;

/**
 * Created by dodo on 2018/5/22.
 */

public class UnPassAllCourseHintPop  extends BasePopu{

    public UnPassAllCourseHintPop(FrameActivity act,View v) {
        super(act,v, AutoUtils.getPercentWidthSize(720), LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    private TextView mContent;
    @Override
    protected void init() {
            findViewsId(R.id.sure,true);
        mContent = findViewsId(R.id.content, false);
    }

    public void bindDatas(List<String> hints){
        SpannableStringUtils.Builder builder = new SpannableStringUtils.Builder()
                .append("你还有")
                .append("\n");
        for(String s: hints){
            builder.append(s);
            builder.append("\n");
        }
        builder.append("课时未完成");
        SpannableStringBuilder ssb = builder.create();
        mContent.setText(ssb);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.sure:
                    dismiss();
                    break;
            }
    }


    @Override
    public void update() {
        if(Build.VERSION.SDK_INT == 24){

        }else {
            super.update();
        }
    }
}
