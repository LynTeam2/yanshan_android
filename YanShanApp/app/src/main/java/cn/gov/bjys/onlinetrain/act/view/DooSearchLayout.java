package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooSearchLayout extends DooRootLayout {

    public interface CallBack {
         void textChange(String s);
         void cancel();
         void editClick();
    }

    private CallBack listener;

    public void register(CallBack l){
        listener = l;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        listener = null;
    }

    public DooSearchLayout(Context context) {
//        super(context);
    this(context,null);
    }

    public DooSearchLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
    this(context,attrs,0);
    }

    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs, defStyleAttr);

    }

/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs, defStyleAttr);
    }*/

    EditText mEditText;
    TextView mText;
    RelativeLayout mSearchBtn;
    RelativeLayout mCancelLayout;
    TextView mCancelBtn;

    @Override
    public void initViews() {
            mEditText = (EditText) findViewById(R.id.edit_text);
            mText = (TextView) findViewById(R.id.text);
            mSearchBtn = (RelativeLayout) findViewById(R.id.search_btn_layout);
            mCancelLayout = (RelativeLayout) findViewById(R.id.cancel_layout);
            mCancelBtn = (TextView) findViewById(R.id.cancel_btn);
            mCancelLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditText.setText("");
                }
            });
            mCancelBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.cancel();
                    }
                }
            });

            mEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.editClick();
                    }
                }
            });

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                   String content =  s.toString();
                   if(TextUtils.isEmpty(content)){
                       //null or ""
                       mCancelLayout.setVisibility(View.GONE);
                   }else {
                       mCancelLayout.setVisibility(View.VISIBLE);
                   }
                   if(listener != null){
                       listener.textChange(content);
                   }
                }
            });
    }


    /**
     * 获取自定义的属性值
     *
     * @param attrs
     */
    private void getAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DooSearchLayout,defStyle,0);
        isSearch = a.getBoolean(R.styleable.DooSearchLayout_need_search,false);
        needCancel = a.getBoolean(R.styleable.DooSearchLayout_need_cancel, false);
        mEditText.setVisibility(isSearch ? VISIBLE : GONE);
        mText.setVisibility(isSearch ? GONE : VISIBLE);
        mCancelBtn.setVisibility(needCancel ? VISIBLE : GONE);
        a.recycle();
    }

    boolean isSearch = false;
    boolean needCancel = false;
    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_search_layout,null);
    }

    public View getView(int id){
        return findViewById(id);
    }

}
