package com.stevengharms.javacard;

import junit.framework.TestCase;

public class TestJavaCard extends TestCase {
	public void testMagicNumber() throws Exception {
		assertEquals(1,JavaCard.magicNumber);
	}
	public void testMagicSttring() throws Exception{
		assertEquals("Razzle", JavaCard.magicString);
	}
}