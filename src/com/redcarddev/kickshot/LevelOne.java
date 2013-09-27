package com.redcarddev.kickshot;

import java.util.Random;

import com.redcarddev.kickshot.views.Board;
import com.redcarddev.kickshot.views.Dice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class LevelOne extends Activity implements OnClickListener {
	
	String LOGCAT = LevelOne.class.getName();
	
	static Random r;
	
	int currentPlayer = 1;
	int currentState = 0;
	
	Board board = null;
	
	final static int OFFENSE_STATE = 1;
	final static int DEFENSE_STATE = 2;
	final static int SHOT_STATE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_one);
		
		 this.board = (Board)findViewById(R.id.board);
		 this.board.setOnClickListener(this);
		 
		 this.r = new Random();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.levelone, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	
	        	if (this.currentPlayer == 1) {//change to away
	        		this.currentPlayer = 2;
	        		this.board.dicePositionAway(1);
	        		this.board.dicePositionAway(2);
	        	} else {//change to home
	        		this.currentPlayer = 1;
	        		this.board.dicePositionHome(1);
	        		this.board.dicePositionHome(2);
	        	}
	        	
	        	this.board.ballPosession(this.currentPlayer);
	        	
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.board) {
			Log.v(LOGCAT, "Clicked on the board");
        	
        	switch(this.currentState) {
        	
	        	case LevelOne.OFFENSE_STATE:
	        	case LevelOne.DEFENSE_STATE:
	        		this.rollDiceAction();
	        		break;
	        	case LevelOne.SHOT_STATE:
	        		this.rollDiceAction();
	        		this.currentState = LevelOne.OFFENSE_STATE;
        		break;
        	
        	}
			
			this.rollDiceAction();
		}
		
	}
	

	protected void Shoot(int dice1Shot, int dice2Shot){
		int chipLoc = 0;
		
		
		if(this.currentPlayer == 2){
			this.board.dicePositionHome(1);
			this.board.dicePositionHome(2);
		}
		else{
			this.board.dicePositionAway(1);
			this.board.dicePositionAway(2);
		}
		
		
		if(dice1Shot != dice2Shot){
			//player score increments by 1
			//player variable is an argument
			//this must be the current player since the opponent is rolling
			//playerScore++
			System.out.println("scored!");
			chipLoc = 12;
		}
		else{
			if(this.currentPlayer == 1){
				chipLoc = 2;
			}
			else{
				chipLoc = 21;
			}
		}
		
		this.currentPlayer = (this.currentPlayer % 2) + 1;
		this.board.ballPosession(this.currentPlayer);
		this.board.setChipLocation(chipLoc);
		this.currentState = LevelOne.OFFENSE_STATE;
		//reposition the dice		
	}
	

	/**
	 * Find an integer between 1 and 6
	 * @return
	 */
	protected int rollDice() {
		return r.nextInt(6-1) + 1;
	}
	
	/**
	 * Roles both dice at the same time
	 * 
	 * Calculates two dice roles and changes the dice faces
	 * on the board. Then moves the ball the distance of the
	 * value of the largest dice (plus one for doubles).
	 * 
	 */
	protected void rollDiceAction() {
		if(this.currentPlayer == 1){
			this.board.dicePositionHome(1);
			this.board.dicePositionHome(2);
		}
		else{
			this.board.dicePositionAway(1);
			this.board.dicePositionAway(2);
		}
		
		int moves1 = this.rollDice();
		int moves2 = this.rollDice();
		
		this.board.diceChangeFace(1, moves1);
		this.board.diceChangeFace(2, moves2);
		
		//if moving shooting
		if(this.currentState == LevelOne.OFFENSE_STATE){
			if(moves1 > moves2){
				this.moveBall(moves1);
			}
			else if(moves2 > moves1){
				this.moveBall(moves2);
			}
			else{ //doubles were rolled
				this.moveBall(moves1 + 1);
			}
		}
		else if(this.currentState == LevelOne.DEFENSE_STATE){
			//try and intercept
		}
		else{
			this.Shoot(moves1, moves2);
			
		}
		
	}
	
	/**
	 * Has the view move the ball a specific number of lines
	 * 
	 * If currentPlayer is player #1 then move the ball towards
	 * the away goal, and if player #2 then move the ball
	 * towards the home goal. If the currentLine is 11 or -11
	 * then change the game state to SHOT_STATE.
	 * 
	 * @param positions
	 */
	protected void moveBall(int positions) {
		
		int currentLine = 0;
		
		if (this.currentPlayer == 1) {
    		currentLine = this.board.ballTowardsAway(positions);
    	} else {
    		currentLine = this.board.ballTowardsHome(positions);
    	}
		
		if (currentLine == 23 || currentLine == 0) {
			this.currentState = LevelOne.SHOT_STATE;
		}
		
	}

}
