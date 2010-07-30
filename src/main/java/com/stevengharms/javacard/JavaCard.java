package com.stevengharms.javacard;

public class JavaCard{
	JavaCardDeck sourceDeck;
	JavaCardDeck correctDeck;
	JavaCardDeck incorrectDeck;
	
	static public int magicNumber = 1;
	static public String magicString = "Razzle";
	
	// Create an instance of the game
	public JavaCard()
	{
		sourceDeck    = new JavaCardDeck();
		correctDeck   = new JavaCardDeck();
		incorrectDeck = new JavaCardDeck();
		
	}
	
	public static void main (String[] args)
	{
		JavaCard j = new JavaCard();
		System.out.println("Hello, JavaCard!");
	}
	

}