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
	        case R.id.action_move_ball:
	        	
	        	switch(1) {
	        	
		        	case LevelOne.OFFENSE_STATE:
		        	case LevelOne.DEFENSE_STATE:
		        	case LevelOne.SHOT_STATE:
		        		this.rollDiceAction();
	        		break;
	        	
	        	}
	        	
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.board) {
			Log.v(LOGCAT, "Clicked on the board");
			
			
			this.rollDiceAction();
			
		}
		
	}
	
	protected int rollDice() {
		return r.nextInt(6-1) + 1;
	}
	
	protected void rollDiceAction() {
		
		int moves1 = this.rollDice();
		int moves2 = this.rollDice();
		
		this.board.diceChangeFace(1, moves1);
		this.board.diceChangeFace(2, moves2);

		if(moves1 > moves2){
			this.moveBall(moves1);
		}
		else if(moves2 > moves1){
			this.moveBall(moves2);
		}
		else{
			this.moveBall(moves1 + 1);
		}
		
	}
	
	protected void moveBall(int positions) {
		
		if (this.currentPlayer == 1) {
    		this.board.ballTowardsAway(positions);
    	} else {
    		this.board.ballTowardsHome(positions);
    	}
		
	}

}
