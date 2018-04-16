package cn.gov.bjys.onlinetrain.act;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ycl.framework.base.FrameActivity;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class SearchActivity extends FrameActivity {

    @Bind(R.id.search_layout)
    DooSearchLayout mDooSearchLayout;

    @Bind(R.id.search_rv)
    RecyclerView search_rv;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_search_layout);
    }


    EditText mEditText;
    @Override
    public void initViews() {
        super.initViews();
        mEditText = (EditText) mDooSearchLayout.findViewById(R.id.edit_text);
        mEditText.setEnabled(true);
        mEditText.setFocusable(true);
        initEditTextListener();
    }


    private void initEditTextListener(){
      mEditText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });
    }

}
