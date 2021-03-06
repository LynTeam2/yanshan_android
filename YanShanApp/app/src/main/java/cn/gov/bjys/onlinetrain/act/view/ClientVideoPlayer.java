package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.LinkedHashMap;

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


    public void setUp(String url, int screen, Object... objects) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("高清", url);
        map.put("超高清", url);
        setUp(map, 0, screen, objects);
    }

    private int currentUrlMapIndexDefault;
    private LinkedHashMap urlMapDefault;
    public void setUp(LinkedHashMap urlMap, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(urlMap, defaultUrlMapIndex, screen, objects);
        this.currentUrlMapIndexDefault = defaultUrlMapIndex;
        this.urlMapDefault = urlMap;
        if (objects.length == 0) return;
        titleTextView.setText(objects[0].toString());
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            fullscreenButton.setImageResource(cn.jzvd.R.drawable.jz_shrink);
            backButton.setVisibility(View.VISIBLE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.VISIBLE);
            if (urlMap.size() == 1) {
                clarity.setVisibility(GONE);
            } else {
                clarity.setText(JZUtils.getKeyFromLinkedMap(urlMap, currentUrlMapIndexDefault));
                clarity.setVisibility(View.VISIBLE);
            }
            changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_fullscreen));
        } else if (currentScreen == SCREEN_LAYOUT_NORMAL
                || currentScreen == SCREEN_LAYOUT_LIST) {
            fullscreenButton.setImageResource(cn.jzvd.R.drawable.jz_enlarge);
            backButton.setVisibility(View.GONE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_normal));
            batteryTimeLayout.setVisibility(View.GONE);
//            clarity.setVisibility(View.GONE);
            if (urlMap.size() == 1) {
                clarity.setVisibility(GONE);
            } else {
                clarity.setText(JZUtils.getKeyFromLinkedMap(urlMap, currentUrlMapIndexDefault));
                clarity.setVisibility(View.VISIBLE);
            }
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            tinyBackImageView.setVisibility(View.VISIBLE);
            setAllControlsVisible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.GONE);
            clarity.setVisibility(View.GONE);
        }
        setSystemTimeAndBattery();

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
                    case AudioManager.RINGER_MODE_VIBRATE:
                        //振动模式
                    case AudioManager.RINGER_MODE_SILENT:
                        //静音模式
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol, 0);
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

            case R.id.clarity:
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(cn.jzvd.R.layout.jz_layout_clarity, null);

                OnClickListener mQualityListener = new OnClickListener() {
                    public void onClick(View v) {
                        int index = (int) v.getTag();
                        onStatePreparingChangingUrl(index, getCurrentPositionWhenPlaying());
                        clarity.setText(JZUtils.getKeyFromLinkedMap(urlMapDefault, currentUrlMapIndexDefault));
                        for (int j = 0; j < layout.getChildCount(); j++) {//设置点击之后的颜色
                            if (j == currentUrlMapIndexDefault) {
                                ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#fff85959"));
                            } else {
                                ((TextView) layout.getChildAt(j)).setTextColor(Color.parseColor("#ffffff"));
                            }
                        }
                        if (clarityPopWindow != null) {
                            clarityPopWindow.dismiss();
                        }
                    }
                };

                for (int j = 0; j < urlMapDefault.size(); j++) {
                    String key = JZUtils.getKeyFromLinkedMap(urlMapDefault, j);
                    TextView clarityItem = (TextView) View.inflate(getContext(), cn.jzvd.R.layout.jz_layout_clarity_item, null);
                    clarityItem.setText(key);
                    clarityItem.setTag(j);
                    layout.addView(clarityItem, j);
                    clarityItem.setOnClickListener(mQualityListener);
                    if (j == currentUrlMapIndexDefault) {
                        clarityItem.setTextColor(Color.parseColor("#fff85959"));
                    }
                }

                clarityPopWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
                clarityPopWindow.setContentView(layout);
                clarityPopWindow.showAsDropDown(clarity);
                layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                if(currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                    clarityPopWindow.update(clarity, -40, 46, Math.round(layout.getMeasuredWidth()), layout.getMeasuredHeight());
                }else{
                    clarityPopWindow.update(clarity, -40, -350, Math.round(layout.getMeasuredWidth()), layout.getMeasuredHeight());
                }
                    break;
            default:
                super.onClick(v);
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
            mImgStart.setImageResource(cn.jzvd.R.drawable.jz_click_error_selector);
            retryTextView.setVisibility(INVISIBLE);
        } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_replay_selector);
            mImgStart.setImageResource(cn.jzvd.R.drawable.jz_click_replay_selector);
            retryTextView.setVisibility(VISIBLE);
        } else {
            startButton.setImageResource(cn.jzvd.R.drawable.jz_click_play_selector);
            mImgStart.setImageResource(cn.jzvd.R.drawable.jz_click_play_selector);
            retryTextView.setVisibility(INVISIBLE);
        }
    }


    @Override
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
        //设置本地seekBar for voice
        mVoiceSeekBar.setProgress(volumePercent);
    }
}
