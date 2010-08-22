package com.stevengharms.javacard;

import java.io.*;

public class JavaCard extends Card implements Serializable{
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