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


public class CommonActivity extends FrameActivity {
    public final static String TAG = CommonActivity.class.getSimpleName();
    public final static int SAVE_NICK = 1;
    public final static int SIGN_IN = 2;

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
            getSignInFragment()
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
}
