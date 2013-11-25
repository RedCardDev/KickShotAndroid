package com.redcarddev.kickshot;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.redcarddev.kickshot.views.Board;
import com.redcarddev.kickshot.views.Dice;

import android.os.Bundle;
import android.os.SystemClock;
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

	int currentState = 1;
	
	Board board = null;

    final static String PARAM_RANDOM = "";
	
	final static int OFFENSE_STATE = 1;
	final static int DEFENSE_STATE = 2;
	final static int SHOT_STATE = 3;
	final static int BLOCK_STATE = 4;
    final static int WON_STATE = 5;
    final static int LOST_STATE = 6;
	
	final static int AWAY_GOAL_LINE = 11;
	final static int HOME_GOAL_LINE = -11;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_one);

        this.board = (Board)findViewById(R.id.board);
        this.board.setOnClickListener(this);

        this.r = (Random) getIntent().getSerializableExtra(LevelOne.PARAM_RANDOM);
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
            case R.id.play_again:
                Intent start_again = new Intent(LevelOne.this, LevelOne.class);
                finish();
                startActivity(start_again);

                return true;
	        case R.id.action_rules:
	        	
	        	Intent intent = new Intent(LevelOne.this, LevelOneRules.class);
	        	startActivity(intent);
	        	
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.board) {
			Log.v(LOGTAG, "Clicked on the board");

            if(board.GameOver() == 1){
                this.currentState = LevelOne.LOST_STATE;
            }
            else if(board.GameOver() == 2){
                this.currentState = LevelOne.WON_STATE;
            }

			
			
			this.board.setEnabled(false);
        	
        	this.playerTurn();
            if(this.currentState != LevelOne.WON_STATE && this.currentState != LevelOne.LOST_STATE){
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {


                                board.dicePositionAway(1);
                                board.dicePositionAway(2);


                                Timer buttonTimer2 = new Timer();
                                buttonTimer2.schedule(new TimerTask() {

                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {


                                                computerTurn();

                                                Timer buttonTimer3 = new Timer();
                                                buttonTimer3.schedule(new TimerTask() {

                                                    @Override
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {

                                                            @Override
                                                            public void run() {



                                                                board.dicePositionHome(1);
                                                                board.dicePositionHome(2);

                                                                board.setEnabled(true);
                                                            }
                                                        });
                                                    }
                                                }, 1000);


                                            }
                                        });
                                    }
                                }, 3000);


                            }
                        });
                    }
                }, 1000);
            }
		}
		
	}
	
	
	public void showToast(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

    public void showAction(int state) {
        Log.v(LOGTAG, "showAction e, state=" + state);
        Intent myIntent = new Intent(this, LevelOneActions.class);
        myIntent.putExtra("state", state);
        startActivity(myIntent);
        Log.v(LOGTAG, "showAction x");
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
            this.showAction(LevelOneActions.PLAYER_TURNOVER);
			
		} else {//kept possesion
			
			currentLine = this.moveBall(this.max(moves));//make the move
			
			if (currentLine == LevelOne.AWAY_GOAL_LINE) {//take a shot?
				
				Log.v(LOGTAG, "playerOffenseAction\t change to shot state");
				
				this.currentState = LevelOne.SHOT_STATE;
				
				this.showToast(this.getResources().getString(R.string.LevelOnePlayerShot));
                this.showAction(LevelOneActions.PLAYER_SHOT);
				
				
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
            this.showAction(LevelOneActions.COMPUTER_TURNOVER);
			
		} else {
			
			currentLine = this.moveBall(this.max(moves));//make the move
			
			if (currentLine == LevelOne.HOME_GOAL_LINE) {//take a shot?
				
				Log.v(LOGTAG, "playerOffenseAction\t change to shot state");
				
				this.currentState = LevelOne.BLOCK_STATE;
				
				this.showToast(this.getResources().getString(R.string.LevelOneComputerShot));
                this.showAction(LevelOneActions.COMPUTER_SHOT);
				
			}
			
			
		}
		
	}
	
	public void playerDefenseAction() {
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) {//take controll of the ball
			
			this.currentState = LevelOne.OFFENSE_STATE;
            this.showAction(LevelOneActions.PLAYER_INTERCEPT);
			this.board.ballPosession(Board.HOME);
			
		}
		
		
	}
	
	public void computerDefenseAction() {
		
		int[] moves = this.rollDiceAction();
		
		if (doubles(moves)) {//take away posession
			//switch posession
			this.currentState = LevelOne.DEFENSE_STATE;
            this.showAction(LevelOneActions.COMPUTER_INTERCEPT);
			this.board.ballPosession(Board.AWAY);
		}
		
	}
	
	protected void playerBlockAction() {
		
		int[] moves = this.rollDiceAction();
		
		this.currentState = LevelOne.OFFENSE_STATE;
		this.board.ballPosession(Board.HOME);
		
		if (doubles(moves)) {//blocked
			
			this.showToast(this.getResources().getString(R.string.LevelOnePlayerBlock));
            this.showAction(LevelOneActions.PLAYER_BLOCKED);
			
			this.moveBall(moves[0] + moves[1]);

		} else {

            this.showAction(LevelOneActions.COMPUTER_SCORED);
			this.showToast(this.getResources().getString(R.string.LevelOneComputerGoal));
			
			this.board.goalAddAway();
			this.board.setChipLocation(0);
			
		}
		
	}
	
	protected void computerBlockAction() {
		
		int[] moves = this.rollDiceAction();

        this.currentState = LevelOne.DEFENSE_STATE;
        this.board.ballPosession(Board.AWAY);

		if (doubles(moves)) {//blocked
			
			this.showToast(this.getResources().getString(R.string.LevelOneComputerBlock));
            this.showAction(LevelOneActions.COMPUTER_BLOCKED);
			
			this.moveBall(moves[0] + moves[1]);
			
		} else {
			
			this.showToast(this.getResources().getString(R.string.LevelOnePlayerGoal));

            this.showAction(LevelOneActions.PLAYER_SCORED);
			this.board.goalAddHome();
			this.board.setChipLocation(0);
            //this really needs to have a timer added
            computerTurn();
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
	    	case LevelOne.BLOCK_STATE:
	    		this.playerBlockAction();
    		break;
            case LevelOne.LOST_STATE:
                this.showToast("You Lost!");
                break;
            case LevelOne.WON_STATE:
                this.showToast("You Won!");
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
	    	case LevelOne.SHOT_STATE:
	    		this.computerBlockAction();
                break;
            case LevelOne.LOST_STATE:
                this.showToast("You Lost!");
                break;
            case LevelOne.WON_STATE:
                this.showToast("You Won!");
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
