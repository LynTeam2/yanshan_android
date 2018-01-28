package cn.gov.bjys.onlinetrain.act.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.ycl.framework.base.BaseDialog;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class EndPracticeDialog extends Dialog implements View.OnClickListener {

    protected PracticeActivity mAct;         // 上下文
    public EndPracticeDialog(PracticeActivity activity) {
        super(activity, com.ycl.framework.R.style.Theme_Light_FullScreenDialogAct);
        mAct = activity;
        setContentView(R.layout.layout_practice_dialog);
        initViews();
        setCanceledOnTouchOutside(false);

    }

    private TextView mSure,mContent;
    private void initViews(){
        mSure = (TextView) findViewById(R.id.sure);
        mContent = (TextView) findViewById(R.id.content);
        mSure.setOnClickListener(this);
    }

    public void bindDatas(int right,int error,int all){
        if(error > 0) {
            SpannableStringBuilder ssb = new SpannableStringUtils.Builder()
                    .append("对").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("" + right).setForegroundColor(mAct.getResources().getColor(R.color.normal_red))
                    .append("题，").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("错").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("" + error).setForegroundColor(mAct.getResources().getColor(R.color.normal_red))
                    .append("题，").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("共").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("" + all).setForegroundColor(mAct.getResources().getColor(R.color.normal_red))
                    .append("题").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .create();
            mContent.setText(ssb);
        }else{
            SpannableStringBuilder ssb = new SpannableStringUtils.Builder()
                    .append("恭喜你全对").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .create();
            mContent.setText(ssb);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                dismiss();
                mAct.finishPractice();
                break;
        }
    }
}
