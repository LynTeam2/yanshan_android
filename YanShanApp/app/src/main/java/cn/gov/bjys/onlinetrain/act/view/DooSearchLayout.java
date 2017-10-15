package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooSearchLayout extends DooRootLayout {


    public DooSearchLayout(Context context) {
        super(context);
    }

    public DooSearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DooSearchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    EditText mEditText;
    RelativeLayout mSearchBtn;

    @Override
    public void initViews() {
            mEditText = (EditText) findViewById(R.id.edit_text);
            mSearchBtn = (RelativeLayout) findViewById(R.id.search_btn_layout);
    }

    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_search_layout,null);
    }

    public View getView(int id){
        return findViewById(id);
    }

}
