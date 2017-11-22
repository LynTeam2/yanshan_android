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
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.UpdateFileUtils;


public class ShopFragment extends FrameFragment {

    public final static String TAG = ShopFragment.class.getSimpleName();


    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.web_view)
    ProgressWebView webView;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_shop_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }

    @Override
    protected void initViews() {
        super.initViews();
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
