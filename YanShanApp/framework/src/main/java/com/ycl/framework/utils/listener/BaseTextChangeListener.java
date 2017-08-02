package com.ycl.framework.utils.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 自定义base
 * TextChangerListener 接口 by joeYu on 17/7/13.
 */

public class BaseTextChangeListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
