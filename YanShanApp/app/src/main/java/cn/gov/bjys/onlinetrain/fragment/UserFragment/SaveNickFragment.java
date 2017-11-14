package cn.gov.bjys.onlinetrain.fragment.UserFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.CommonActivity;
import cn.gov.bjys.onlinetrain.act.UserSettingActivity;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by dodozhou on 2017/11/14.
 */
public class SaveNickFragment extends FrameFragment {
    public final static String TAG = SaveNickFragment.class.getSimpleName();

    @Bind(R.id.new_nick)
    EditText new_nick;

    @Bind(R.id.save_btn)
    Button save_btn;

    public static SaveNickFragment newInstance() {
        Bundle args = new Bundle();
        SaveNickFragment fragment = new SaveNickFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return View.inflate(getContext(), R.layout.fragment_changenick_layout,null);
    }

    @OnClick({R.id.save_btn})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.save_btn:
             String nickname = new_nick.getText().toString();
                if(TextUtils.isEmpty(nickname)){
                    ToastUtil.showToast("请输入有效昵称");
                }else{
                    //TODO save nickname
                    Bundle bundle = new Bundle();
                    bundle.putString(TAG, nickname);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                getActivity().setResult(CommonActivity.SAVE_NICK,intent);
                getActivity().finish();
                }
                break;
        }
    }
}
