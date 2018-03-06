package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.CustomHelper;

/**
 * Created by dodozhou on 2017/11/13.
 */
public class UserAvatarChooseActivity extends FrameActivity implements TakePhoto.TakeResultListener,InvokeListener {
    public static final String TAG = UserAvatarChooseActivity.class.getName();

    public final static int AVATAR_SAVE_OK = 98;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CustomHelper customHelper;

    @Bind(R.id.my_avatar)
    ImageView my_avatar;

    @Bind(R.id.shoot_btn)
    TextView shoot_btn;//拍照

    @Bind(R.id.photo_btn)
    TextView photo_btn;//相册选择

    @Bind(R.id.photo_cancel)
    TextView photo_cancel;//取消或者完成

    @OnClick({R.id.photo_cancel,R.id.shoot_btn,R.id.photo_btn})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.photo_cancel:
                backAboveAct();
                break;
            case R.id.shoot_btn:
                customHelper.onClick(shoot_btn,getTakePhoto());
                break;
            case R.id.photo_btn:
                customHelper.onClick(photo_btn,getTakePhoto());
                break;
        }
    }

    public void backAboveAct(){
        if(!TextUtils.isEmpty(avatarPath)) {
            Intent intent = new Intent();
            Bundle mBundle = new Bundle();
            mBundle.putString(TAG, avatarPath);
            intent.putExtras(mBundle);
            setResult(AVATAR_SAVE_OK, intent);
        }
        finish();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backAboveAct();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    @Override
    protected void setRootView() {
        View contentView= LayoutInflater.from(this).inflate(R.layout.activity_avatar_choose_layout,null);
        setContentView(contentView);
        customHelper=CustomHelper.of(contentView);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();

    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    private String avatarPath;
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
        avatarPath = result.getImage().getCompressPath();
        final Bitmap bitmap = getDiskBitmap(result.getImage().getCompressPath());
        if(bitmap != null) {
            my_avatar.post(new Runnable() {
                @Override
                public void run() {
                    photo_cancel.setText("完成");
                    my_avatar.setImageBitmap(bitmap);
                }
            });
        }

        pushAvatar();
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    //上传头像
    private void pushAvatar(){

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }


    public static Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return bitmap;
    }
}
