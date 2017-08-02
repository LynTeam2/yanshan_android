package com.ycl.framework.albums;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ycl.framework.R;
import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameActivityStack;
import com.ycl.framework.utils.io.FileUtils;
import com.ycl.framework.utils.util.PhotoUtils;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;

import java.io.File;

import org.greenrobot.eventbus.EventBus;

public class CameraTempActivity extends FrameActivity {

    private String mPhotoPath;

    private static final int REQUEST_TAKE_CAMERA_PERMISSION = 10;

    private int topicId;
    private String tagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YisLoger.state(getClass().getName(), "---------  onCreate " + (savedInstanceState == null));
        if (savedInstanceState != null) {
            finish();
        } else {
            topicId = getIntent().getIntExtra("topicId", 0);
            tagName = getIntent().getStringExtra("tagName");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //动态申请牌照申请权限，CAMERA
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_TAKE_CAMERA_PERMISSION);
                    return;
                }
                PhotoUtils.takePicture(this, PhotoUtils.CAMERA_FOLDER, PhotoUtils.INTENT_REQUEST_CODE_CAMERA);
            } else
                PhotoUtils.takePicture(this, PhotoUtils.CAMERA_FOLDER, PhotoUtils.INTENT_REQUEST_CODE_CAMERA);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        YisLoger.state(getClass().getName(), "---------  onSaveInstanceState ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);
        YisLoger.state(getClass().getName(), "---------  onActivityResult ");
        if (resultCode == RESULT_OK) {
            mPhotoPath = PhotoUtils.mPhotoPath;
            YisLoger.debugUrl("----------------    " + mPhotoPath);
            File f = FileUtils.getOwnCacheDirectory(getApplicationContext(), mPhotoPath);
            if (f.exists()) {
                if (AlbumActivity.MaxSelectNum != 1) {
                    MyAdapter.mSelectedImage.add(mPhotoPath);
                } else
                    MyAdapter.mSelectedImage.clear();
                AlbumEntityEBus cameraBus = new AlbumEntityEBus<>(mPhotoPath);
                cameraBus.setTypeEvent(2);  //表示 拍照
                cameraBus.setTopicId(topicId);
                cameraBus.setTagName(tagName);
                EventBus.getDefault().post(cameraBus);
                //刷新相册
                MediaScannerConnection.scanFile(this, new String[]{mPhotoPath}, null, null);
                try {
                    FrameActivityStack.create().finishActivity(AlbumActivity.class);
                } catch (Exception e) {
                    ToastUtil.showToast(e.toString());
                }
            } else {
                ToastUtil.showToast("请重新选择照片！");
            }
        }
        finish();
    }


    @Override
    public void setRootView() {

    }


    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TAKE_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.takePicture(this, PhotoUtils.CAMERA_FOLDER, PhotoUtils.INTENT_REQUEST_CODE_CAMERA);
                } else {
                    ToastUtil.showToast(getString(R.string.need_permission));
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
