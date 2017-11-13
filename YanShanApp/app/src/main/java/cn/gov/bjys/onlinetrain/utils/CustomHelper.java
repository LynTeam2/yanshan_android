package cn.gov.bjys.onlinetrain.utils;

import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

import cn.gov.bjys.onlinetrain.R;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.cboy.me
 * GitHub:https://github.com/crazycodeboy
 * Eamil:crazycodeboy@gmail.com
 * 本地文件对其进行修改设定一个默认config配置
 */
public class CustomHelper {

    final static String SAVE_NATIVE_PATH = "/YanShanApp/"+System.currentTimeMillis()+".jpg";

    final static int MAX_PIC_SIZE = 100*1024;//byte 100KB 图片压缩最大内存
    final static int CHOOSE_MAX_SIZE = 1;//选取的照片的数量
    final static boolean IS_COMPRESS = true;//是否压缩  true压缩 false 不压缩
    final static int COMPRESS_W = 300;//px 裁剪的宽
    final static int COMPRESS_H = 300;//px 裁剪的高
    final static boolean SHOW_PROGRESS = true;// 是否显示压缩进度条
    final static boolean SAVE_SIM_PIC = true;// 拍照后是否保存原图
    final static boolean isNativeCompressTool = false;// 压缩工具是本地的还是Luban

    //裁剪模块
    final static boolean NEED_CROP = true;//需要剪裁  反之false
    final static int CROP_W = 300;
    final static int CROP_H = 300;
    final static boolean IS_ASPECT = true;// true:"宽/高" false:"宽x高"


    //相册部分
    final static boolean isNativePickTool = true;// 是否使用TakePhoto自带相册
    final static boolean isCorrect = false;// 纠正拍照的照片旋转角度
    private View rootView;
    public static CustomHelper of(View rootView){
        return new CustomHelper(rootView);
    }
    private CustomHelper(View rootView) {
        this.rootView = rootView;
        init();
    }
    private void init(){
    }

    public void onClick(View view,TakePhoto takePhoto) {
        File file=new File(Environment.getExternalStorageDirectory(), SAVE_NATIVE_PATH);
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto,IS_COMPRESS,MAX_PIC_SIZE,COMPRESS_W,COMPRESS_H,SHOW_PROGRESS,SAVE_SIM_PIC,isNativeCompressTool);
        configTakePhotoOption(takePhoto,isNativePickTool,isCorrect);
        switch (view.getId()){
            case R.id.btnPickBySelect:
                //选择照片
                int limit= Integer.parseInt(etLimit.getText().toString());
                if(limit>1){
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){//是否裁切
                        takePhoto.onPickMultipleWithCrop(limit,getCropOptions());
                    }else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                if(rgFrom.getCheckedRadioButtonId()==R.id.rbFile){ //从文件or 相册
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                        takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
                    }else {
                        takePhoto.onPickFromDocuments();
                    }
                    return;
                }else {
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                        takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());
                    }else {
                        takePhoto.onPickFromGallery();
                    }
                }
                break;
            case R.id.btnPickByTake:
                //拍照
                if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                }else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param takePhoto
     * @param isNativePickTool 是否使用TakePhoto自带相册
     * @param isCorrect 纠正拍照的照片旋转角度
     */
    private void configTakePhotoOption(TakePhoto takePhoto,boolean isNativePickTool,boolean isCorrect){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
            builder.setWithOwnGallery(isNativePickTool);//是否使用TakePhoto自带相册
            builder.setCorrectImage(isCorrect);//纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(builder.create());

    }

    /**
     *
     * @param takePhoto
     * @param isCompress 是否压缩
     * @param picSize 图片最大内存
     * @param cropW 压缩的宽
     * @param cropH 压缩的高
     * @param showProgress 是否显示压缩进度条
     * @param saveSimPic 拍照后是否保存原图
     * @param isNativeCompressTool 压缩工具是本地的还是Luban
     */
    private void configCompress(TakePhoto takePhoto,boolean isCompress, int picSize,int cropW, int cropH ,
                                boolean showProgress,boolean saveSimPic,boolean isNativeCompressTool){
        if(!isCompress){//是否压缩
            takePhoto.onEnableCompress(null,false);
            return ;
        }
        int maxSize= picSize;//图片最大内存
        int width= cropW;//压缩的宽
        int height= cropH;//压缩的高
        boolean showProgressBar= showProgress;//是否显示压缩进度条
        boolean enableRawFile = saveSimPic;//拍照后是否保存原图
        CompressConfig config;
        if(isNativeCompressTool){//压缩工具是本地的还是Luban
            config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        }else {
            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config= CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config,showProgressBar);


    }

    //是否剪裁 设定宽高值
    /**
     *
     * @param needCrop  true需要剪裁  反之false
     * @param w
     * @param h
     * @param isAspect true:"宽/高" false:"宽x高"
     * @return
     */
    private CropOptions getCropOptions(boolean needCrop,int w,int h,boolean isAspect){
        int height= h;
        int width= w;
        boolean withWonCrop = needCrop;

        CropOptions.Builder builder=new CropOptions.Builder();

        if(isAspect){
            builder.setAspectX(width).setAspectY(height);
        }else {
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
