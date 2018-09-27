package cn.gov.bjys.onlinetrain.act;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Switch;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.fragment.UserFragment.SaveNickFragment;
import cn.gov.bjys.onlinetrain.fragment.UserFragment.SignInFragment;
import cn.gov.bjys.onlinetrain.fragment.UserFragment.UpdatePasswordFragment;


public class CommonActivity extends FrameActivity {
    public final static String TAG = CommonActivity.class.getSimpleName();
    public final static int SAVE_NICK = 1;
    public final static int SIGN_IN = 2;
    public final static int UPDATE_PASSWORD = 3;

    @Bind(R.id.header)
    TitleHeaderView header;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_common_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    FrameFragment[] mFragments = {
            getSaveNickFragment(),
            getSignInFragment(),
            getUpdatePasswordFragment()
    };

    android.support.v4.app.FragmentTransaction mFt;
    @Override
    public void initViews() {
        super.initViews();
        mFt = getSupportFragmentManager().beginTransaction();
        int revInt =  getIntent().getIntExtra(TAG,-1);
        switch (revInt){
            case SAVE_NICK:
                header.setTitleText("昵称设置");
                if(!mFragments[0].isAdded()){
                    mFt.add(R.id.frame_layout,mFragments[0]);
                }
                mFt.show(mFragments[0]).commit();
                break;
            case SIGN_IN:
                header.setTitleText("签到");
                if(!mFragments[1].isAdded()){
                    mFt.add(R.id.frame_layout,mFragments[1]);
                }
                mFt.show(mFragments[1]).commit();
                break;
            case UPDATE_PASSWORD:
                header.setTitleText("修改密码");
                if(!mFragments[2].isAdded()){
                    mFt.add(R.id.frame_layout,mFragments[2]);
                }
                mFt.show(mFragments[2]).commit();
                break;
            default:
                break;
        }
    }

    SaveNickFragment mSaveNickFragment;
    public SaveNickFragment getSaveNickFragment() {
        if (mSaveNickFragment == null) {
            mSaveNickFragment = SaveNickFragment.newInstance();
        }
        return mSaveNickFragment;
    }

    SignInFragment mSignInFragment;
    public SignInFragment getSignInFragment() {
        if (mSignInFragment == null) {
            mSignInFragment = SignInFragment.newInstance();
        }
        return mSignInFragment;
    }

    UpdatePasswordFragment mUpdatePasswordFragment;

    public UpdatePasswordFragment getUpdatePasswordFragment(){
        if(mUpdatePasswordFragment == null){
            mUpdatePasswordFragment = UpdatePasswordFragment.newInstance();
        }
        return mUpdatePasswordFragment;
    }
}
