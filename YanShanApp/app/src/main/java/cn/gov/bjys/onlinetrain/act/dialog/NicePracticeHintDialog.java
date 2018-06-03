package cn.gov.bjys.onlinetrain.act.dialog;

import android.app.Dialog;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;


public class NicePracticeHintDialog extends Dialog implements View.OnClickListener {

    protected PracticeActivity mAct;         // 上下文
    public NicePracticeHintDialog(PracticeActivity activity) {
        super(activity, com.ycl.framework.R.style.Theme_Light_FullScreenDialogAct);
        mAct = activity;
        setContentView(R.layout.layout_nice_practice_hint_dialog);
        initViews();
        setCanceledOnTouchOutside(false);

    }

    private TextView mSure,mContent,mCancel;
    private void initViews(){
        mSure = (TextView) findViewById(R.id.sure);
        mContent = (TextView) findViewById(R.id.content);
        mCancel = (TextView) findViewById(R.id.cancel);
        mSure.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void bindDatas(String needShowStr){
            SpannableStringBuilder ssb = new SpannableStringUtils.Builder()
                    .append("您还有第").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append(needShowStr).setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .append("题目未做,确认退出当前课程学习吗?").setForegroundColor(mAct.getResources().getColor(R.color.normal_black))
                    .create();
            mContent.setText(ssb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                dismiss();
                mAct.finish();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
