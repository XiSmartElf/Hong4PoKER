package com.example.hong4poker;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class LaunchActivity extends Activity implements OnClickListener {

	public static MediaPlayer mySong;
	public static LaunchActivity launch;
	int index = 0;
    Handler hand = new Handler();
    LinearLayout introImage;
    int images[]={R.drawable.intropage,R.drawable.intropage2,R.drawable.intropage3,R.drawable.intropage4};
    Button singlePlayer;
    Button multiPlayer;
    Button About;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_launch);
		
		//setting background image and switch every 100 millsecs
		introImage =  (LinearLayout)findViewById(R.id.intropage);
        hand.postDelayed(backgroundSwitch,100);
        
		//init all buttons
        singlePlayer=(Button)findViewById(R.id.singlePlayer);
        multiPlayer=(Button)findViewById(R.id.multiPlayer);
        About=(Button)findViewById(R.id.about);
        singlePlayer.setOnClickListener(this);
        multiPlayer.setOnClickListener(this);
        About.setOnClickListener(this);
        
		//start playing background music
		mySong = MediaPlayer.create(LaunchActivity.this, R.raw.intro_sound);
			mySong.start();
		
		//set reference of this instance so that later activity can control it
		launch=this;
		
	}
	
	Runnable backgroundSwitch = new Runnable() {
        @Override
        public void run() {
        	introImage.setBackgroundDrawable(getResources().getDrawable(images[index++]));
            if (index == images.length)
                index = 0;
            hand.postDelayed(backgroundSwitch,100);
        }
    };

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.cool_menu, menu);
		return true;	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.preference:
				Intent openPreference = new Intent("com.example.hong4poker.PREFS");
				startActivity(openPreference);
				break;	
			case R.id.exit:
					System.exit(0); // close application completely
					break;			
		}
		return true;
	}

	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(LaunchActivity.mySong.isPlaying())
			mySong.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			mySong.start();
	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch (button.getId())
		{
			case R.id.singlePlayer:
				Intent startSinglePlayMode = new Intent("com.example.hong4poker.SINGLEMODESELECTIONACTIVITY");
				startActivity(startSinglePlayMode);
				break;
	
			case R.id.multiPlayer:
				Intent startMultiPlayMode = new Intent("com.example.hong4poker.MUTIPLAYERSELECTION");
				startActivity(startMultiPlayMode);
				break;
				
			case R.id.about:
				Intent startAboutPage = new Intent("com.example.hong4poker.ABOUTPAGE");
				startActivity(startAboutPage);
				break;
		}	
		
	}
	
	
}
