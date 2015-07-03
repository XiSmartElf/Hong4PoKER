package com.example.hong4poker;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class SingleModeSelectionActivity extends Activity implements OnClickListener {
	Button singlePlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_single_mode_selection);
		singlePlayer = (Button)findViewById(R.id.singlePlayer);
		singlePlayer.setOnClickListener(this);
		//LaunchActivity.mySong.start();				
	}
	
	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch(button.getId())
		{
			case R.id.singlePlayer:
				Intent startSinglePlayMode = new Intent("com.example.hong4poker.ONESINGLEGAMINGACTIVITY");
				startActivity(startSinglePlayMode);
				//stop music from intro page
				//LaunchActivity.mySong.release();
				//finish the intro page
				LaunchActivity.launch.finish();
				//finish myself when game starts
				finish();
				break;
		
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(LaunchActivity.mySong.isPlaying())
			LaunchActivity.mySong.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LaunchActivity.mySong.start();
	}
	
}
