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
		
		set = this.board.ballPosession(Board.CHIP_HOME);
		assertTrue(set);
		
		set = this.board.ballPosession(Board.CHIP_AWAY);
		assertTrue(set);
		
		set = this.board.ballPosession(3);
		assertFalse(set);
		
		
		Log.v(LOGTAG, "testBallPosession x");
		
	}
	
	public void testDiceSetFace() {
		Log.v(LOGTAG, "testDiceSetFace e");
		
		
		
		Log.v(LOGTAG, "testDiceSetFace x");
	}

}
