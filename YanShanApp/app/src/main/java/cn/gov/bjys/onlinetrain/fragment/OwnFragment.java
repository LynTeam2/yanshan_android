package cn.gov.bjys.onlinetrain.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sina.weibo.sdk.api.share.Base;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.CommonActivity;
import cn.gov.bjys.onlinetrain.act.UserAvatarChooseActivity;
import cn.gov.bjys.onlinetrain.act.UserMessageActivity;
import cn.gov.bjys.onlinetrain.act.UserMoreErrorActivity;
import cn.gov.bjys.onlinetrain.act.UserSettingActivity;
import cn.gov.bjys.onlinetrain.act.view.DooLinear;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class OwnFragment extends FrameFragment {

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.user_avatar)
    RoundImageViewByXfermode user_avatar;


    @Bind(R.id.user_name)
    TextView user_name;

    @Bind(R.id.user_wealth)
    TextView user_wealth;


    @Bind(R.id.user_functions_layout)
    LinearLayout user_functions_layout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_ownpage, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(), view.findViewById(R.id.status_bar_layout));
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();

        initUserBaseInfos();
        initUserFunctionsLayout();
    }

    public void initUserBaseInfos(){
        //TODO 设置用户基本信息

    }

    public void initUserFunctionsLayout() {
        user_functions_layout.removeAllViews();
        //个人设置
        DooLinear userSetting = new DooLinear(getActivity());
        userSetting.setImgIcon(R.drawable.user_setting_icon);
        userSetting.setImgNext(R.drawable.next_right_btn);
        userSetting.setTvContent("个人设置");
        userSetting.setCustomClick(R.id.next_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("个人设置");
                startAct(UserSettingActivity.class);
            }
        });
        user_functions_layout.addView(userSetting);
        //签到有礼
        DooLinear userFlag = new DooLinear(getActivity());
        userFlag.setImgIcon(R.drawable.sign_in_icon);
        userFlag.setImgNext(R.drawable.next_right_btn);
        userFlag.setTvContent("签到有礼");
        userFlag.setCustomClick(R.id.next_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("签到有礼");
                Bundle bundle = new Bundle();
                bundle.putInt(CommonActivity.TAG,CommonActivity.SIGN_IN);
                startAct(CommonActivity.class,bundle);
            }
        });
        user_functions_layout.addView(userFlag);
        //我的消息
        DooLinear userMessage = new DooLinear(getActivity());
        userMessage.setImgIcon(R.drawable.user_message_icon);
        userMessage.setImgNext(R.drawable.next_right_btn);
        userMessage.setTvContent("我的消息");
        userMessage.setCustomClick(R.id.next_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("我的消息");
                startAct(UserMessageActivity.class);
            }
        });
        user_functions_layout.addView(userMessage);

        //我的错题
        DooLinear userErr = new DooLinear(getActivity());
        userErr.setImgIcon(R.drawable.user_message_icon);
        userErr.setImgNext(R.drawable.next_right_btn);
        userErr.setTvContent("我的错题");
        userErr.setCustomClick(R.id.next_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("我的错题");
                startAct(UserMoreErrorActivity.class);
            }
        });
        user_functions_layout.addView(userErr);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            //fragment展示
            if (YSUserInfoManager.getsInstance().isLogin()) {
                //登录显示
                setUserBaseInfo();
            } else {

            }
        }
    }

    public void setUserBaseInfo(){
        setUserAvatar();
        setUserNickName();
    }

    public void setUserAvatar(){
        String avatarPath = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH);
        if(!TextUtils.isEmpty(avatarPath)){
            GlideProxy.loadImgForUrlPlaceHolderDontAnimate(user_avatar,avatarPath,R.drawable.user_normal_avatar);
        }
    }

    public void setUserNickName(){
        String nick = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK);
        if(!TextUtils.isEmpty(nick)){
           user_name.setText(nick);
        }
    }
    private final static int REQ_CHANGE_AVATAR = 0X03;
    @OnClick({R.id.user_avatar})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.user_avatar:
                startActivityForResult(new Intent(getActivity(), UserAvatarChooseActivity.class),REQ_CHANGE_AVATAR);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CHANGE_AVATAR){
            if(resultCode == UserAvatarChooseActivity.AVATAR_SAVE_OK){
                Bundle bundle =  data.getExtras();
                String avatarPath = (String) bundle.get(UserAvatarChooseActivity.TAG);
                if(!TextUtils.isEmpty(avatarPath)){
                    SavePreference.save(BaseApplication.getAppContext(),YSConst.UserInfo.USER_AVATAR_PATH,avatarPath);
//                        avatarLinear.setAvatar(Uri.parse(avatarPath));
                    GlideProxy.loadImgForUrlPlaceHolderDontAnimate(user_avatar,avatarPath,R.drawable.user_normal_avatar);
                }
            }
        }
    }
}
