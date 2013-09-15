package com.redcarddev.kickshot;

import com.redcarddev.kickshot.views.Board;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LevelOne extends Activity {
	
	int currentPlayer = 1;
	
	Board board = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_one);
		
		 this.board = (Board)findViewById(R.id.board);
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
	        	
	        	if (this.currentPlayer == 1) {
	        		this.currentPlayer = 2;
	        	} else {
	        		this.currentPlayer = 1;
	        	}
	        	
	        	this.board.changeChip(this.currentPlayer);
	        	
	            return true;
	        case R.id.action_move_ball:
	        	
	        	if (this.currentPlayer == 1) {
	        		this.board.towardsAway(1);
	        	} else {
	        		this.board.towardsHome(1);
	        	}
	        	
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
