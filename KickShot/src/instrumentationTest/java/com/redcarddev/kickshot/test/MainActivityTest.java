package com.redcarddev.kickshot.test;

import com.redcarddev.kickshot.MainActivity;
import com.redcarddev.kickshot.LevelOne;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.squareup.spoon.Spoon;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private Activity context;
	
	private Solo solo;
	
	public MainActivityTest() {
		super(MainActivity.class);
		
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		this.context = this.getActivity();
		this.solo = new Solo(this.getInstrumentation(), this.getActivity());
		
	}
	
	public void testMainAcreen() {
		Spoon.screenshot(this.context, "InitialState");
		
		solo.assertCurrentActivity("Expect that the MainActivity is open", MainActivity.class);
		
		solo.clickOnButton("Junior");
		
		solo.assertCurrentActivity("Change the the Junior Game", LevelOne.class);
		
		Spoon.screenshot(this.context,  "LevelOneInitial");
		
	}

}
