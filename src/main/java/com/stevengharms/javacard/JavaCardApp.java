package com.stevengharms.javacard;

public class JavaCardApp{
	private JavaCardDeck sourceDeck;
	private JavaCardDeck correctDeck;
	private JavaCardDeck incorrectDeck;
	private JavaCardView view;
		
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
	
	public void razzle(){
		System.out.println("razzle");
	}
	
	public void addNewCard(String[] qa){
		sourceDeck.addCard(new JavaCard(qa));
	}
}