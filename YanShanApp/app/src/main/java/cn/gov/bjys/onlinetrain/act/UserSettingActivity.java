package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooUserSettingLinear;
import cn.gov.bjys.onlinetrain.fragment.UserFragment.SaveNickFragment;

/**
 * Created by dodozhou on 2017/11/14.
 */
public class UserSettingActivity extends FrameActivity {

    public final static int SET_NICK_BACK = 1;

    @Bind(R.id.header)
    TitleHeaderView header;

    @Bind(R.id.quite_btn)
    Button quite_btn;

    @Bind(R.id.setting_layout)
    LinearLayout setting_layout;

    @Bind(R.id.content_layout)
    RelativeLayout mContentLayout;

    @OnClick({R.id.quite_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.quite_btn:
                //TODO GOTO QUTIE
                break;
        }
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_user_setting);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    android.support.v4.app.FragmentTransaction ft;
    @Override
    public void initViews() {
        super.initViews();
        ft = getSupportFragmentManager().beginTransaction();
        initSettingLayout();
    }


    DooUserSettingLinear avatarLinear, nickLinear, otherLinear;

    public void initSettingLayout() {
        setting_layout.removeAllViews();
        avatarLinear = new DooUserSettingLinear(this);
        avatarLinear.setTitle("头像");
        avatarLinear.setAvatar(R.drawable.user_normal_avatar);
        avatarLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 设置头像
                startAct(UserAvatarChooseActivity.class);
            }
        });
        setting_layout.addView(avatarLinear);

        nickLinear = new DooUserSettingLinear(this);
        nickLinear.setTitle("昵称");
        nickLinear.setName("考试王");
        nickLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 昵称
                Bundle bundle = new Bundle();
                bundle.putInt(CommonActivity.TAG,CommonActivity.SAVE_NICK);
                startActForResultBundle(CommonActivity.class,bundle,SET_NICK_BACK);
            }
        });
        setting_layout.addView(nickLinear);

        otherLinear = new DooUserSettingLinear(this);
        otherLinear.setTitle("其他");
        otherLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 其他
                ToastUtil.showToast("功能暂未开放");
            }
        });
        setting_layout.addView(otherLinear);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SET_NICK_BACK:
                if(resultCode == CommonActivity.SAVE_NICK){
                  Bundle bundle =  data.getExtras();
                   String nick = (String) bundle.get(SaveNickFragment.TAG);
                    nickLinear.setName(nick);
                }

                break;
        }
    }
}
