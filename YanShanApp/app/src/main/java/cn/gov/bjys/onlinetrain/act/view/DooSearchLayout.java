package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooSearchLayout extends DooRootLayout {

    public DooSearchLayout(Context context) {
//        super(context);
    this(context,null);
    }

    public DooSearchLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
    this(context,attrs,0);
    }

    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
    this(context,attrs,defStyleAttr,0);
    }

    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttr(context, attrs, defStyleAttr);
    }

    EditText mEditText;
    TextView mText;
    RelativeLayout mSearchBtn;

    @Override
    public void initViews() {
            mEditText = (EditText) findViewById(R.id.edit_text);
            mText = (TextView) findViewById(R.id.text);
            mSearchBtn = (RelativeLayout) findViewById(R.id.search_btn_layout);
    }


    /**
     * 获取自定义的属性值
     *
     * @param attrs
     */
    private void getAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DooSearchLayout,defStyle,0);
        isSearch = a.getBoolean(R.styleable.DooSearchLayout_need_search,false);
        mEditText.setVisibility(isSearch ? VISIBLE : GONE);
        mText.setVisibility(isSearch ? GONE : VISIBLE);
        a.recycle();
    }

    boolean isSearch = false;
    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_search_layout,null);
    }

    public View getView(int id){
        return findViewById(id);
    }

}
