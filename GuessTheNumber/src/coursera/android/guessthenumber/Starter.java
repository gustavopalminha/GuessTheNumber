package coursera.android.guessthenumber;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Starter extends Activity {

	int answer;
	int lower;
	int higher;
	int guessRemaining;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starter);
		
		//setup instructions
		WebView webView = (WebView) findViewById(R.id.instructions);
	    String str = (String) this.getString(R.string.instructions);			
		webView.loadData(str, "text/html", "utf-8");		
		
		lower = 1;
		higher = 6;
		
		startGame();	
		
	}

	private int getRandom(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private void startGame() {
		answer = getRandom(lower, higher);
		guessRemaining = 3;
		TextView triesTextView = (TextView)findViewById(R.id.tries);
		String triesString = (String)this.getString(R.string.tries);
		
		triesTextView.setText(triesString + " " + guessRemaining);
	}
	
	public void doStart(View view) {
		startGame();
		Toast.makeText(this, "Game reseted!", Toast.LENGTH_LONG).show();
	}	

	public void doPlay(View view) {
		EditText guessEdit = (EditText)findViewById(R.id.player_guess);
		String guessStr = (String)guessEdit.getText().toString().replace(" ", "");
		guessEdit.setText(guessStr);
		
		String title = null, message = null;
		int icon = 0;	
		
		try{
			int gessInt = Integer.parseInt(guessStr);

			if(gessInt < lower || gessInt > higher){
				title = "Guess was out of bounds...";
				message = "It must be between " + String.valueOf(lower) + " and " + String.valueOf(higher) +".";
				icon = R.drawable.wrong_icon;				
			}else{
				
				if(gessInt == answer && guessRemaining > 0){
					title = "Congratulations...";
					message = "You found it!\nReset the game to play again.";
					icon = R.drawable.correct_icon;
				}else{
					if(guessRemaining > 0 ){
						guessRemaining --;	
						title = "Wrong answer...";
						message = "Keep trying and do not give up :)";
						icon = R.drawable.wrong_icon;							
					}
					
					if(guessRemaining == 0){
						title = "Game ended.";
						message = "No more tries left.\nReset it to play again :)";
						icon = R.drawable.wrong_icon;						
					}
					
					String triesString = (String)this.getString(R.string.tries);
					TextView triesTextView = (TextView)findViewById(R.id.tries);
					triesTextView.setText(triesString + " " + guessRemaining);
				}
			}
			
		}catch(NumberFormatException e){
			title = "This value is not valid...";
			message = e.getMessage();
			icon = R.drawable.error_icon;
		}
			
	    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
	    alertBuilder.setTitle(title);
	    alertBuilder.setMessage(message);
	    alertBuilder.setIcon(icon);
	    alertBuilder.setCancelable(true);
	    alertBuilder.setNeutralButton(android.R.string.ok,
		    new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		    		dialog.cancel();
		    }
	    });

	    AlertDialog alertDialog = alertBuilder.create();
	    alertDialog.show();	
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.starter, menu);
		return true;
	}

}
