package com.lt.autocomemaildemo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class AutoCompleteEditText extends EditText implements TextWatcher {

	private Bitmap mBitmap;
	private int mHeight;
	private int mWidth;
	private Paint mPaint;
	private int mBaseLine;
	private Canvas mCanvas;
	private BitmapDrawable mDrawable;
	private boolean mFlag;
	private String mAddedText;
	

	/**
	 * 常用的邮箱
	 */
	private HashMap<String, String> mAutoData = new HashMap<String, String>();

	public AutoCompleteEditText(Context context) {
		super(context);
		initEmail();
		addTextChangedListener(this);
	}

	public AutoCompleteEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initEmail();
		addTextChangedListener(this);
	}

	public AutoCompleteEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initEmail();
		addTextChangedListener(this);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (!focused) {
			append(mAddedText);
		}
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void afterTextChanged(Editable s) {
		String text = s.toString();
		mFlag = true;
		Iterator<Map.Entry<String, String>> iterator = mAutoData.entrySet()
				.iterator();
		// 遍历常用邮箱
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if (text.endsWith(entry.getKey())) {
				drawAddedText(entry.getValue());
				mAddedText = entry.getValue();
				mFlag = false;
				break;
			}
		}

		// 如果没有匹配，就画一个空
		if (mFlag) {
			drawAddedText("");
			mAddedText = "";
		}
	}

	/**
	 * 画出后缀字符串
	 * 
	 * @param addedText
	 */
	@SuppressWarnings("deprecation")
	private void drawAddedText(String addedText) {
		// 如果字符串为空，画空
		if (addedText.equals("")) {
			setCompoundDrawables(null, null, null, null);
			return;
		}
		// 只需要初始化一次
		if (mBitmap == null) {
			mHeight = getHeight();
			mWidth = getWidth();

			// 初始化画笔
			mPaint = new Paint();
			mPaint.setColor(Color.GRAY);
			mPaint.setAntiAlias(true);// 去除锯齿
			mPaint.setFilterBitmap(true);// 对位图进行滤波处理
			mPaint.setTextSize(getTextSize());

		}

		// 计算baseLine
		Rect rect = new Rect();
		int baseLineLocation = getLineBounds(0, rect);
		mBaseLine = baseLineLocation - rect.top;

		// 添加的字符窜的长度
		int addedTextWidth = (int) (mPaint.measureText(addedText) + 1);

		// 创建bitmap
		mBitmap = Bitmap.createBitmap(addedTextWidth, mHeight,
				Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

		// 绘制后缀字符串
		mCanvas.drawText(addedText, 0, mBaseLine, mPaint);
		// bitmap转化为Drawable
		mDrawable = new BitmapDrawable(mBitmap);

		String text = getText().toString();
		// 计算后缀字符串在输入框中的位置
		int addedTextLeft = (int) (mPaint.measureText(text) - mWidth + addedTextWidth);
		int addedTextRight = addedTextLeft + addedTextWidth;
		int addedTextTop = 0;
		int addedTextBottom = addedTextTop + mHeight;
		// 设置后缀字符串位置
		mDrawable.setBounds(addedTextLeft, addedTextTop, addedTextRight,
				addedTextBottom);
		// 显示后缀字符串
		setCompoundDrawables(null, null, mDrawable, null);
	}

	/**
	 * 初始化常用的邮箱
	 */
	private void initEmail() {
		mAutoData.put("@q", "q.com");
		mAutoData.put("@qq", ".com");
		mAutoData.put("@qq.", "com");
		mAutoData.put("@qq.c", "om");
		mAutoData.put("@qq.co", "m");
		mAutoData.put("@1", "63.com");
		mAutoData.put("@16", "3.com");
		mAutoData.put("@163", ".com");
		mAutoData.put("@163.", "com");
		mAutoData.put("@163.c", "om");
		mAutoData.put("@163.co", "m");
		mAutoData.put("@s", "ina.cn");
		mAutoData.put("@si", "na.cn");
		mAutoData.put("@sin", "a.cn");
		mAutoData.put("@sina", ".cn");
		mAutoData.put("@sina.", "cn");
		mAutoData.put("@sina.c", "n");
		mAutoData.put("@s", "ina.com");
		mAutoData.put("@si", "na.com");
		mAutoData.put("@sin", "a.com");
		mAutoData.put("@sina", ".com");
		mAutoData.put("@sina.", "com");
		mAutoData.put("@sina.c", "om");
		mAutoData.put("@sina.co", "m");
		mAutoData.put("@1", "26.com");
		mAutoData.put("@12", "6.com");
		mAutoData.put("@126", ".com");
		mAutoData.put("@126.", "com");
		mAutoData.put("@126.c", "om");
		mAutoData.put("@126.co", "m");
	}

}
