package com.redcarddev.kickshot.views;

import com.redcarddev.kickshot.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Board extends View {
	
	Paint mPaint = null;
	Bitmap chip = null;
	
	private Canvas canvas = null;
	
	private int mWidth;
	private int mHeight;
	
	protected int chipXPos = 0;
	protected int chipYPos = 0;
	
	int initSet = 0;

	public Board(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}
	
	private void init() {
		Resources res = getContext().getResources();
		int chipDrawable = R.drawable.ballchiphome;
		
		this.chip = BitmapFactory.decodeResource(res, chipDrawable);
		
		this.chipXPos = (this.canvas.getWidth() - this.chip.getWidth()) / 2;
		this.chipYPos = (this.canvas.getHeight() - this.chip.getHeight()) / 2;
		
		this.initSet = 1;
		
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		this.canvas = canvas;
		
		if (this.initSet == 0) {
			this.init();
		}
		
		canvas.drawBitmap(this.chip, this.chipXPos, this.chipYPos, null);
		
	}

}
