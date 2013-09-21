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
		
		
		Log.v(LOGTAG, "testBallTowardsAway x");
	}

}

