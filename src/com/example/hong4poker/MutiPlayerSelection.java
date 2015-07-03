package com.example.hong4poker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MutiPlayerSelection extends Activity implements OnClickListener {

	Button startAgame;
	Button joinAgame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_muti_player_selection);
		startAgame=(Button)findViewById(R.id.startAgame);
		joinAgame=(Button)findViewById(R.id.joinAgame);
		startAgame.setOnClickListener(this);
		joinAgame.setOnClickListener(this);
		LaunchActivity.mySong.start();

	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch(button.getId())
		{
			case R.id.startAgame:
				//Intent startAnewGame = new Intent("com.example.hong4poker.STARTANEWGAME");
				//startActivity(startAnewGame);
				//finish();
				//LaunchActivity.mySong.pause();
				break;
			case R.id.joinAgame:
				//Intent joinAnotherGame = new Intent("com.example.hong4poker.SINGLEMODESELECTIONACTIVITY");
				//startActivity(joinAnotherGame);
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
