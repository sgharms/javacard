package com.stevengharms.javacard;

import java.util.Iterator;
import java.io.*;

public class JavaCardDeck extends Deck implements Serializable
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