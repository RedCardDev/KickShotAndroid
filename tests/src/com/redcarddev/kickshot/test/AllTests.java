package com.redcarddev.kickshot.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite(AllTests.class.getName());
		
		suite.addTest(new BoardViewTest("testFirst"));
		
		return suite;
	}

}
