package com.blake.service;

public  class Transaction {
	
	private static Transaction tran;
	private Parser par = null;
	
	public static Transaction getInst(){
		if(tran==null){
			synchronized(Transaction.class){
				if(tran==null)
					tran = new Transaction();
			}
		}
		return tran;
	}
	
	public void init(){
		if( par == null){
			par = new Parser();
		}
	}
	
	public void DeInit(){
		par = null;
	}

	public Parser getPar() {
		return par;
	}

	public void setPar(Parser par) {
		this.par = par;
	}
}
