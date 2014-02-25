package com.redcarddev.kickshot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jordan on 2/18/14.
 */
public class CoolVideos extends Activity implements View.OnClickListener {
    protected String LOGTAG = "CorrectForm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cool_videos);

    }

    @Override
    public void onClick(View view) {

        Log.v(LOGTAG, "onClick e");

        /*if (view.getId() == R.id.header) {
            //Intent intent = new Intent(CorrectForm.this, HeaderHelp.class);
            //startActivity(intent);
        }else if(view.getId() == R.id.throw_in){
            //Intent intent = new Intent(CorrectForm.this, ThrowInHelp.class);
            //startActivity(intent);
        }else if(view.getId() == R.id.slide_tackle){
            //Intent intent = new Intent(CorrectForm.this, SlideTackleHelp.class);
            //startActivity(intent);
        }
        else {
            Log.v(LOGTAG, Integer.toString(view.getId()));
        }*/

        Log.v(LOGTAG, "onClick e");
    }
}
