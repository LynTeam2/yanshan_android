package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ZipUtils;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.view.ProgressWebView;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.business.DownLoadInfoBusiness;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;
import com.zls.www.mulit_file_download_lib.multi_file_download.view.MultiDownView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.UpdateFileUtils;

/**
 * Created by Administrator on 2018/2/2 0002.
 */
public class TempFragment extends FrameFragment {

    public final static String TAG = ShopFragment.class.getSimpleName();


    @Bind(R.id.header)
    TitleHeaderView mHeader;


    MultiDownView mMultiDownView;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_shop_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }

    public DataInfo prepareDatas(){
        DataInfo info = new DataInfo();
        info.setAllUrl("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
        info.setSavePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator+ "dodoMp4.mp4");
        info.setState(DataInfo.DownState.START);
        return info;
    }

    public DataInfo getDataInfo(DataInfo info){
        String allUrl =  info.getAllUrl();
        DownLoadInfoBean bean =  DownLoadInfoBusiness.getInstance(BaseApplication.getAppContext()).queryBykey(allUrl);
        if(bean.getDataInfo() == null ||
                TextUtils.isEmpty(bean.getDataInfo().getAllUrl())){
            return info;
        }
        return bean.getDataInfo();
    }

    @Override
    protected void initViews() {
        super.initViews();

        mMultiDownView.bindDatas(getDataInfo(prepareDatas()));

        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] lists = null;
                try {
                    lists =  getContext().getAssets().list("update");
                }catch (IOException e) {
                    e.printStackTrace();
                }
                if(null == lists){
                    return;
                }
                final String s = lists[0];
                if(s != null && !TextUtils.isEmpty(s)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File sdGen =  Environment.getExternalStorageDirectory();
                            final String outPath = sdGen.getAbsoluteFile() + File.separator + "ADemo";

                            File outFile = new File(outPath);
                            if(!outFile.exists()){
                                outFile.mkdirs();
                            }else{
                                Log.d(TAG, "outFile abs = " +outFile.getAbsolutePath() +"\n"
                                );
                            }
                            final String ret = UpdateFileUtils.getAssetsCacheFile(getContext(),s);
                            Log.d(TAG, "sdGen = " +sdGen.getAbsolutePath() +"\n"+
                                    "outPath = " + outPath +"\n"
                                    + "ret = " + ret
                            );
                            try {
                                ZipUtils.unzipFile(ret,outPath);
                            } catch (IOException e) {
                                Log.d(TAG,"解压出问题了");
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }



    @Override
    protected void initData() {
        super.initData();
    }

}

