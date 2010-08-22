package com.stevengharms.javacard;

public class Card{
	private Object cardFront;
	private Object cardBack;
	
	/* Initializers */
	public Card(){
		this.cardFront = "Placeholder front";
		this.cardBack  = "Placeholder back";
	}
	
	public Card(Object f, Object b) throws IllegalArgumentException{
		this.setFront(f);
		this.setBack(b);
	}
	
	public Card(Object[] qa) throws IllegalArgumentException{
		System.out.println("passed through here");
		this.setFront(qa[0]);
		this.setBack(qa[1]);	
	}

	/* Getters and Setters */
	
	public Object getFront(){return this.cardFront;}
	
	public void   setFront(Object f) throws IllegalArgumentException
	{
		if ((f == "") ||
			f.equals(null) || 
			((String)f).isEmpty() ){
			throw new IllegalArgumentException("The field cannot be null");
		}
		
		this.cardFront = f;
		
	}

	public Object getBack(){return this.cardBack;}
	public void   setBack(Object b) throws IllegalArgumentException{
		if ((b == "") ||
			b.equals(null) || 
			((String)b).isEmpty() ){
			throw new IllegalArgumentException("The field cannot be null");
		}		
		this.cardBack = b;
	}
	
	public String toString(){
		return String.format("%s has [%s] and [%s]\n", getClass().getName(), this.getFront(), this.getBack());
	}
}