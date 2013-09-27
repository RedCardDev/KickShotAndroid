package com.redcarddev.kickshot;

import java.util.Random;

import com.redcarddev.kickshot.views.Board;
import com.redcarddev.kickshot.views.Dice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LevelOne extends Activity implements OnClickListener {
	
	String LOGTAG = LevelOne.class.getName();
	
	static Random r;
	
	int currentPlayer = 1;
	int currentState = 1;
	
	Board board = null;
	
	final static int OFFENSE_STATE = 1;
	final static int DEFENSE_STATE = 2;
	final static int SHOT_STATE = 3;
	final static int BLOCK_STATE = 4;
	
	final static int AWAY_GOAL_LINE = 0;
	final static int HOME_GOAL_LINE = 23;
	
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
	        	
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.board) {
			Log.v(LOGTAG, "Clicked on the board");
        	
        	this.playerTurn();
        	this.computerTurn();
		}
		
	}
	
	
	public void showToast(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
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
	protected int[] rollDiceAction() {
		int moves1 = this.rollDice();
		int moves2 = this.rollDice();
		
		this.board.diceChangeFace(1, moves1);
		this.board.diceChangeFace(2, moves2);
		
		this.showToast("Rolled: " + moves1 + " and " + moves2);
		
		int[] moves = {moves1, moves2};
		
		return moves;
	}
	
	public Boolean doubles(int[] moves) {
		
		if (moves[0] == moves[1]) {
			return true;
		}
		
		return false;
	}
	
	public int max(int[] moves) {
		if (moves[0] > moves[1]) {
			return moves[0];
		}
		
		return moves[1];
	}
	
	public void playerOffenseAction() {
		Log.v(LOGTAG, "playerOffenseAction e");
		
		int currentLine = 0;
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) { //did the player lose possesion
			Log.v(LOGTAG, "playerOffenseAction\t change to defense state");
			this.currentState = LevelOne.DEFENSE_STATE;
			
			this.board.ballPosession(Board.AWAY);
			
		} else {//kept possesion
			
			currentLine = this.moveBall(this.max(moves));//make the move
			
			if (currentLine == LevelOne.AWAY_GOAL_LINE) {//take a shot?
				
				Log.v(LOGTAG, "playerOffenseAction\t change to shot state");
				
				this.currentState = LevelOne.SHOT_STATE;
				
			}
			
			
		}
		
		Log.v(LOGTAG, "offenseAction x");
		
	}
	
	public void computerOffenseAction() {
		
		int currentLine = 0;
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) { //switch to player on offense
			
			this.currentState = LevelOne.OFFENSE_STATE;
			this.board.ballPosession(Board.HOME);
			
		} else {
			
			currentLine = this.moveBall(this.max(moves));//make the move
			
			if (currentLine == LevelOne.HOME_GOAL_LINE) {//take a shot?
				
				Log.v(LOGTAG, "playerOffenseAction\t change to shot state");
				
				this.currentState = LevelOne.BLOCK_STATE;
				
			}
			
			
		}
		
	}
	
	public void playerDefenseAction() {
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) {//take controll of the ball
			
			this.currentState = LevelOne.OFFENSE_STATE;
			this.board.ballPosession(Board.HOME);
			
		}
		
		
	}
	
	public void computerDefenseAction() {
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) {//take away posession
			//switch posession
			this.currentState = LevelOne.DEFENSE_STATE;
			this.board.ballPosession(Board.AWAY);
			
			
			try{
			    Thread.sleep(5000);
			}catch(InterruptedException e){
			    System.out.println("got interrupted!");
			}
			
			this.computerOffenseAction();
		}
		
	}
	
	public void playerTurn() {
		
		switch(this.currentState) {
    	
	    	case LevelOne.OFFENSE_STATE:
	    		this.playerOffenseAction();
			break;
	    	case LevelOne.DEFENSE_STATE:
	    		this.playerDefenseAction();
    		break;
		
		}
		
	}
	
	public void computerTurn() {
		
		switch(this.currentState) {
    	
	    	case LevelOne.OFFENSE_STATE:
	    		this.computerDefenseAction();
			break;
	    	case LevelOne.DEFENSE_STATE:
	    		this.computerOffenseAction();
    		break;
		
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
	protected int moveBall(int positions) {
		
		int currentLine = 0;
		
		if (this.currentState == LevelOne.OFFENSE_STATE) {
    		currentLine = this.board.ballTowardsAway(positions);
    	} else if (this.currentState == LevelOne.DEFENSE_STATE) {
    		currentLine = this.board.ballTowardsHome(positions);
    	}
		
		return currentLine;
		
	}

}
