package com.redcarddev.kickshot.test;

import com.redcarddev.kickshot.views.Board;

import android.test.AndroidTestCase;
import android.util.Log;

public class BoardViewTest extends AndroidTestCase {
	
	private String LOGTAG = "BoardViewTest";
	
	private Board board;
	
	public BoardViewTest(String name) {
		this.setName(name);
	}
	  
	protected void setUp() throws Exception {  
		super.setUp();
		
		this.board = new Board(mContext, null);
	} 
   
	public void testBallTowardsAway() {
		Log.v(LOGTAG, "testBallTowardsAway e");

		
		int line = this.board.ballTowardsAway(5);
		assertEquals(5, line);
		
		line = this.board.ballTowardsAway(5);
		assertEquals(10, line);
		
		//the view should only move to line 11
		line = this.board.ballTowardsAway(6);
		assertEquals(11, line);
		
		
		Log.v(LOGTAG, "testBallTowardsAway x");
	}
	
	public void testBallTowardsHome() {
		Log.v(LOGTAG, "testBallTowardsHome e");

		
		int line = this.board.ballTowardsHome(5);
		assertEquals(-5, line);
		
		line = this.board.ballTowardsHome(5);
		assertEquals(-10, line);
		
		//the view should only move to line 11
		line = this.board.ballTowardsHome(6);
		assertEquals(-11, line);
		
		
		Log.v(LOGTAG, "testBallTowardsHome x");
	}
	
	public void testBallPosession() {
		
		Log.v(LOGTAG, "testBallPosession e");
		
		Boolean set = false;
		
		set = this.board.ballPosession(1);
		assertTrue(set);
		
		set = this.board.ballPosession(2);
		assertTrue(set);
		
		set = this.board.ballPosession(Board.HOME);
		assertTrue(set);
		
		set = this.board.ballPosession(Board.AWAY);
		assertTrue(set);
		
		set = this.board.ballPosession(3);
		assertFalse(set);
		
		
		Log.v(LOGTAG, "testBallPosession x");
		
	}
	
	public void testDicePositionAway() {
		Log.v(LOGTAG, "testDicePositionAway e");
		
		Boolean set = false;
		
		//position the first dice to the away site
		set = this.board.dicePositionAway(0);
		assertFalse(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionAway(1);
		assertTrue(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionAway(2);
		assertTrue(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionAway(3);
		assertFalse(set);
		
		Log.v(LOGTAG, "testDicePositionAway x");
	}
	
	public void testDicePositionHome() {
		Log.v(LOGTAG, "testDicePositionHome e");
		
		Boolean set = false;
		
		//position the first dice to the away site
		set = this.board.dicePositionHome(0);
		assertFalse(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionHome(1);
		assertTrue(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionHome(2);
		assertTrue(set);
		
		//position the first dice to the away site
		set = this.board.dicePositionHome(3);
		assertFalse(set);
		
		Log.v(LOGTAG, "testDicePositionHome x");
	}
	
	public void testDiceChangeFace() {
		Log.v(LOGTAG, "testDiceChangeFace e");
		
		Boolean set = false;
		
		set = this.board.diceChangeFace(1, 0);
		assertTrue(set);
		
		set = this.board.diceChangeFace(2, 1);
		assertTrue(set);
		
		set = this.board.diceChangeFace(1, 2);
		assertTrue(set);
		
		set = this.board.diceChangeFace(2, 3);
		assertTrue(set);
		
		set = this.board.diceChangeFace(1, 4);
		assertTrue(set);
		
		set = this.board.diceChangeFace(2, 5);
		assertTrue(set);
		
		set = this.board.diceChangeFace(1, 6);
		assertTrue(set);
		
		set = this.board.diceChangeFace(2, 7);
		assertTrue(set);
		
		Log.v(LOGTAG, "testDiceChangeFace x");
		
		
	}

}

