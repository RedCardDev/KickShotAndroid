package com.redcarddev.kickshot;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.MenuItem;

import com.redcarddev.kickshot.utils.LevelOneState;

import java.util.Random;

import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity implements OnClickListener {
	
	protected String LOGTAG = "MainActivity";

    Menu menu;

    int REQUEST_LEADERBOARD = 1;
    int REQUEST_ACHIEVEMENTS = 2;
	
	Button juniorButton = null;
    Button r1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
		
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

        } else if (view.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            beginUserInitiatedSignIn();
        } else {
            Log.v(LOGTAG, Integer.toString(view.getId()));
        }
		
		Log.v(LOGTAG, "onClick e");
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_leaderboard:

                String leaderboard = String.format(getResources().getString(R.string.leaderboard_fastest_win));

                startActivityForResult(getGamesClient().getLeaderboardIntent(leaderboard), REQUEST_LEADERBOARD);
                return true;
            case R.id.menu_achievements:

                startActivityForResult(getGamesClient().getAchievementsIntent(), REQUEST_ACHIEVEMENTS);

                return true;

            case R.id.menu_invitations:

                if (getGamesClient().isConnected()) {//only check for invitations signed in
                    // request code (can be any number, as long as it's unique)
                    final int RC_INVITATION_INBOX = 10001;

                    // launch the intent to show the invitation inbox screen
                    Intent invitationIntent = getGamesClient().getInvitationInboxIntent();
                    startActivityForResult(invitationIntent, RC_INVITATION_INBOX);
                }

                return true;

            case R.id.menu_signout:

                // sign out.
                signOut();

                // show sign-in button, hide the sign-out button
                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSignInSucceeded() {
        // show sign-out button, hide the sign-in button
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);

        // (your code here: update UI, enable functionality that depends on sign in, etc)
    }

    @Override
    public void onSignInFailed() {
        // Sign in has failed. So show the user the sign-in button.
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }

}
