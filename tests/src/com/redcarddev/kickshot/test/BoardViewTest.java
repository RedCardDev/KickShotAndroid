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

		
		int line = this.board.towardsAway(5);
		assertEquals(5, line);
		
		line = this.board.towardsAway(5);
		assertEquals(10, line);
		
		//the view should only move to line 11
		line = this.board.towardsAway(6);
		assertEquals(11, line);
		
		
		Log.v(LOGTAG, "testBallTowardsAway x");
	}
	
	public void testBallTowardsHome() {
		Log.v(LOGTAG, "testBallTowardsHome e");

		
		int line = this.board.towardsHome(5);
		assertEquals(-5, line);
		
		line = this.board.towardsHome(5);
		assertEquals(-10, line);
		
		//the view should only move to line 11
		line = this.board.towardsHome(6);
		assertEquals(-11, line);
		
		
		Log.v(LOGTAG, "testBallTowardsHome x");
	}

}

