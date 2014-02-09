package com.redcarddev.kickshot;

import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jordan on 12/3/13.
 */
public class Winning extends Activity {

    String LOGTAG = this.getClass().getName();

    protected int state = -1;

    final static int COMPUTER_WON = 0;
    final static int PLAYER_WON = 1;

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

        this.setGameOverView();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.finish();
        return true;
    }

    protected boolean setGameOverView() {

        String action = "";
        // every time this is called randomize if it's a shot to the left or right
        switch (this.state) {
            case Winning.COMPUTER_WON:
                action = "YOU LOST!!!";
                break;
            case Winning.PLAYER_WON:
                action = "CONGRATS! YOU WON!!";
                break;
            default:
                return false;
        }

        this.setActionText(action);

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
