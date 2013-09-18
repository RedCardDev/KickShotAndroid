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
	
	int currentPlayer = 1;
	
	Board board = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_one);
		
		 this.board = (Board)findViewById(R.id.board);
		 this.board.setOnClickListener(this);
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
	        		this.board.positionDiceAway();
	        	} else {//change to home
	        		this.currentPlayer = 1;
	        		this.board.positionDiceHome();
	        	}
	        	
	        	this.board.changeChip(this.currentPlayer);
	        	
	            return true;
	        case R.id.action_move_ball:
	        	
	        	
	        	
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.board) {
			Log.v(LOGCAT, "Clicked on the board");
			
			Random r = new Random();
			int numloop = 0;
			long lastSec = 0;
			int moves1 = 1;
			int moves2 = 1;
			while(numloop < 8){
				long sec = System.currentTimeMillis() / 1000;
				if(sec != lastSec){
					numloop++;
					moves1 = r.nextInt(6-1) + 1;
					moves2 = r.nextInt(6-1) + 1;
					this.board.changeDice(moves1, moves2);
					lastSec = sec;
				}
			}
			
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
		
	}
	
	protected void moveBall(int positions) {
		
		if (this.currentPlayer == 1) {
    		this.board.towardsAway(positions);
    	} else {
    		this.board.towardsHome(positions);
    	}
		
	}

}
