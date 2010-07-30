package com.stevengharms.javacard;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Iterator;

public class TestJavaCardDeck extends TestCase {
	JavaCardDeck j;
	
	static final int TEST_SLOTS = 5;
	
	public void setUp(){
		j = new JavaCardDeck();
	}

	public void tearDown(){
	}

	public void testDeckCreation() throws Exception {
		assertNotNull("Make sure basic JavaCardDeck instantiates", j);
	}
	
	public void testCardAdd() throws Exception {
		JavaCard jc = new JavaCard("Test", "Card");
		j.addCard(jc);
		assertEquals("These should be equal", jc, j.get(0));
	}
	
	public void testSize() throws Exception {
		fillDeck();		
		assertEquals(TestJavaCardDeck.TEST_SLOTS,j.size());		
	}
	
	/*
	  This test is essentially worthless in its present state.  The reason is that the toArray() method that under-
	  pins the getCards() method inherently creates something new on the heap with a reference.  Therefore this test
	  only saves you from the egregious error of getting the same refVar.  This is probably worth testing for, but
	  it's not a very good test of whether the state of the shuffle has changed.
	
	  OTOH, it's really hard to tell if you've shuffled properly because there is some probability that pre-shuffle
	  and post-shuffle SHOULD be the same thing.  In a small population it's entirely possible that there would be
	  no discernible difference and this test would fail -- improperly! 
	
	  There are suggestions on using statistical inference, etc....but man, that's way more than this app needs
   */

	public void testShuffle() throws Exception {
		fillDeck();
		Card[] preShuffle = j.getCards();
		j.shuffle();                      
		Card[] postShuffle = j.getCards();		
		assertNotSame("Pre- and Post-shuffle decks should differ -- at the very least in terms of reference variable!",
			preShuffle, postShuffle);
	}
	
	private void fillDeck(){
		int testSlots = 5;
		
		for (int i=0; i<TestJavaCardDeck.TEST_SLOTS; i++)
		{
			j.addCard( new JavaCard(("Test" + i), ("Card" +i)) );
		}
	}
}