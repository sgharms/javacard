package com.stevengharms.javacard;

public class Card{
	private Object cardFront;
	private Object cardBack;
	
	/* Initializers */
	public Card(){
		this.cardFront = "Placeholder front";
		this.cardBack  = "Placeholder back";
	}
	
	public Card(Object f, Object b){
		this.cardFront = f;
		this.cardBack  = b;
	}

	/* Getters and Setters */
	
	public Object getFront(){return this.cardFront;}
	public void   setFront(Object f){this.cardFront = f;}

	public Object getBack(){return this.cardBack;}
	public void   setBack(Object b){this.cardBack = b;}
}