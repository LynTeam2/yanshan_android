package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import cn.gov.bjys.onlinetrain.R;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerStandard;


public class ClientVideoPlayer extends JZVideoPlayerStandard {
    public final static String TAG = ClientVideoPlayer.class.getSimpleName();

    public ClientVideoPlayer(Context context) {
        super(context);
    }

    public ClientVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jc_client;
    }

    SeekBar mVoiceSeekBar;
    RelativeLayout mRlStart;//底下暂停开始的父布局
    ImageView mImgStart;//底下的暂停开始图标
    @Override
    public void init(Context context) {
        //这是播放控件初始化的时候最先调用的
        super.init(context);
        initViews();
        initVoice();
        showInWindowNormal();
    }

    private void initViews(){
        mRlStart = (RelativeLayout) findViewById(R.id.rl_start);
        mRlStart.setOnClickListener(this);
        mImgStart = (ImageView) findViewById(R.id.small_start);
        mVoiceSeekBar = (SeekBar) findViewById(R.id.voice_seek_progress);
        retryTextView.setOnClickListener(this);
    }

    private void initVoice(){
        //最大音量
       final int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mVoiceSeekBar.setProgress((int) (currentVolume/(maxVolume*1.0f)*100));
        mVoiceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentVol = (int) (progress/(100 * 1.0f) * maxVolume);
                int mode = mAudioManager.getRingerMode();switch (mode){
                    case AudioManager.RINGER_MODE_NORMAL:
                        //普通模式
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0); //tempVolume:音量绝对值
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        //振动模式
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        //静音模式
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        //这是控件里所有控件的onClick响应函数，比如监听开始按钮的点击，全屏按钮的点击，空白的点击，retry按钮的点击，等。如果你想拦截这些点击的响应或者继承这些点击的响应，那么复写此函数
        super.onClick(v);
        Log.d(TAG, "onClick()");

        switch (v.getId()){
            case R.id.retry_text:
                //播放完成之后显示如果被点击  就重新播放
                startVideo();
            break;

            case R.id.rl_start:
                //暂停或者开始的逻辑
                startButton.performClick();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //在JCVideoPlayer中此函数主要响应了全屏之后的手势控制音量和进度
        Log.d(TAG, "onTouch");
        return super.onTouch(v, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent");
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public void startVideo() {
        //用户触发的视频开始播放
        super.startVideo();
        Log.d(TAG, "startVideo");
    }

    @Override
    public void onVideoRendingStart() {
        //用户触发的视频开始播放后进入preparing状态，当视频准备完毕之后进入onVideoRendingStart函数，开始播放
        super.onVideoRendingStart();
        Log.d(TAG, "onVideoRendingStart");
    }

    @Override
    public void onStateNormal() {
        //控件进入普通的未播放状态
        super.onStateNormal();
        Log.d(TAG, "onStateNormal");

    }

    @Override
    public void onStatePreparing() {
        //进入preparing状态，正在初始化视频
        super.onStatePreparing();
        Log.d(TAG, "onStatePreparing");
    }

    @Override
    public void onStatePause() {
        //暂停视频，进入暂停状态
        super.onStatePause();
        Log.d(TAG, "onStatePause");
    }

    @Override
    public void onStatePlaybackBufferingStart() {
        //在播放状态下seek进度，进入缓存视频的状态
        super.onStatePlaybackBufferingStart();
        Log.d(TAG, "onStatePlaybackBufferingStart");
    }

    @Override
    public void onStateError() {
        //进入错误状态
        super.onStateError();
        Log.d(TAG, "onStateError");
    }

    @Override
    public void onStateAutoComplete() {
        //进入视频自动播放完成状态
        super.onStateAutoComplete();
        Log.d(TAG, "onStateAutoComplete");
    }

    @Override
    public void onInfo(int what, int extra) {
        //android.media.MediaPlayer回调的info
        super.onInfo(what, extra);
        Log.d(TAG, "onInfo");
    }

    @Override
    public void onError(int what, int extra) {
        //android.media.MediaPlayer回调的error
        super.onError(what, extra);
        Log.d(TAG, "onError");
    }

    @Override
    public void startWindowFullscreen() {
        //进入全屏
        super.startWindowFullscreen();
        Log.d(TAG, "startWindowFullscreen");
        showInWindowFullscreen();
    }


    @Override
    public void playOnThisJzvd(){
        //退出全屏和小窗的方法
        super.playOnThisJzvd();
        showInWindowNormal();
    }

    @Override
    public void startWindowTiny() {
        //进入小屏
        super.startWindowTiny();
        Log.d(TAG, "startWindowTiny");
    }

    /**
     * 正常显示内容 非小屏和全屏情况下
     */
    private void showInWindowNormal(){
        //小屏退出按钮 屏蔽
        findViewById(cn.jzvd.R.id.back_tiny).setVisibility(View.GONE);
        //back 作为横屏退出小屏的按钮 小屏屏蔽
        findViewById(cn.jzvd.R.id.back).setVisibility(GONE);
    }

    /**
     * 全屏显示内容
     */
    private void showInWindowFullscreen(){
        //小屏退出按钮 屏蔽
        findViewById(cn.jzvd.R.id.back_tiny).setVisibility(View.GONE);
        //back 作为横屏退出小屏的按钮
        findViewById(cn.jzvd.R.id.back).setVisibility(VISIBLE);
        JZUtils.getAppCompActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void updateStartImage() {
        if (currentState == CURRENT_STATE_PLAYING) {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_pause_selector);
            mImgStart.setImageResource(cn.jzvd.R.drawable.jz_click_pause_selector);
            retryTextView.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_ERROR) {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_error_selector);
            retryTextView.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_replay_selector);
            retryTextView.setVisibility(VISIBLE);
        } else {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_play_selector);
            retryTextView.setVisibility(INVISIBLE);
        }
    }
}
