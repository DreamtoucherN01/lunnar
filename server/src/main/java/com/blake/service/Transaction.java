package com.blake.service;

public  class Transaction {
	
	private static Transaction tran;
	private Resolve res = null;
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
		if(res == null && par == null){
			res = new Resolve();
			par = new Parser();
		}
	}
	
	public void DeInit(){
		res = null;
		par = null;
	}
	
	public synchronized Resolve getRes() {
		return res;
	}

	public synchronized void setRes(Resolve res) {
		this.res = res;
	}

	public Parser getPar() {
		return par;
	}

	public void setPar(Parser par) {
		this.par = par;
	}
}
