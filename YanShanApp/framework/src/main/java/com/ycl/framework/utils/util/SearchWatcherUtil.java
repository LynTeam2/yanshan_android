package com.ycl.framework.utils.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ycl.framework.utils.string.DensityUtils;

/**
 * Edittext 内容监听
 * @deprecated  暂时废弃
 */
public class SearchWatcherUtil implements TextWatcher {
    private EditText et_content;
    private ImageView iv_delete;
    private int MAXLINES;
    private OnUpdateListener onUpdateListener;

    private final float imgSize = 23;

    /**
     * @param viewRoot
     * @param et       所需监听的edittext
     * @param lines    lines最大行数
     */
    public SearchWatcherUtil(View viewRoot, EditText et, int lines) {
        MAXLINES = lines;
        this.et_content = et;
        iv_delete = new ImageView(viewRoot.getContext());

        if (viewRoot instanceof LinearLayout) {
            iv_delete.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(imgSize, viewRoot.getContext()), DensityUtils.dp2px(imgSize, viewRoot.getContext())));
        }

        if (TextUtils.isEmpty(et_content.getText().toString())) {
            iv_delete.setVisibility(View.VISIBLE);
            et_content.setSelection(et_content.getText().length());
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_content.getText().clear();
                et_content.requestFocus();
                et_content.findFocus();
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void setOnUpdateListener(OnUpdateListener mlistener) {
        this.onUpdateListener = mlistener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (onUpdateListener != null) {
            onUpdateListener.updateState();
        }
        String edit_text = et_content.getText().toString();
        if (edit_text.length() == 0) {
            iv_delete.setVisibility(View.GONE);
        } else {
            iv_delete.setVisibility(View.VISIBLE);
        }
        //限制行数
        int lines = et_content.getLineCount();
        if (lines > MAXLINES) {
            String str = s.toString();
            int cursorStart = et_content.getSelectionStart();
            int cursorEnd = et_content.getSelectionEnd();
            if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
                str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
            } else {
                str = str.substring(0, s.length() - 1);
            }
            // setText会触发afterTextChanged的递归
            et_content.setText(str);
            // setSelection用的索引不能使用str.length()否则会越界
            et_content.setSelection(et_content.getText().length());
        }
    }

    public interface OnUpdateListener {
        void updateState();
    }
}