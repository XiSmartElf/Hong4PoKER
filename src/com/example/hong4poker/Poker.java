package com.example.hong4poker;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Poker {

	public static final String[] pokerCards =
		   {
				"s1","s2","s3","s4","s5","s6","s7","s8","s9","s10","s11","s12","s13", //Spades
				"h1","h2","h3","h4","h5","h6","h7","h8","h9","h10","h11","h12","h13", //Heart
				"d1","d2","d3","d4","d5","d6","d7","d8","d9","d10","d11","d12","d13", //Diamonds
				"c1","c2","c3","c4","c5","c6","c7","c8","c9","c10","c11","c12","c13"  //Clubs
			};

	public List<String> user1card=null;
	public List<String> user2card=null;
	public List<String> user3card=null;
	public List<String> user4card=null;
	
	//shuffle it when new game begins
	public int Shuffle_Distribute(int numberOfPlayers)
	{
		//put 52 cards in a list and shuffle them
		ArrayList<String> pokerToUse = new ArrayList<String>(Arrays.asList(pokerCards));
		Collections.shuffle(pokerToUse);
		//based on how many people play
		int whoStarts=0;
		switch(numberOfPlayers)
		{
			
			case 2:
				user1card = new ArrayList<String>();
				user2card = new ArrayList<String>();
				//give 13 cards to each player
				for(int i=0;i<13;i++)
				{
					//give a random card to the first player
					int idx1 = new Random().nextInt(pokerToUse.size());
					String random1 = pokerToUse.get(idx1);
					pokerToUse.remove(idx1);
					user1card.add(random1);
					//give a random card to the second player
					int idx2 = new Random().nextInt(pokerToUse.size());
					String random2 = pokerToUse.get(idx2);
					pokerToUse.remove(idx2);
					user2card.add(random2);
				}	
				boolean findWhoStarts = false;
				int cardNumber=4;
				while(findWhoStarts==false)
				{
					String card = new StringBuilder("h").append(Integer.toString(cardNumber)).toString();
					if(user1card.contains(card))
					{
						findWhoStarts=true;
						whoStarts=1;
					}
					else if(user2card.contains(card))
					{
						findWhoStarts=true;
						whoStarts=2;
					}
					else
					{
						findWhoStarts=false;
						cardNumber++;
						//it's rarely possible no one has a heart car. If it happesn, re shuffle 
						if (cardNumber==14)
						{	
							whoStarts=99;//error needs reshuffle
							break;
						}
					}					
				}						
				break;				
			default:
				whoStarts=0;
				break;
		
		}	
		return whoStarts;
	}
	
	//Get rid of the cards you give from all of your cards and check if you have no cards then
	public boolean getRideOfcards(ArrayList<String>  Cards_youGive, int playerIndex)
	{
		String whichPlayer= new StringBuilder("user").append(Integer.toString(playerIndex)).toString();
		boolean cardsLeft=true;
		switch(whichPlayer)
		{
			case "user1":
				user1card.removeAll(Cards_youGive);
				if (user1card.size()==0)
					cardsLeft=false;
				break;
			case "user2":
				user2card.removeAll(Cards_youGive);
				if (user2card.size()==0)
					cardsLeft=false;
				break;
			case "user3":
				user3card.removeAll(Cards_youGive);
				if (user3card.size()==0)
					cardsLeft=false;
				break;
			case "user4":
				user4card.removeAll(Arrays.asList(Cards_youGive));
				if (user3card.size()==0)
					cardsLeft=false;
				break;		
		}		
		return cardsLeft;		
	}

	//Who is next player to give cards
	public int whoIsNext(int whoIsNow, int numberOfPlayers)
	{ //1,2,3,4
		int whoIsNextPlayer;
		if (whoIsNow==numberOfPlayers)
			whoIsNextPlayer=1;
		else
			whoIsNextPlayer=whoIsNow+1;
		return whoIsNextPlayer;		
	}	
	

}
