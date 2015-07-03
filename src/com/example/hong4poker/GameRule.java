package com.example.hong4poker;

import java.util.ArrayList;
import java.util.Collections;


public class GameRule {
	
	public static boolean validateCards(ArrayList<String>  Cards_youGive, ArrayList<String> cards_beforeGive, boolean firstTime)
	{
		ArrayList<Integer> cardsBeforeGive = new ArrayList<Integer>();
		ArrayList<Integer> cardsYouGive = new ArrayList<Integer>();
		cardsBeforeGive = conversion(cards_beforeGive);
		cardsYouGive=conversion(Cards_youGive);
		boolean valid = false;
		
		//if it's the first-time give cards, only checks it's format
		if(firstTime==true)
		{
			switch(cardsYouGive.size())
			{
				case 1:
						valid=true;
						break;
				case 2:
						if(cardsYouGive.get(0) == cardsYouGive.get(1)) 
							valid=true; //if the same then it's valid
						break;
				case 3:
						//when it is 3 the same
						if(cardsYouGive.get(0)==cardsYouGive.get(1) && cardsYouGive.get(0) == cardsYouGive.get(2))
								valid=true;
						//if it's string cards
						else
						{  
							if (checkIfStringCards(cardsYouGive)==true)
								valid=true;							
						}							
						break;
				case 4:
						//when it is 4 the same
						if(cardsYouGive.get(0)==cardsYouGive.get(1) && cardsYouGive.get(0) == cardsYouGive.get(2) && cardsYouGive.get(0) == cardsYouGive.get(3))
								valid=true;
						//if it's string cards
						else
						{  
							if (checkIfStringCards(cardsYouGive)==true)
								valid=true;								
						}				
						break;
						
				case 5:
						//5 cards must be string cards	
						if (checkIfStringCards(cardsYouGive)==true)
							valid=true;		
						break;
						
				case 6:
						//if it's single string cards
						if (checkIfStringCards(cardsYouGive)==true)
								valid=true;													
						//if it's 3 double string cards
						else
						{
							if (checkIfMultiStringCards(cardsYouGive,2)==true)
								valid=true;									
						}
						break;
						
				case 7:
						//7 cards must be string cards	
						if (checkIfStringCards(cardsYouGive)==true)
							valid=true;		
						break;
					
				case 8:
						//if it's single string cards
						if (checkIfStringCards(cardsYouGive)==true)
								valid=true;							
						//if it's 4 double string cards
						else
						{
							if (checkIfMultiStringCards(cardsYouGive,2)==true)
								valid=true;									
						}
						break;
						
				case 9:
						//if it's single string cards
						if (checkIfStringCards(cardsYouGive)==true)
								valid=true;							
						//if it's 3 triple string cards
						else
						{
							if (checkIfMultiStringCards(cardsYouGive,3)==true)
								valid=true;									
						}
						break;
						
				case 10:
						//if it's single string cards
						if (checkIfStringCards(cardsYouGive)==true)
								valid=true;													
						//if it's 5 double string cards
						else
						{
							if (checkIfMultiStringCards(cardsYouGive,2)==true)
								valid=true;									
						}
						break;
					
				case 12:
						//6 double
						if (checkIfMultiStringCards(cardsYouGive,2)==true)
								valid=true;							
						//if 4 triple string cards			
						if (checkIfMultiStringCards(cardsYouGive,3)==true)
								valid=true;									
						//else would be 3 quadruple string cards
						if (checkIfMultiStringCards(cardsYouGive,4)==true)
								valid=true;									
						break;	
				default:
						valid=false;
						break;
			}
		}
		//if it's after some one gives cards, check length first and check if the format matches
		else
		{
			//if size not the same then not valid
			if(cardsBeforeGive.size() != cardsYouGive.size())
				valid = false;
			//if size the same
			else
			{
				switch(cardsBeforeGive.size())
				{
					case 1:
							valid=singleComparison(cardsYouGive.get(0),cardsBeforeGive.get(0));
							break;
					case 2:
							if(cardsYouGive.get(0)!=cardsYouGive.get(1)) //if your two cards not the same then not valid
								valid=false;
							else
								valid=singleComparison(cardsYouGive.get(0),cardsBeforeGive.get(0));//pick one from each and compare since the same
							break;
					case 3:
							//when it is 3 the same
							if(cardsBeforeGive.get(0)==cardsBeforeGive.get(1) && cardsBeforeGive.get(0) == cardsBeforeGive.get(2))//3 the same cards comparsion
							{
								if(cardsYouGive.get(0)==cardsYouGive.get(1) && cardsYouGive.get(0) == cardsYouGive.get(2))
									valid=singleComparison(cardsYouGive.get(0),cardsBeforeGive.get(0));
								else
									valid=false;
							}
							//if it's string cards
							else
							{   boolean isStringCards = checkIfStringCards(cardsYouGive);
								
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);
									
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}							
							break;
					case 4:
							//when it is 4 the same
							if(cardsBeforeGive.get(0)==cardsBeforeGive.get(1) && cardsBeforeGive.get(0) == cardsBeforeGive.get(2) && cardsBeforeGive.get(0) == cardsBeforeGive.get(3))//4 the same cards comparsion
							{
								if(cardsYouGive.get(0)==cardsYouGive.get(1) && cardsYouGive.get(0) == cardsYouGive.get(2) && cardsYouGive.get(0) == cardsYouGive.get(3))
									valid=singleComparison(cardsYouGive.get(0),cardsBeforeGive.get(0));
								else
									valid=false;
							}
							//if it's string cards
							else
							{   boolean isStringCards = checkIfStringCards(cardsYouGive);
								
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}				
							break;
							
					case 5:
							//5 cards must be string cards	
							if (checkIfStringCards(cardsYouGive)==true)
							{
								int min_you= Collections.min(cardsYouGive);
								int min_before= Collections.min(cardsBeforeGive);
								System.out.println(min_you);
								System.out.println(min_before);							
								valid=singleComparison(min_you,min_before);
							}
							else
								valid=false;		
							break;
							
					case 6:
							//if it's single string cards
							if (checkIfStringCards(cardsBeforeGive)==true)
							{ 
								boolean isStringCards = checkIfStringCards(cardsYouGive);//your card is string as well or not
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}	
							//if it's 3 double string cards
							else
							{
								boolean isDoubleStringCards = checkIfMultiStringCards(cardsYouGive,2);
								if (isDoubleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							break;
							
					case 7:
							//7 cards must be string cards	
							if (checkIfStringCards(cardsYouGive)==true)
							{
								int min_you= Collections.min(cardsYouGive);
								int min_before= Collections.min(cardsBeforeGive);
								System.out.println(min_you);
								System.out.println(min_before);							
								valid=singleComparison(min_you,min_before);
							}
							else
								valid=false;		
							break;
						
					case 8:
							//if it's single string cards
							if (checkIfStringCards(cardsBeforeGive)==true)
							{ 
								boolean isStringCards = checkIfStringCards(cardsYouGive);//your card is string as well or not
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}	
							//if it's 4 double string cards
							else
							{
								boolean isDoubleStringCards = checkIfMultiStringCards(cardsYouGive,2);
								if (isDoubleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							break;
							
					case 9:
							//if it's single string cards
							if (checkIfStringCards(cardsBeforeGive)==true)
							{ 
								boolean isStringCards = checkIfStringCards(cardsYouGive);//your card is string as well or not
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}	
							//if it's 3 triple string cards
							else
							{
								boolean isTripleStringCards = checkIfMultiStringCards(cardsYouGive,3);
								if (isTripleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							break;
							
					case 10:
							//if it's single string cards
							if (checkIfStringCards(cardsBeforeGive)==true)
							{ 
								boolean isStringCards = checkIfStringCards(cardsYouGive);//your card is string as well or not
								if (isStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}	
							//if it's 5 double string cards
							else
							{
								boolean isDoubleStringCards = checkIfMultiStringCards(cardsYouGive,2);
								if (isDoubleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							break;
						
					case 12:
							//6 double
							if (checkIfMultiStringCards(cardsBeforeGive,2)==true)
							{ 
								boolean isDoubleStringCards = checkIfMultiStringCards(cardsYouGive,2);
								if (isDoubleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;							
							}	
							//if 4 triple string cards
							else if(checkIfMultiStringCards(cardsBeforeGive,3)==true)
							{
								boolean isTripleStringCards = checkIfMultiStringCards(cardsYouGive,3);
								if (isTripleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							//else would be 3 quadruple string cards
							else
							{
								boolean isQuadrupleStringCards = checkIfMultiStringCards(cardsYouGive,4);
								if (isQuadrupleStringCards==true)
								{
									int min_you= Collections.min(cardsYouGive);
									int min_before= Collections.min(cardsBeforeGive);
									System.out.println(min_you);
									System.out.println(min_before);							
									valid=singleComparison(min_you,min_before);
								}
								else
									valid=false;									
							}
							break;	
					default:
							valid=false;
							break;
				}			
			}				
		}
		return valid;
	}
	
	//check if the cards are string cards
	public static boolean checkIfStringCards(ArrayList<Integer> cardsYouGive) {
		//sort ascending
		Collections.sort(cardsYouGive);
		//if cards contain 1,2,3 then not string cards
		if (cardsYouGive.contains(1) || cardsYouGive.contains(2) || cardsYouGive.contains(3))
			return false;
		else
		{
			//check if cards are increment by 1, if not, then not string cards
			for(int i=1; i<cardsYouGive.size();i++)
			{	
				if ((cardsYouGive.get(0)+i)!=cardsYouGive.get(i))
					return false;				
			}	
			return true;
		}
	}
	
	public static boolean checkIfMultiStringCards(ArrayList<Integer> cardsYouGive, int howManyEach)
	{
		//sort ascending
		Collections.sort(cardsYouGive);
		//if cards contain 1,2,3 then not string cards
		if (cardsYouGive.contains(1) || cardsYouGive.contains(2) || cardsYouGive.contains(3))
			return false;
		else
		{
			//check if cards are increment by 1 for multiple cards, if not, then not mutlistring cards
			int difference = 0;//between cards
			int count=1; //use to set how many times difference increment
			for(int i=0; i<cardsYouGive.size();i++)
			{	
				
				if ((cardsYouGive.get(0)+difference)!=cardsYouGive.get(i)) //0,0,1,1,2,2,3,3
					return false;	
				else
				{
					if (count==howManyEach)						
					{
						difference++;
						count=1;					
					}
					else
						count++;			
				}						
			}	
			return true;
		}	
	}

	//cards conversion from string to integer
	public static ArrayList<Integer> conversion (ArrayList<String> cards)
	{
		ArrayList<Integer> convertedValue = new ArrayList<Integer>();
		for (String oneCard: cards)
		{
			convertedValue.add(Integer.parseInt(oneCard.substring(1)));	
		}
		return convertedValue;
	}

	//single card comparison
	public static boolean singleComparison(int cardA, int cardB)
	{
		if(cardA == 1 || cardA==2 || cardA==3)
			cardA+=20;
		if(cardB == 1 || cardB==2 || cardB==3)
			cardB+=20;
		if(cardA>cardB)
			return true;
		else
			return false;
	}
}
