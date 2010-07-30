package com.stevengharms.javacard;

import junit.framework.TestCase;

public class TestJavaCard extends TestCase {
	JavaCard j;

	protected void setUp(){
		j = new JavaCard();
	}
	
	protected void tearDown(){
		j = null;
	}
	
	public void testFrontBackBasicInit() throws Exception{
		assertTrue(j.getFront().equals("Placeholder front"));
		assertTrue(j.getBack().equals("Placeholder back"));
	}
	
	public void testTwoObjectConstructor() throws Exception {
		j = new JavaCard("My front", "My back");
		assertTrue(j.getFront().equals("My front"));
		assertTrue(j.getBack().equals("My back"));
	}
	
}