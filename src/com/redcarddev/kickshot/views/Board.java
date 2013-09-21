package com.redcarddev.kickshot.views;

import com.redcarddev.kickshot.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View {
	
	String LOGTAG = "BoardView";

	Bitmap chip = null;
	
	//two dice in case of doubles
	Dice dice1 = null;
	Dice dice2 = null;
	Bitmap diceImage = null;
	
	
	private Canvas canvas = null;
	
	protected int chipXPos = 0;
	protected int chipYPos = 0;
	protected int chipInitYPos = 0;
	
	protected int chipLine = 0;
	
	protected int dice1XPos = 0;
	protected int dice1YPos = 0;
	protected int dice2XPos = 0;
	protected int dice2YPos = 0;
	
	protected int dice1HomePosition = 0;
	protected int dice2HomePosition = 0;
	
	public final static int CHIP_HOME = 1;
	public final static int CHIP_AWAY = 2;
	
	int initSet = 0;

	public Board(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    
	    this.dice1 = new Dice(context);
	    this.dice2 = new Dice(context);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	public Boolean ballPosession(int playerTurn) {
		
		int chipDrawable;
		
		if (playerTurn == Board.CHIP_HOME) {//home chip
			chipDrawable = R.drawable.ballchiphome;
		} else if (playerTurn == Board.CHIP_AWAY) {//away chip
			chipDrawable = R.drawable.ballchipaway;
		} else {
			return false;
		}
		
		Resources res = getContext().getResources();
		this.chip = BitmapFactory.decodeResource(res, chipDrawable);
		
		invalidate();
		return true;
	}
	
	public void changeDice(int dice1Face, int dice2Face) {
		this.dice1.setDiceFace(dice1Face);
		this.dice2.setDiceFace(dice2Face);
		invalidate();
	}
	
	public void positionDiceHome() {
		
		//this.diceYPos = this.diceHomePosition;
		this.dice1YPos = 150;
		this.dice2YPos = 250;
		invalidate();
		
	}
	
	public void positionDiceAway() {
		this.dice1YPos = 150;
		this.dice2YPos = 250;
		invalidate();
	}
	
	public int ballTowardsAway(int steps) {
		this.chipLine += steps;
		return this.ballMove();
	}
	
	public int ballTowardsHome(int steps) {
		this.chipLine -= steps;
		return this.ballMove();
	}
	
	private int ballMove() {
		
		if(this.chipLine <= -11){ //do not allow past the home goal
			this.chipLine = -11;
		}
		else if(this.chipLine >= 11){//do not allow past the away goal
			this.chipLine = 11;
		}
		
		Log.v(LOGTAG, "Chip Line: " + this.chipLine);
		
		this.chipYPos = this.chipInitYPos - (40 * this.chipLine);
		Log.v(LOGTAG, "Setting chipYPos: " + this.chipYPos);
		
		invalidate();
		
		return this.chipLine;
	}
	
	private void init() {
		
		this.dice1HomePosition = this.canvas.getHeight() - 150;
		this.dice2HomePosition = this.canvas.getHeight() - 250;
		
		ballPosession(1);
		positionDiceHome();
		
		
		this.chipXPos = (this.canvas.getWidth() - this.chip.getWidth()) / 2;
		this.chipYPos = this.chipInitYPos = (this.canvas.getHeight() - this.chip.getHeight()) / 2;
		
		this.initSet = 1;
		
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		this.canvas = canvas;
		
		if (this.initSet == 0) {
			this.init();
		}
		
		canvas.drawBitmap(this.dice1.getCurrent(), this.dice1XPos, this.dice1YPos, null);
		canvas.drawBitmap(this.dice2.getCurrent(), this.dice2XPos, this.dice2YPos, null);
		
		canvas.drawBitmap(this.chip, this.chipXPos, this.chipYPos, null);
		
	}

}
