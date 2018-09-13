package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.UserBean;
import cn.gov.bjys.onlinetrain.bean.event.LoginEvent;
import cn.gov.bjys.onlinetrain.utils.LoginHelper;
import cn.gov.bjys.onlinetrain.utils.MapParamsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends FrameActivity implements View.OnClickListener {
    private String mUserName;
    private String mPassword;

    @Bind(R.id.login_et_userid)
    EditText login_et_userid;

    @Bind(R.id.login_et_password)
    EditText login_et_password;

//    @Bind(R.id.forget_password_btn)
//    TextView forget_password_btn;
//
//    @Bind(R.id.register_btn)
//    TextView register_btn;

    @Bind(R.id.act_login_btn)
    Button act_login_btn;

    @Bind(R.id.login_cancel)
    ImageView login_cancel;

    @Bind(R.id.login_yanjing)
    CheckBox login_yanjing;

    @Bind(R.id.login_header)
    TitleHeaderView login_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login2);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }


    @Override
    public void initData() {
        super.initData();
        initClick();
    }


    @Override
    public void initViews() {
        super.initViews();

    }

    protected void initClick() {
        login_header.setCustomClickListener(R.id.iv_title_header_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login_header.setTitleText("登录");
//        forget_password_btn.setOnClickListener(this);
//        register_btn.setOnClickListener(this);
        act_login_btn.setOnClickListener(this);

        login_et_userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    login_cancel.setVisibility(View.VISIBLE);
                } else {
                    login_cancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Bundle bundle;
        switch (id) {
            case R.id.act_login_btn:
                getLoginInfo();
//                checkLogin();
                break;
            case R.id.register_btn:
                ToastUtil.showToast("register");
                break;
            case R.id.forget_password_btn:
                ToastUtil.showToast("forgetWord");
                break;
        }
    }

    //获取登录信息
    private void getLoginInfo() {

//        toMainAct();

        mUserName = login_et_userid.getText().toString().trim();
        mPassword = login_et_password.getText().toString().trim();
        userLogin();
    }

    private void userLogin() {
        LoginHelper.login(mUserName, mPassword);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuc(LoginEvent event) {
        switch (event.getType()) {
            case 0:
                //留在原地
                break;
            case 1:
                toMainAct();
                break;
        }
    }


    private void toMainAct() {
        startAct(MainActivity.class);
        finish();
    }

    public void checkLogin() {
        mUserName = login_et_userid.getText().toString().trim();
        mPassword = login_et_password.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)) {
            showToast(R.string.string_check_user);
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            showToast(R.string.string_check_password);
            return;
        }
        if (!mUserName.matches("[1][3578]\\d{9}|14[57]\\d{8}")) {
            showToast(R.string.string_check_phone_right);
            return;
        }
        if (mPassword.length() < YSConst.PASSWORD_LENGTH_MIN
                || mPassword.length() > YSConst.PASSWORD_LENGTH_MAX) {
            showToast(R.string.string_check_password_lendth);
            return;
        }
        login();
    }

    private void login() {
        showProDialog("登录中...");
        login_et_userid.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                startAct(MainActivity.class);
            }
        }, 1500);
    }

    private int mLastPwdType;

    @OnClick({R.id.login_cancel, R.id.login_yanjing})
    void tabClick(View view) {
        switch (view.getId()) {
            case R.id.login_cancel:
                login_cancel.setVisibility(View.GONE);
                login_et_userid.setText("");
                break;
            case R.id.login_yanjing:

                if (login_et_password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    login_et_password.setInputType(mLastPwdType);
                    login_yanjing.setChecked(false);
                } else {
                    mLastPwdType = login_et_password.getInputType();
                    login_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    login_yanjing.setChecked(true);
                }
                login_et_password.setSelection(login_et_password.getText().length());
                break;

        }
    }


}
