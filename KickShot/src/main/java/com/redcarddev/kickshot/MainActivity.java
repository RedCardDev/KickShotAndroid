package com.redcarddev.kickshot;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.redcarddev.kickshot.utils.LevelOneState;

import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {
	
	protected String LOGTAG = "MainActivity";
	
	Button juniorButton = null;
    Button r1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.juniorButton = (Button)findViewById(R.id.junior);
		this.juniorButton.setOnClickListener(this);

        this.r1 = (Button)findViewById(R.id.r1);
        this.r1.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public void onClick(View view) {
		
		Log.v(LOGTAG, "onClick e");
		
		if (view.getId() == R.id.junior) {
			
			Intent intent = new Intent(MainActivity.this, LevelOne.class);

            Random random = new Random();
            LevelOneState state = new LevelOneState();
            intent.putExtra(LevelOne.PARAM_RANDOM, random);
            intent.putExtra(LevelOne.PARAM_STATE, state);



			startActivity(intent);
			
			
		} else if (view.getId() == R.id.r1) {

            Intent intent = new Intent(MainActivity.this, LevelOneRules.class);
            startActivity(intent);

        } else {
            Log.v(LOGTAG, Integer.toString(view.getId()));
        }
		
		Log.v(LOGTAG, "onClick e");
	}

}
