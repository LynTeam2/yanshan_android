package com.zls.www.mulit_file_download_lib.multi_file_download.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.mulit_file_download_lib.R;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.manager.HttpDownManager;
import com.zls.www.mulit_file_download_lib.multi_file_download.manager.HttpProgressOnNextListener;

/**
 * Created by dodo on 2018/1/23.
 */

public class MultiDownView extends RelativeLayout implements View.OnClickListener{

    public MultiDownView(Context context) {
        super(context);
        initView();
    }

    public MultiDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultiDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MultiDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    private View mRootView;
    private void initView(){
       mRootView =  View.inflate(getContext(), R.layout.multi_download_view,null);
       this.addView(mRootView);
       initViews();
    }
    //绑定UI
    private HttpDownManager manager;
    private LinearLayout mLinear;
    private TextView mStart,mPause,mStopDel,mType;
    private NumberProgressBar2 mNumberProgressBar;
    private void initViews(){
        mLinear = (LinearLayout) mRootView.findViewById(R.id.linear);
        mStart = (TextView) mRootView.findViewById(R.id.start);
        mStart.setOnClickListener(this);
        mPause = (TextView) mRootView.findViewById(R.id.pause);
        mPause.setOnClickListener(this);
        mStopDel = (TextView) mRootView.findViewById(R.id.stop);
        mStopDel.setOnClickListener(this);
        mType = (TextView) mRootView.findViewById(R.id.type);

        mNumberProgressBar  = (NumberProgressBar2) findViewById(R.id.number_progress_bar);

        manager = HttpDownManager.getInstance();
    }


    private DataInfo mData;
    //绑定数据
    public void bindDatas(DataInfo data){
        mData = data;
        data.setListener(httpProgressOnNextListener);
        mNumberProgressBar.setMax( data.getCountLength());
        mNumberProgressBar.setProgress( data.getReadLength());
        /*第一次恢复 */
        switch (data.getState()){
            case START:
                /*起始状态*/
                break;
            case PAUSE:
                mType.setText("暂停中");
                break;
            case DOWN:
                manager.startDown(data);
                break;
            case STOP:
                mType.setText("下载停止");
                break;
            case ERROR:
                mType.setText("下載錯誤");
                break;
            case  FINISH:
                mType.setText("下载完成");
                break;
        }
    }


    /*下载回调*/
    HttpProgressOnNextListener<DataInfo> httpProgressOnNextListener=new HttpProgressOnNextListener<DataInfo>() {
        @Override
        public void onNext(DataInfo baseDownEntity) {
            mType.setText("提示：下载完成");
            Toast.makeText(getContext(),baseDownEntity.getSavePath(),Toast.LENGTH_SHORT).show();
            Log.d("dodoT","onNext");
        }

        @Override
        public void onStart() {
            mType.setText("提示:开始下载");
            Log.d("dodoT","onStart");
        }

        @Override
        public void onComplete() {
            mType.setText("提示：下载结束");
            Log.d("dodoT","onComplete");
            manager.save(mData);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mType.setText("失败:"+e.toString());
            Log.d("dodoT","onError");
        }


        @Override
        public void onPuase() {
            super.onPuase();
            mType.setText("提示:暂停");
            Log.d("dodoT","onPuase");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("dodoT","onStop");
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            mType.setText("提示:下载中");
            mNumberProgressBar.setMax(countLength);
            mNumberProgressBar.setProgress(readLength);
            Log.d("dodoT","updateProgress");
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start) {
            if(mData.getState()!= DataInfo.DownState.FINISH){
                manager.startDown(mData);
            }
        } else if (id == R.id.pause) {
            manager.pause(mData);
        } else if (id == R.id.stop) {
            manager.stopDown(mData);
        } else {
        }
    }
}
