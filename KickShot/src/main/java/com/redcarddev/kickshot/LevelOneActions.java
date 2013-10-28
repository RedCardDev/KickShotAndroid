package com.redcarddev.kickshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by otternq on 10/27/13.
 */
public class LevelOneActions extends Activity {

    String LOGTAG = this.getClass().getName();

    final static int COMPUTER_SCORED = 1;
    final static int COMPUTER_BLOCKED = 3;
    final static int COMPUTER_SHOT = 5;

    final static int PLAYER_SCORED = 0;
    final static int PLAYER_BLOCKED = 2;
    final static int PLAYER_SHOT = 4;

    protected int state = -1;

    protected TextView actionText;
    protected ImageView actionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_one_actions);

        Intent mIntent = getIntent();
        this.state = mIntent.getIntExtra("state", -1);

        this.actionText = (TextView)findViewById(R.id.actionText);
        this.actionImage = (ImageView)findViewById(R.id.actionImage);

        this.setActionView();

    }

    protected boolean setActionView() {

        String action = "";
        String image = "";

        switch (this.state) {
            case LevelOneActions.COMPUTER_SCORED:
                action = "They scored!";
                break;
            case LevelOneActions.COMPUTER_BLOCKED:
                action = "They blocked your shot!";
                break;
            case LevelOneActions.COMPUTER_SHOT:
                action = "They are shooting!";
                break;
            case LevelOneActions.PLAYER_SCORED:
                action = "You scored!";
                break;
            case LevelOneActions.PLAYER_BLOCKED:
                action = "You blocked their shot!";
                break;
            case LevelOneActions.PLAYER_SHOT:
                action = "You are shooting!";
                break;
            default:
                return false;
        }

        this.setActionText(action);
        //this.setActionImage(image);

        return true;

    }

    protected boolean setActionImage(String image) {
        return false;
    }

    protected boolean setActionText(String action) {

        this.actionText.setText(action);

        return true;

    }



}
