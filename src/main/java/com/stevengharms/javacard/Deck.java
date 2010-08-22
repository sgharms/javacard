package com.stevengharms.javacard;

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
	ArrayList<Card> cards;
	
	public Deck()
	{
		cards = new ArrayList<Card>();
	}
	
	// Open and close a deck
	void initializeDeck()
	{
	}
	
	void closeDeck()
	{
		// System.out.println("Closing " + this.toString() );		
	}
	
	// State maintenance
	void shuffle()
	{
		Collections.shuffle(cards);
	}
	
	void addCard(Card c){
		try
		{
			cards.add(c);
			System.out.println("Just added the card " + c);	
			System.out.println("The deck looks like:" + cards);		
		}
		catch (Exception e)
		{
			System.out.println("Was not able to add the card "+ c + " because "+ e);
		}
	}
	
	Card get(int i){
		return (Card)cards.get(i);
	}
	
	public int size(){
		return cards.size();
	}
	
	public Card[] getCards(){
		/*
		This is pretty funky syntax, if you ask me
		http://download-llnw.oracle.com/javase/tutorial/collections/interfaces/collection.html
		
		For example, suppose that c is a Collection. The following snippet
        dumps the contents of c into a newly allocated array of Object whose
        length is identical to the number of elements in c.
		
		 Object[] a = c.toArray(); Suppose that c is known to contain only
        strings (perhaps because c is of type Collection<String>). The
        following snippet dumps the contents of c into a newly allocated array
        of String whose length is identical to the number of elements in c.
        String[] a = c.toArray(new String[0]);
    
		*/
		return cards.toArray(new Card[0]);
		
	}
	
}