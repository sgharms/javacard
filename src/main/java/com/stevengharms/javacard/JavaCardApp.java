package com.stevengharms.javacard;

public class JavaCardApp{
	JavaCardDeck sourceDeck;
	JavaCardDeck correctDeck;
	JavaCardDeck incorrectDeck;
	
	public static  int magicNumber = 1;
	public static String magicString = "Razzle";
	
	// Create an instance of the game
	public JavaCardApp(){
		sourceDeck    = new JavaCardDeck();
		correctDeck   = new JavaCardDeck();
		incorrectDeck = new JavaCardDeck();		
	}
	
	public static void main (String[] args)
	{
		JavaCardApp app = new JavaCardApp();
		JavaCardView jcv = new JavaCardView();
	}
	

}