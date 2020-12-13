package gui;

import java.util.Observable;

public class Counter extends Observable {
	  private int value;
	  public void increment(){ 
		  value++;
		  setChanged();
		  notifyObservers();
	  }
	  public void decrement(){ 
		  value--;
		  setChanged();
		  notifyObservers();
	  }
	  public int getValue(){ return value;}
	}
