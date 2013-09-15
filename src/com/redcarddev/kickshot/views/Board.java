package com.redcarddev.kickshot.views;

import com.redcarddev.kickshot.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View {

	Bitmap chip = null;
	
	Dice dice = null;
	Bitmap diceImage = null;
	
	
	private Canvas canvas = null;
	
	protected int chipXPos = 0;
	protected int chipYPos = 0;
	
	protected int diceXPos = 0;
	protected int diceYPos = 0;
	
	int initSet = 0;

	public Board(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    
	    this.dice = new Dice(context);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	public void changeChip(int playerTurn) {
		
		int chipDrawable;
		
		if (playerTurn == 1) {//home chip
			chipDrawable = R.drawable.ballchiphome;
		} else {//away chip
			chipDrawable = R.drawable.ballchipaway;
		}
		
		Resources res = getContext().getResources();
		
		
		this.chip = BitmapFactory.decodeResource(res, chipDrawable);
		invalidate();
	}
	
	public void changeDice(int diceFace) {
		this.dice.setDiceFace(diceFace);
		invalidate();
	}
	
	public void towardsAway(int steps) {
		this.moveChip(steps);
	}
	
	public void towardsHome(int steps) {
		this.moveChip(-1 * steps);
	}
	
	private void moveChip(int number) {
		this.chipYPos = this.chipYPos - (50 * number);
		invalidate();
	}
	
	private void init() {
		
		changeChip(1);
		
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
		
		canvas.drawBitmap(this.dice.getCurrent(), this.diceXPos, this.diceYPos, null);
		
		canvas.drawBitmap(this.chip, this.chipXPos, this.chipYPos, null);
		
	}

}
