package com.ycl.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FrameTextView extends TextView {

	public FrameTextView(Context context) {
		super(context);
	}

	public FrameTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FrameTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (text == null) {
			text = "";
		}
		super.setText(text, type);
	}
}