package com.example.hong4poker;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class oneSingleGamingActivity extends Activity{
	MediaPlayer gameSong;
	SoundPool cards;
	MediaPlayer shuffle;
	SoundPool flipCards;
	SoundPool send;
	SoundPool fail;
	SoundPool youquit;
	SoundPool otherquit;
	SoundPool win;
	AlertDialog continueGame;
	String whoWhinningString;
	Poker poker;
	NPC npc;
	float touchX,touchY;
	CanvasView canvasView;
	Thread gaming;
	Thread otherPlay;
	String showMessage = "nothing";
	int whoStarts;
	String userName;
	ArrayList<String> Cards_currentGive;
	ArrayList<String> cards_beforeGive;
	ArrayList<String> CardsUser1;
	ArrayList<String> CardsUser2;
	ArrayList<Bitmap> user1PlayingCards;
	ArrayList<Bitmap> user2PlayingCards;
	int canvasX, canvasY;
	int[]x =  new int[13];
	int[]y =  new int[13];
	int whoIsTouched=-1;
	boolean valid = false;
	ArrayList<Integer> selectedList;
	ArrayList<Bitmap> cardsGotRidPutInMiddleMap;
	int playerIndex;
	int orgSizeWidth;
	int orgSizeHeight;
	boolean firtTimeGiveCards = true;
	long tStart=0;
	boolean youQuit =false;
	int soundCards,soundSend,soundFlip,soundFail,soundWin,soundYouquit,soundOtherquit;
	boolean isRunning = true;
	Thread ourThread;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		canvasView = new CanvasView(this);
		//play game background music
		cards = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		send = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		fail = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		youquit = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		otherquit = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		win = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		flipCards= new SoundPool(1, AudioManager.STREAM_MUSIC,0);
		soundCards = cards.load(this, R.raw.cards,1);
		soundFlip= flipCards.load(this, R.raw.flipcards,1); 
		soundSend = send.load(this, R.raw.send,1); 
		soundWin = win.load(this, R.raw.win,1); 
		soundFail = fail.load(this, R.raw.fail,1); 
		soundYouquit = youquit.load(this, R.raw.youquit,1); 
		soundOtherquit = otherquit.load(this, R.raw.otherquit,1); 	
		gameSong = MediaPlayer.create(this, R.raw.gaming1);
		shuffle = MediaPlayer.create(this, R.raw.shuffle);
		gameSong.setLooping(true);
		gameSong.start();
		poker = new Poker();
		npc = new NPC();
		touchX=0;touchY=0;
		Cards_currentGive = new ArrayList<String>();
		cards_beforeGive= new ArrayList<String>();
		user1PlayingCards = new ArrayList<Bitmap>();
		user2PlayingCards = new ArrayList<Bitmap>();
		selectedList = new ArrayList<Integer>();
		cardsGotRidPutInMiddleMap = new ArrayList<Bitmap>();
		CardsUser1 = new ArrayList<String>();
		CardsUser2= new ArrayList<String>();
		gaming = new Thread(){
			public void run(){
				gameRounds();
			}
		};
		gaming.start();
		setContentView(canvasView);
	}
	
	
	private void gameRounds() {	
		SystemClock.sleep(1000);
		showMessage = "beginMessage";
		SystemClock.sleep(3000);
		showMessage = "nothing"; 
		SystemClock.sleep(500);		
		whoStarts = poker.Shuffle_Distribute(2);	
		while (whoStarts==99)
			 whoStarts = poker.Shuffle_Distribute(2); 
		if (whoStarts == 1)
			userName="Player1(You)";
		else
			userName="Player2(NPC)";
		playerIndex=whoStarts;
		showMessage = "shuffle"; 
		shuffle.start();
		SystemClock.sleep(5000);
		showMessage = "flipCards"; 
		flipCards.play(soundFlip, 1, 1, 0, 0,1);
		SystemClock.sleep(5000);
		showMessage = "playing";
		if(playerIndex!=1)
			computerOtherPlayers();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==event.ACTION_UP)	
		{
			if(playerIndex==1 && showMessage=="playing")
			{
				touchX = event.getX();
				touchY = event.getY();
				for(int i=0; i<13; i++)	
				{
					if(i==0) //if it's out of bound
					{
						if (x[i]< touchX && touchY> canvasY/6*5)
							whoIsTouched=i;
					}
					else //if it's not out of bound
					{
						if (x[i-1]> touchX && touchX>x[i] && touchY> canvasY/6*5)
							whoIsTouched=i;
					}		
				}
		
				//when touch the skip button
				if(touchX>=canvasX-orgSizeWidth*2 && touchX <=(canvasX-orgSizeWidth*2)+(orgSizeWidth*3/2) && touchY>=(canvasY-orgSizeHeight*2) && touchY<=(canvasY-orgSizeHeight*2)+(orgSizeHeight/2+10))
				{
					youquit.play(soundYouquit, 1, 1, 0, 0,1);
					for(int n=0; n<selectedList.size();n++)
					{
						int temp=selectedList.get(n);
						y[temp]+=40;  //make the card go back to where it was
					}	
					Cards_currentGive.clear();
					selectedList.clear();
					cards_beforeGive.clear();
					firtTimeGiveCards=true; //since you quit, it will be new start from the other player***<<this part will be switch case for mutliple NPC>>
					playerIndex=poker.whoIsNext(playerIndex, 2); //give up the right and move to the next player
					youQuit=true;
					otherPlay= new Thread()
					{
						public void run()
						{
							SystemClock.sleep(1000);
							computerOtherPlayers();
						}

					};
					otherPlay.start();
				}
				//when touch the gobutton
				if (touchX >= (canvasX-orgSizeWidth*4) && touchX <= (canvasX-orgSizeWidth*4)+(orgSizeWidth*3/2) && touchY>= (canvasY-orgSizeHeight*2) && touchY<= (canvasY-orgSizeHeight*2)+(orgSizeHeight/2+10)) 
				{
					valid=GameRule.validateCards(Cards_currentGive, cards_beforeGive, firtTimeGiveCards); //validate cards based on first time our not
					if(valid==true)
					{
						send.play(soundSend, 1, 1, 0, 0,1);
						firtTimeGiveCards=false;
						//delete the cards sent
						if(poker.getRideOfcards(Cards_currentGive, 1)==false)
						{
							for(int n=0; n<selectedList.size();n++)
							{
								int temp=selectedList.get(n);
								y[temp]+=40;  //make the card go back to where it was
							}	
							cardsGotRidPutInMiddleMap.clear();//redefine a collection for delete so previous cards no longer available
							for(int n=0; n<selectedList.size();n++)
							{
								int temp=selectedList.get(n);
								cardsGotRidPutInMiddleMap.add(user1PlayingCards.get(temp));   //put all the cards in the collection for next delete instruction
							}
							user1PlayingCards.removeAll(cardsGotRidPutInMiddleMap);//remove the drawing bitmap of the sent cardsfrom the list
							selectedList.clear();		  //clear the list which stores all the position indexes	
							gameOver();
						}  
						else
						{
							for(int n=0; n<selectedList.size();n++)
							{
								int temp=selectedList.get(n);
								y[temp]+=40;  //make the card go back to where it was
							}	
							cardsGotRidPutInMiddleMap.clear();//redefine a collection for delete so previous cards no longer available
							for(int n=0; n<selectedList.size();n++)
							{
								int temp=selectedList.get(n);
								cardsGotRidPutInMiddleMap.add(user1PlayingCards.get(temp));   //put all the cards in the collection for next delete instruction
							}
							user1PlayingCards.removeAll(cardsGotRidPutInMiddleMap);//remove the drawing bitmap of the sent cardsfrom the list
							selectedList.clear();		  //clear the list which stores all the position indexes			
							cards_beforeGive.clear();
							for(String card: Cards_currentGive)
								cards_beforeGive.add(card);     //set the before cards
							Cards_currentGive.clear();		//clear the current cards for next round use
							playerIndex=poker.whoIsNext(playerIndex, 2);
							otherPlay= new Thread()
							{
								public void run()
								{
									SystemClock.sleep(1000);
									computerOtherPlayers();
								}

							};
							otherPlay.start();
						}
					}
				}
			}
		}
		return super.dispatchTouchEvent(event);
	    }

	public void computerOtherPlayers()
	{		
		SystemClock.sleep(1500);
		npc.pickCards(firtTimeGiveCards, cards_beforeGive, poker, playerIndex);//assigh the return value1
		if(npc.cardsNPCgive==null)
		{
			otherquit.play(soundOtherquit, 1, 1, 0, 0,1);
			firtTimeGiveCards=true; //npc gives up then i do whatever-
		}
		else
		{
			send.play(soundSend, 1, 1, 0, 0,1);
			if(poker.getRideOfcards(npc.cardsNPCgive, playerIndex)==false)
			{
				firtTimeGiveCards=false;//npc gives card
				cardsGotRidPutInMiddleMap.clear();
				for(int i=0;i<npc.indexes.length;i++)
					cardsGotRidPutInMiddleMap.add(user2PlayingCards.get((int)npc.indexes[i]));
				user2PlayingCards.removeAll(cardsGotRidPutInMiddleMap);
				gameOver();
			}
			else
			{
				firtTimeGiveCards=false;//npc gives card
				cards_beforeGive.clear();
				for(int i=0; i<npc.cardsNPCgive.size();i++)
					cards_beforeGive.add(npc.cardsNPCgive.get(i));
				cardsGotRidPutInMiddleMap.clear();
				for(int i=0;i<npc.indexes.length;i++)
					cardsGotRidPutInMiddleMap.add(user2PlayingCards.get((int)npc.indexes[i]));
				user2PlayingCards.removeAll(cardsGotRidPutInMiddleMap);
			}
		}
		//indicates who is next
		youQuit=false;
		playerIndex=poker.whoIsNext(playerIndex, 2);
	}
	//*************************the surface view class of the game********************
	public class CanvasView extends SurfaceView implements Runnable 
	{
		SurfaceHolder ourHolder;
		Typeface font1;
		Typeface font2;
		Paint textPaint;
		Paint recPaint;
		Rect rectangle;
		Bitmap background;
		Bitmap cardBack;
		int recLength=0;
		int shuffleX, shuffleY;
		Random randShuffle;
		Bitmap[] user1Card = new Bitmap[13];
		Bitmap[] user2Card = new Bitmap[13];
		Bitmap[] paintingCard = new Bitmap[13];
		Bitmap goButton;
		Bitmap skipButton;
		Bitmap user1face;
		Bitmap user2face;
		Bitmap user1faceSelect;
		Bitmap user2faceSelect;
		Bitmap arrow1;
		Bitmap arrow2;		
		int changSizeWidth;
		int changSizeHeight;
		boolean firstTime;
		boolean firstTime2;
		boolean firstTime3;
		boolean previousTest=true;
		Random a;
		Canvas canvas;

		
		public CanvasView(Context context) {
			super(context);
			//text
			textPaint = new Paint();
			textPaint.setTextAlign(Align.CENTER);
			textPaint.setTextSize(200);	
			font1 = Typeface.createFromAsset(context.getAssets(), "font1.ttf");	
			font2 = Typeface.createFromAsset(context.getAssets(), "font2.ttf");	
			textPaint.setTypeface(font1);
			//rec
			rectangle =  new Rect();
			recPaint =  new Paint();
			recPaint.setColor(Color.BLUE);
			recPaint.setTypeface(font2);
			//background image
			background = BitmapFactory.decodeResource(getResources(), R.drawable.pokertbl);
			cardBack = BitmapFactory.decodeResource(getResources(), R.drawable.back);
			//else setup
			ourHolder = getHolder();
			ourThread = new Thread(this);
			ourThread.start();
			randShuffle = new Random();
			firstTime=true;
			firstTime2=true;
			firstTime3=true;
			user1face = BitmapFactory.decodeResource(getResources(),R.drawable.user1);
			user2face = BitmapFactory.decodeResource(getResources(),R.drawable.user2);
			user1faceSelect = BitmapFactory.decodeResource(getResources(),R.drawable.user1select);
			user2faceSelect = BitmapFactory.decodeResource(getResources(),R.drawable.user2select);
			arrow1 = BitmapFactory.decodeResource(getResources(),R.drawable.arrow);
			arrow2 = BitmapFactory.decodeResource(getResources(),R.drawable.arrow2);
		}
		
		@Override
		public void run() {
			while(isRunning)
			{
				if(!ourHolder.getSurface().isValid())
					continue;
				canvas = ourHolder.lockCanvas();
				if(firstTime3==true)
				{
					background = Bitmap.createScaledBitmap(background, canvas.getWidth(), canvas.getHeight(), false);
					cardBack = Bitmap.createScaledBitmap(cardBack, canvas.getWidth()/14, canvas.getHeight()/6, false);	
					orgSizeWidth = canvas.getWidth()/14;
					orgSizeHeight = canvas.getHeight()/6;
					canvasX = canvas.getWidth();
					canvasY = canvas.getHeight();
					user1face = Bitmap.createScaledBitmap(user1face, orgSizeHeight, orgSizeHeight, false);
					user2face = Bitmap.createScaledBitmap(user2face, orgSizeHeight, orgSizeHeight, false);
					user1faceSelect = Bitmap.createScaledBitmap(user1faceSelect, orgSizeHeight, orgSizeHeight, false);
					user2faceSelect = Bitmap.createScaledBitmap(user2faceSelect, orgSizeHeight, orgSizeHeight, false);
					arrow1 = Bitmap.createScaledBitmap(arrow1, orgSizeWidth*5/6, orgSizeWidth/2, false);
					arrow2 = Bitmap.createScaledBitmap(arrow2, orgSizeWidth*5/6, orgSizeWidth/2, false);
					firstTime3=false;
				}
				canvas.drawBitmap(background, 0,0,null);
				switch(showMessage)
				{
					case "beginMessage":
						recUpdate();
						rectangle.set(0,400,recLength,550);
						canvas.drawRect(rectangle, recPaint);
						textPaint.setColor(Color.RED);
						canvas.drawText("Round Begins", canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
						SystemClock.sleep(20);
						break;
						
					case "shuffle":					
						a= new Random();
						canvas.drawBitmap(user2face, canvasX*4/5+a.nextInt(30),canvasY/2-user2face.getHeight()+a.nextInt(30),null); //printing user2 face
						canvas.drawBitmap(user1face, canvasX/10+a.nextInt(30),canvasY/2-user1face.getHeight()+a.nextInt(30),null); //printing user1 face
						textPaint.setColor(Color.rgb(a.nextInt(265), a.nextInt(265), a.nextInt(265)));
						shuffleX= randShuffle.nextInt(80)+canvas.getWidth()/2;
						shuffleY= randShuffle.nextInt(80)+canvas.getHeight()/2;			
						canvas.drawText("Shuffle", shuffleX, shuffleY, textPaint);
						if(shufcardsUpdate(canvas)==true)
						{
							for(int i=0; i<13; i++)
							{
								canvas.drawBitmap(cardBack, 0,0,null);
								canvas.drawBitmap(cardBack, x[i],y[i],null);
							}						
						}
						else
						{
							for(int i=0; i<13; i++)
							{
								canvas.drawBitmap(cardBack, x[i],y[i],null);
							}						
						}
						SystemClock.sleep(20);
						break;
						
					case "flipCards":
						canvas.drawBitmap(user2face, canvasX/3,canvasY/20,null);
						canvas.drawBitmap(user1face, canvasX/30,canvasY/14*8,null);
						for(int i=0;i<poker.user2card.size();i++)
						{
							canvas.drawBitmap(cardBack, canvasX/2+changSizeWidth/3*i,canvasY/20,null);
						}
						if(firstTime==true)
						{
							changSizeWidth=orgSizeWidth;
							changSizeHeight=orgSizeHeight;
							for(int i=0; i<13; i++)
							{
								//assign card bitmaps to users
								String mDrawableName1 = poker.user1card.get(i).toString();
								int imgID1 = getResources().getIdentifier(mDrawableName1 , "drawable", getPackageName());
								user1Card[i] =  BitmapFactory.decodeResource(getResources(),imgID1);									
								String mDrawableName2 = poker.user2card.get(i).toString();
								int imgID2 = getResources().getIdentifier(mDrawableName2 , "drawable", getPackageName());
								user2Card[i] =  BitmapFactory.decodeResource(getResources(),imgID2);	
								//go button assignment
								goButton = BitmapFactory.decodeResource(getResources(),R.drawable.gobutton);	
								goButton = Bitmap.createScaledBitmap(goButton, orgSizeWidth*3/2, orgSizeHeight/2+10, false);	
								skipButton = BitmapFactory.decodeResource(getResources(),R.drawable.skipbutton);	
								skipButton = Bitmap.createScaledBitmap(skipButton, orgSizeWidth*3/2, orgSizeHeight/2+10, false);	
							}	
							firstTime=false;
						}
						flipCards(canvas);
						for(int i=0; i<13; i++)	
						{	
							paintingCard[i] = Bitmap.createScaledBitmap(user1Card[i], changSizeWidth, changSizeHeight, false);
							if (checkIfDoneFlip==true)
							{
								canvas.drawBitmap(paintingCard[i], x[i],y[i],null);
								String starterString = new StringBuilder(userName).append(" starts").toString();
								textPaint.setColor(Color.CYAN);
								textPaint.setTextSize(100);	
								canvas.drawText(starterString, canvas.getWidth()/2, canvas.getHeight()/2, textPaint);					
							}
							else
								canvas.drawBitmap(cardBack, x[i],y[i],null);
						}	 
						SystemClock.sleep(40);
						break;
						
					case "playing":
						//setting everything up
						//canvas.drawBitmap(cardBack, canvasX/3+a.nextInt(80),canvasY/3+a.nextInt(80),null); //this is for testing canvas locked or not
						if(firstTime2==true)
						{
							textPaint.setTextSize(50);
							textPaint.setColor(Color.YELLOW);
							recPaint.setTextSize(80);
							for(int i=0; i<13; i++)	//assigncards bit maps to dynamic map variables
							{	
								paintingCard[i] = Bitmap.createScaledBitmap(user1Card[i], orgSizeWidth, orgSizeHeight, false);
								user2PlayingCards.add(Bitmap.createScaledBitmap(user2Card[i], orgSizeWidth, orgSizeHeight, false));//set bitmap of all cards of user2
								user1PlayingCards.add(paintingCard[i]);//set the cards that will be printed on the screenn where as paintingCard is static for ref
							}	
							firstTime2=false;
						}
						//draw who is giving card now
						if(playerIndex==1)
						{
							recPaint.setColor(Color.MAGENTA);
							canvas.drawBitmap(user1faceSelect, canvasX/30,canvasY/14*8,null); //printing user1 face
							canvas.drawBitmap(user2face, canvasX/3,canvasY/20,null);  //pringt user 2 face
							canvas.drawBitmap(goButton,canvasX-orgSizeWidth*4,canvasY-orgSizeHeight*2,null); //printing the gobutton
							canvas.drawBitmap(skipButton,canvasX-orgSizeWidth*2,canvasY-orgSizeHeight*2,null); //printing the skipbutton
							canvas.drawBitmap(arrow2,canvasX/10,canvasY*3/5,null); //printing the arrow
							canvas.drawText("Player "+Integer.toString(playerIndex), canvasX/7, canvasY*3/4, recPaint);
						}
						else
						{
							recPaint.setColor(Color.MAGENTA);
							canvas.drawBitmap(user1face, canvasX/30,canvasY/14*8,null); //printing user1 face
							canvas.drawBitmap(user2faceSelect, canvasX/3,canvasY/20,null);  //pringt user 2 face
							canvas.drawText("Player "+Integer.toString(playerIndex), canvasX/5, canvasY/4, recPaint);
							canvas.drawBitmap(arrow1,canvasX*11/35,canvasY/7,null); //printing the arrow
						}
						//draw cardback for user2
						for(int i=0;i<poker.user2card.size();i++)
						{
							canvas.drawBitmap(cardBack, canvasX/2+changSizeWidth/3*i,canvasY/20,null);
						}
						//printing all the cards for user1	
						for(int i=0; i<user1PlayingCards.size(); i++)	
							canvas.drawBitmap(user1PlayingCards.get(i), x[i],y[i],null);
						//printing touch cards info
						if (whoIsTouched !=-1 && whoIsTouched<user1PlayingCards.size())//when a card is touched or selected
						{
							if(selectedList.contains(whoIsTouched))//when it has been selected before
							{
								moveDownBit();
								selectedList.remove((Integer)whoIsTouched);
								Cards_currentGive.remove(poker.user1card.get(whoIsTouched));
							}
							else //when it has never been selected before
							{
								selectedList.add((Integer)whoIsTouched);
								moveUpBit();
								Cards_currentGive.add(poker.user1card.get(whoIsTouched));
								//canvas.drawText(poker.user1card.get(whoIsTouched), canvas.getWidth()/3, canvas.getHeight()/3, textPaint); //printing the number of the card for debugging
							}
							whoIsTouched =-1; //reset the value then can be reuse
						}
						//printing the deleted cards on the middle of the screen
						if(cardsGotRidPutInMiddleMap!=null)
						{
							for(int i=0; i<cardsGotRidPutInMiddleMap.size(); i++)	
								canvas.drawBitmap(cardsGotRidPutInMiddleMap.get(i), canvas.getWidth()/2-100+i*70, canvas.getHeight()/2-70,null);
						}
						//if pc or you give up then print out this
						if(NPC.randSz==999 && playerIndex==1)
							canvas.drawText("Player2 Computer gave up. You can give any card", canvas.getWidth()/2, canvas.getHeight()/3, textPaint);
						if(youQuit==true && playerIndex==2)
							canvas.drawText("You gave up. Player2 can give any card", canvas.getWidth()/2, canvas.getHeight()/3, textPaint);
						//else for debugging purpose
						//canvas.drawText(Boolean.toString(firtTimeGiveCards), 100, 100, textPaint);
						//canvas.drawText(Integer.toString(NPC.randSz), 100, 200, textPaint);
						//canvas.drawText(Boolean.toString(valid), canvas.getWidth()-30, 30, textPaint);
						break;
					case "gameOver":
						textPaint.setTextSize(200);	
						textPaint.setColor(Color.RED);
						canvas.drawText(whoWhinningString, canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
						break;
					case "nothing":
						break;				
				}	
				ourHolder.unlockCanvasAndPost(canvas);
			}		
		}	
		//flip card graphics update
		boolean checkIfDoneFlip = false;
		public void flipCards(Canvas canvas)
		{			
				if(checkIfDoneFlip==false)
				{
					if (changSizeWidth < orgSizeWidth/10)
						checkIfDoneFlip=true;
					else
					{
						changSizeWidth -= orgSizeWidth/10;
						checkIfDoneFlip=false;
					}
				}
				else
				{
					if(changSizeWidth < orgSizeWidth )
						changSizeWidth +=orgSizeWidth/10;
					else
						changSizeWidth=orgSizeWidth;
				}								
		}
		//cardMoveUpDownwhenSelected
		public void moveUpBit()
		{
			y[whoIsTouched]-=40;
			cards.play(soundCards, 1, 1, 0, 0,1);
		}
		public void moveDownBit()
		{
			y[whoIsTouched]+=40;
			cards.play(soundCards, 1, 1, 0, 0,1);
		}
		//shufcards update
		int i =0;
		int indexLength=0;		
		public boolean shufcardsUpdate(Canvas canvas)
		{
			if(i==13)
				return false;
			x[i]=x[i]+(canvas.getWidth()-cardBack.getWidth()-indexLength)/5;
			y[i]=y[i]+(canvas.getHeight()-cardBack.getHeight())/5;
			if( y[i]>(canvas.getHeight()-cardBack.getHeight())-50)
			{
				i++;
				indexLength=indexLength+cardBack.getWidth();
			}
			return true;
		}
		//begaining rec moving update
		public void recUpdate()
		{
			recLength = recLength+63;
		}	
		//extra: pause and resume
		public void pause()
		{
			gameSong.pause();
			isRunning = false;
			while(true)
			{
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			ourThread=null;
		}
		public void resume()
		{
			if(isRunning == false)
				gameSong.start();
			isRunning = true;
			ourThread=new Thread(this);
			ourThread.start();
		}
	}
	
//*****************************************other**************	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		canvasView.pause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		canvasView.resume();
	}
	
//*********************Dialog**********************
	//backButtonClick
	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("Note");
		alert.setMessage("Exit current game?");
		alert.setButton2("yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub	
				//go back to intro main page
				if(gaming.isAlive())
				{
					try {
						gaming.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Class ourclass=LaunchActivity.class;
				Intent backToLaunchPage = new Intent(oneSingleGamingActivity.this, ourclass);
				startActivity(backToLaunchPage);
				// stop the game
				finish();
				Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
			}
		});
		alert.setButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub		
				//cancel go back
				Toast.makeText(getApplicationContext(), "Game Resumed", Toast.LENGTH_SHORT).show();
			}
		});
		alert.show();		
	}

	public void gameOver()
	{
		whoWhinningString= new StringBuilder("Player ").append(Integer.toString(playerIndex)).append(" wins!").toString();
		SystemClock.sleep(2000);
		showMessage="gameOver";
		if(playerIndex==1)
			win.play(soundWin, 1, 1, 0, 0,1);
		else
			fail.play(soundFail, 1, 1, 0, 0,1);
		SystemClock.sleep(3000);
		Class ourclass=LaunchActivity.class;
		Intent backToLaunchPage = new Intent(oneSingleGamingActivity.this, ourclass);
		startActivity(backToLaunchPage);
		finish();
	}
	
}
	
