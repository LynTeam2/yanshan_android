package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycl.framework.utils.util.GlideProxy;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/11/14.
 */
public class DooUserSettingLinear extends DooRootLayout {
    public DooUserSettingLinear(Context context) {
        super(context);
    }

    public DooUserSettingLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DooUserSettingLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DooUserSettingLinear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private TextView title;
    private TextView name;
    private ImageView avatar;
    private ImageView nextBtn;
    private LinearLayout nextLayout;
    @Override
    public void initViews() {
            title = (TextView) findViewById(R.id.content);
            name = (TextView) findViewById(R.id.name);
            avatar = (ImageView) findViewById(R.id.avatar);
            nextBtn = (ImageView) findViewById(R.id.next_btn);
            nextLayout = (LinearLayout) findViewById(R.id.next_layout);
    }

    public void setTitle(String  title){
        this.title.setText(title);
    }

    public void setName(String n){
        name.setText(n);
        name.setVisibility(View.VISIBLE);
    }

    public void setAvatar(int res){
        avatar.setImageResource(res);
        avatar.setVisibility(View.VISIBLE);
    }

    public void setAvatar(String url){
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate(avatar,url,R.mipmap.ic_launcher);
        avatar.setVisibility(View.VISIBLE);
    }



    public void setCustomClick(int viewId,OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }
    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_settiing_layout,null);
    }
}
