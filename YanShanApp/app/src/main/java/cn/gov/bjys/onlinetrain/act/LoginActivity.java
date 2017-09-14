package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class LoginActivity extends FrameActivity implements View.OnClickListener{
    private String mUserName;
    private String mPassword;

    @Bind(R.id.login_et_userid)
    EditText login_et_userid;

    @Bind(R.id.login_et_password)
    EditText login_et_password;

    @Bind(R.id.forget_password_btn)
    TextView forget_password_btn;

    @Bind(R.id.register_btn)
    TextView register_btn;

    @Bind(R.id.act_login_btn)
    Button act_login_btn;

    @Bind(R.id.login_cancel)
    ImageView login_cancel;

    @Bind(R.id.login_yanjing)
    CheckBox login_yanjing;

    @Bind(R.id.login_header)
    TitleHeaderView login_header;

    @Bind(R.id.qq_login)
    LinearLayout qq_login;

    @Bind(R.id.wx_login)
    LinearLayout wx_login;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login);
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
        forget_password_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
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
                checkLogin();
                break;
            case R.id.register_btn:
                ToastUtil.showToast("register");
                break;
            case R.id.forget_password_btn:
                ToastUtil.showToast("forgetWord");
                break;
        }
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
        },1500);
    }

    private int mLastPwdType;

    @OnClick({R.id.login_cancel, R.id.login_yanjing, R.id.qq_login, R.id.wx_login})
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

            case R.id.qq_login:
                startOtherLogin(QQ_LOGIN);
                break;
            case R.id.wx_login:
                startOtherLogin(WX_LOGIN);
                break;
        }
    }
    /**
     * @param type  1为qq 2为wx
     */
    public final static int QQ_LOGIN = 1;
    public final static int WX_LOGIN = 2;
    private void startOtherLogin(int type) {
        switch (type) {
            case QQ_LOGIN:
                ToastUtil.showToast("腾讯QQ");
                break;
            case WX_LOGIN:
                ToastUtil.showToast("微信");
                startAct(MainActivity.class);
                break;
        }
    }


}
