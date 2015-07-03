package com.example.hong4poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.os.SystemClock;

public class NPC {

	public int[] indexes;
	public ArrayList<String>cardsNPCgive;
	public static int randSz=0;
	public void pickCards(boolean firsTime, ArrayList<String>cardsBefore, Poker poker, int playerIndex)
	{
		ArrayList<String>targetCards=new ArrayList<String>();
		cardsNPCgive=new ArrayList<String>();
		int size;
		Random randSize = new Random();
		Random randCard = new Random();
		boolean valid =false;
		//Get the existing cards of the current player
		switch(playerIndex)
		{
			case 2:
					for(int i=0;i<poker.user2card.size();i++)
						targetCards.add(poker.user2card.get(i));
					break;
			case 3:
					for(int i=0;i<poker.user3card.size();i++)
						targetCards.add(poker.user3card.get(i));
					break;
			case 4:
					for(int i=0;i<poker.user4card.size();i++)
						targetCards.add(poker.user4card.get(i));
					break;		
		}
		
		//random choose cards and valid
		
			if(firsTime==true)
			{
				long tStart = System.currentTimeMillis();
				long tEnd;
				long tDelta=0;
				while(valid==false)
				{
					cardsNPCgive.clear();
					//define a temp cards for processing. a new instance of TargetCard
					ArrayList<String>tempCards=new ArrayList<String>();
					for(int i=0;i<targetCards.size();i++)
						tempCards.add(targetCards.get(i));
					//getting the size
					if(tDelta>4000) //if the time is >4s then force to choose size as 1
						size=1;
					else 
					{
						if(targetCards.size()==1)  //if cards player has is only one car then size=1
							size=1;
						else if(targetCards.size()<13)  //else randomly select size>1
							size=randSize.nextInt(targetCards.size()-1)+2;
						else
							size=randSize.nextInt(11)+2;
					}							
					randSz=size;
					//validating
					if(targetCards.size()<size) //if player card size is smaller than randomly picked size, then valid no matter what
						valid=false;
					else
					{
						//if the card is 1 card
						if(size==1)
						{
							//if two cards left and want to pick up as first time, then make 3 to seal up.
							if(targetCards.contains("h3") && targetCards.size()==2)
								cardsNPCgive.add("h3");
							else if(targetCards.contains("s3") && targetCards.size()==2)
								cardsNPCgive.add("s3");
							else if(targetCards.contains("d3") && targetCards.size()==2)
								cardsNPCgive.add("d3");
							else if(targetCards.contains("c3") && targetCards.size()==2)
								cardsNPCgive.add("c3");
							else
							{  //else pick th smallest to leave bigger ones later
								cardsNPCgive.add(targetCards.get(smallestCard(playerIndex,targetCards,null)));
								randSz=1111;
							}
						}
						else
						{
							for(int i=0; i<size; i++)
							{
								//select one card
								String randcardPicked =tempCards.get(randCard.nextInt(tempCards.size()));
								//put in the temp list for verification later
								cardsNPCgive.add(randcardPicked);	
								//remove this card from it's list to ensure don't select the same card
								tempCards.remove(randcardPicked);
							}	
						}
						if(size>1 && tempCards.size() > 1)
						{  //if left is one or more, never give multiple 3 bc it's waste
							if(cardsNPCgive.contains("h3") || cardsNPCgive.contains("s3") || cardsNPCgive.contains("d3") || cardsNPCgive.contains("c3") || cardsNPCgive.contains("h2") || cardsNPCgive.contains("s2") || cardsNPCgive.contains("d2") || cardsNPCgive.contains("c2")) 
								valid=false;
							else
								valid=GameRule.validateCards(cardsNPCgive, cardsBefore, true);	
						}
						else
							valid=GameRule.validateCards(cardsNPCgive, cardsBefore, true);	
						tEnd = System.currentTimeMillis();
						tDelta = tEnd - tStart;
						if(tDelta>5000)
						{
							randSz=999;                                                                                                                                                                                                                    
							break; //means time out NPC skips and gives up the right
						}
					}
				}
			}
			else
			{
				size =cardsBefore.size();
				long tStart = System.currentTimeMillis();
				long tEnd;
				long tDelta;
				randSz=size;
				while(valid==false)
				{
					cardsNPCgive.clear();
					//define a temp cards for processing. a new instance of TargetCard
					ArrayList<String>tempCards=new ArrayList<String>();
					for(int i=0;i<targetCards.size();i++)
						tempCards.add(targetCards.get(i));
					//if player card size is smaller than before cards size then false no matter what
					if(targetCards.size()<cardsBefore.size())
					{
						valid=false;
						randSz=999;
						break;
					}
					else
					{	//if size is 1 as picked
						if(size==1)
						{	//pick the smallest to beat the before single card
							int index=smallestCard(playerIndex,targetCards,cardsBefore.get(0));
							if(index==-1 || index == -2)
							{	//if no card from player is bigger than the before single card
								randSz=index;
								break;
							}
							else
							{	//if the single bigger card picked
								cardsNPCgive.add(targetCards.get(index));
								randSz=123;
							}
						}
						else
						{
							for(int i=0; i<size; i++)
							{
								//select one card
								String randcardPicked =tempCards.get(randCard.nextInt(tempCards.size()));
								//put in the temp list for verification later
								cardsNPCgive.add(randcardPicked);	
								//remove this card from it's list to ensure don't select the same card
								tempCards.remove(randcardPicked);
							}		
						}
						if(size>1 && tempCards.size() > 1)
						{  //if left is one or more, never give multiple 3 bc it's waste
							if(cardsNPCgive.contains("h3") || cardsNPCgive.contains("s3") || cardsNPCgive.contains("d3") || cardsNPCgive.contains("c3"))
								valid=false;
							else
								valid=GameRule.validateCards(cardsNPCgive, cardsBefore, false);	
						}
						else
							valid=GameRule.validateCards(cardsNPCgive, cardsBefore, false);	
						tEnd = System.currentTimeMillis();
						tDelta = tEnd - tStart;
						if(tDelta>5000)	
						{
							randSz=999;
							break; //means time out NPC skips and gives up the right
						}
					}
				}
				
			}	
			if(valid==true)
			{	//if the result is true do the work
				indexes = new int[cardsNPCgive.size()];
				for(int i=0; i< cardsNPCgive.size();i++)
					indexes[i]=targetCards.indexOf(cardsNPCgive.get(i));
			}
			else
			{
				cardsNPCgive=null; //return cardNPCgive
			}
	}
	
	
	//smallest car
	public int smallestCard(int playerIndex, ArrayList<String> playerCards, String beforeGiveCard)
	{
		//********no index used for diff player now is just player2
		ArrayList<Integer> temp=new ArrayList<Integer>();
		ArrayList<Integer> ConvertedCards=new ArrayList<Integer>();
		
		for (int i=0; i< playerCards.size();i++)
		{
			int card = Integer.parseInt(playerCards.get(i).substring(1));
			if(card==1 || card==2 || card==3) 
				card+=20;
			ConvertedCards.add(card);	
		}
		for (int i=0; i< playerCards.size();i++)
		{
			int card = Integer.parseInt(playerCards.get(i).substring(1));
			if(card==1 || card==2 || card==3)
				card+=20;
			temp.add(card);	
		}

		int CardNumber;
		int indexToTheStringCards = -2;
		if(beforeGiveCard==null)
		{ //if it's fist time
			CardNumber=Collections.min(ConvertedCards);
			indexToTheStringCards = ConvertedCards.indexOf(CardNumber);
		}
		else
		{//if it's comparing with the single before card
			int beforeCardNumber = Integer.parseInt(beforeGiveCard.substring(1));
			if(beforeCardNumber == 1 || beforeCardNumber == 2 || beforeCardNumber == 3 )
				beforeCardNumber += 20; 
	
			boolean findIt = false;
			while(findIt==false)
			{
				if(temp.size()==0)
				{
					CardNumber=-1;
					indexToTheStringCards=-1;
					findIt=true;
				}
				else
				{
					CardNumber=Collections.min(temp);
					if(CardNumber > beforeCardNumber)
					{			
						indexToTheStringCards = ConvertedCards.indexOf(CardNumber);
						findIt=true;		
					}
					else
						temp.remove((Object)CardNumber);
				}				
			}
		}	
				
		return indexToTheStringCards;
	}
		
}
