package com.redcarddev.kickshot;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class LevelOneRules extends Activity implements OnClickListener {
	
	String LOGTAG = this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.level_one_rules);

        WebView r = (WebView)findViewById(R.id.rule);

        String rules = "<h1>Rules</h1>\n" +
                "<h3>Turns</h3>" +
                "<ol>"+
                "    <li style=\"margin-bottom: 10px;\">Ball Starts at the midfield circle after every score or the start of each half.</li>\n" +
                "    <li style=\"margin-bottom: 10px;\">Offense rolls dice to advance the ball. Ball advances equal to the highest die value.</li>\n" +
                "    <li style=\"margin-bottom: 10px;\">If a double are rolled, the offense turns the ball over.</li>\n" +
                "    <li style=\"margin-bottom: 10px;\">Defense rolls dice. If doubles, defense intercepts the ball.</li>\n" +
                "    <li style=\"margin-bottom: 10px;\">If offense advances to the goal line, they enter shooting mode and take a shot immediately.</li>\n" +
                "    <li style=\"margin-bottom: 10px;\">Defense rolls dice. If doubles, defense blocks the shot and receives the ball on the line equal to the total dice roll.</li>" +
                "</ol>";

        String turnOvers = "<h3>Doubles</h3>" +
                "<p>If doubles are rolled, the ball is turned over.</p>";

        String shooting = "<h3>Scoring Goals</h3>" +
                "<p>When the offense makes it to the goal line, a shot is automatically taken."+
                "The defense can block a shot by rolling doubles.</p>";

        r.loadData(rules + turnOvers + shooting, "text/html", null);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
