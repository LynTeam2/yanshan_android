package cn.gov.bjys.onlinetrain.fragment.UserFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.utils.MapParamsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdatePasswordFragment extends FrameFragment {


    public static UpdatePasswordFragment newInstance() {

        Bundle args = new Bundle();

        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind({R.id.ps1})
    EditText ps1;

    @Bind(R.id.sure_btn)
    TextView sure_btn;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return View.inflate(getContext(), R.layout.fragment_update_password_layout, null);
    }

    @OnClick({R.id.sure_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn:
                if (checkPassword()) {
                    updatePs();
                }
                break;
        }
    }

    private boolean checkPassword() {
        String content = ps1.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast("请输入正确的密码");
            return false;
        }

        if (content.length() < 6) {
            ToastUtil.showToast("密码长度不能低于6位数");
            return false;
        }


        return true;
    }

    private void updatePs() {
        {
            Observable<BaseResponse<String>> obsLogin;
            obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                    getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).updatePassword(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.updatePassword(ps1.getText().toString().trim())));
            obsLogin.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseResponse<String>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseResponse<String> stringBaseResponse) {
                            if ("1".equals(stringBaseResponse.getCode())) {
                                ToastUtil.showToast("更新密码成功");
                                getActivity().finish();
                            }
                        }
                    });
        }
    }

}
