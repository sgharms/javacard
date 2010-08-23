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
		System.out.println("Succesfully added a card!");
	}
	
	public Card getCurrentCard(){
		return currentCard;
	}
	
	public  void nullifyCurrentCard(){
		currentCard = null;
	}
	
	public void deleteCurrentCard() throws NullPointerException{
		boolean result = sourceDeck.removeCard(currentCard);
		if (! result){
			throw new NullPointerException();
		}
		currentCard = null;
		// System.out.println("The ruslt was " + result);
	}
	
	public void setCurrentCard(Card c){
		currentCard = c;
	}
	
	public void goToPreviousCard(){
		currentCard = sourceDeck.cardPriorTo(currentCard);
	}
	public void goToSubsequentCard(){
		currentCard = sourceDeck.cardAfter(currentCard);
	}
	
	public boolean nextCardExists(){
		int currIndex = sourceDeck.indexOf(currentCard);
		boolean yesno = sourceDeck.exists(currIndex+1) ? true : false;
		System.out.println("I say " + yesno);
		return yesno;
	}
	
	public boolean priorCardExists(){
		int currIndex = sourceDeck.indexOf(currentCard);
		return (currIndex == 0) ? false : true;
	}
	
	public Deck getDeck(){
		return sourceDeck;
	}
	
}