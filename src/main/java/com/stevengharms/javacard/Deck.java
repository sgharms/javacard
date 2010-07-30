package com.stevengharms.javacard;

public class Deck
{
	public Deck()
	{
		this.initializeDeck();
		this.shuffle();
		this.closeDeck();
	}
	
	// Open and close a deck
	void initializeDeck()
	{
		System.out.println("Initializing " + this.toString() );
	}
	
	void closeDeck()
	{
		System.out.println("Closing " + this.toString() );		
	}
	
	// State maintenance
	void shuffle()
	{
		System.out.println("Shuffling " + this.toString() );		
	}
	
}