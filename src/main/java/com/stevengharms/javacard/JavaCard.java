package com.stevengharms.javacard;

public class JavaCard extends Card{
	public JavaCard(Object f, Object b){
		super(f,b);
	}
	public JavaCard(){
		super();
	}
	public JavaCard(Object[] o) throws IllegalArgumentException{
		super(o);
	}
}