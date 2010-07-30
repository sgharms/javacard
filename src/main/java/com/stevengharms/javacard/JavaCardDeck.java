package com.stevengharms.javacard;

import java.util.Iterator;

public class JavaCardDeck extends Deck 
{
	public JavaCardDeck()
	{
		super();
	}
	
	public void print()
	{
		Iterator itr = cards.iterator();
		while (itr.hasNext())
		{
			System.out.println(itr.next().toString());
		}
	}
}