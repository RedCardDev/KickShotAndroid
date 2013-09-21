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
	protected Dice[] dice;
	Bitmap diceImage = null;
	
	
	private Canvas canvas = null;
	
	protected int chipXPos = 0;
	protected int chipYPos = 0;
	protected int chipInitYPos = 0;
	protected int chipLine = 0;
	
	protected int[] diceYPos = {0,0}; //represents the current position
	protected int[] diceHomeYPosition = {0,0}; //set in init
	protected int[] diceAwayYPosition = {150, 250};
	
	public final static int HOME = 1;
	public final static int AWAY = 2;
	
	int initSet = 0;

	public Board(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    
	    dice = new Dice[2];
	    this.dice[0] = new Dice(context);
	    this.dice[1] = new Dice(context);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	public Boolean ballPosession(int playerTurn) {
		
		int chipDrawable;
		
		if (playerTurn == Board.HOME) {//home chip
			chipDrawable = R.drawable.ballchiphome;
		} else if (playerTurn == Board.AWAY) {//away chip
			chipDrawable = R.drawable.ballchipaway;
		} else {
			return false;
		}
		
		Resources res = getContext().getResources();
		this.chip = BitmapFactory.decodeResource(res, chipDrawable);
		
		invalidate();
		return true;
	}
	
	public Boolean diceChangeFace(int dice, int diceFace) {
		
		if (dice < 1 || dice > 2) {
			return false;
		}
		
		this.dice[dice - 1].setDiceFace(diceFace);
		invalidate();
		
		return true;
	}
	
	public Boolean dicePositionHome(int dice) {
		
		if (dice < 1 || dice > 2) {
			return false;
		}
		
		this.diceYPos[dice - 1] = this.diceHomeYPosition[dice - 1];
		invalidate();
		
		return true;
	}
	
	public Boolean dicePositionAway(int dice) {
		
		if (dice < 1 || dice > 2) {
			return false;
		}
		
		this.diceYPos[dice - 1] = this.diceAwayYPosition[dice - 1];
		invalidate();
		
		return true;
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
		
		this.diceHomeYPosition[0] = this.canvas.getHeight() - 150;
		this.diceHomeYPosition[1] = this.canvas.getHeight() - 250;
		
		ballPosession(1);
		dicePositionHome(1);
		dicePositionHome(2);
		
		
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
		
		canvas.drawBitmap(this.dice[0].getCurrent(), 0, this.diceYPos[0], null);
		canvas.drawBitmap(this.dice[1].getCurrent(), 0, this.diceYPos[1], null);
		
		canvas.drawBitmap(this.chip, this.chipXPos, this.chipYPos, null);
		
	}

}
