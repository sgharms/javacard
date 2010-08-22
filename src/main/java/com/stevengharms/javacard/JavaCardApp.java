package com.stevengharms.javacard;

import java.util.regex.*;

public class JavaCardApp{
	private JavaCardDeck sourceDeck;
	private JavaCardDeck correctDeck;
	private JavaCardDeck incorrectDeck;
	private JavaCardView view;

	private int deckIndex = 0;
    private Card currentCard = null;
    private int currentIndex = 0;

		
	// Create an instance of the game
	public JavaCardApp(){
		// Model Objects
		sourceDeck    = new JavaCardDeck();
		correctDeck   = new JavaCardDeck();
		incorrectDeck = new JavaCardDeck();
		
		// View Object	
		view          = new JavaCardView(this);	
	}
	
	public static void main (String[] args)
	{
		JavaCardApp app = new JavaCardApp();
	}
	
	public void addNewCard(String[] qa) throws IllegalArgumentException{
		boolean result = false;
		JavaCard jc = null;
		
		try{
			jc = new JavaCard(qa);
			result = sourceDeck.addCard(jc);			
		}
		catch (Exception e){
			System.err.println("Exception Handled:"+e);
			view.giveFocusToEmptyTextArea();
		}
		
		if (!result){
			throw new IllegalArgumentException();
		}
		
		this.setCurrentCard(jc);
		this.currentIndex = sourceDeck.indexOf(jc);
	}
	
	public Card getCurrentCard(){
		return currentCard;
	}
	
	public void setCurrentCard(Card c){
		currentCard = c;
	}
	
	public void goToPreviousCard(){
		sourceDeck.cardPriorTo(currentCard);
	}
	public void goToSubsequentCard(){
		Card c = sourceDeck.cardAfter(currentCard);
		String p = "^Placeholder.*";
		if (c.getFront().toString().matches(p) && 
			c.getBack().toString().matches(p)){
				view.renderNew = true;
		}
	}
	
}