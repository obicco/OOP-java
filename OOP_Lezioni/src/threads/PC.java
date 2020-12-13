package threads;

import java.util.ArrayList;

public class PC {
	
	static class FIFO<T> {
		private ArrayList<T> elementi = new ArrayList<>(3);
		
		public synchronized void inserisci(T elemento) {
			elementi.add(elemento);
			notifyAll();
		}
		
		public synchronized T preleva() throws InterruptedException {
			while(elementi.size() == 0) {
				wait();
			}
			T elemento =  elementi.get(0);
			elementi.remove(0);
			return elemento;
		}
	}

	public static void main(String[] args) {
		
		FIFO<Integer> buffer = new FIFO<>();
		
		Runnable produttore = () -> {
			try {
				while(true) {
					int valore = (int)(Math.random()*100);
					buffer.inserisci(valore);
					System.out.println("Prodotto: " + valore);
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
				System.err.println("Produttore interrotto!");
			}
		};
		
		Runnable consumatore = () -> {
			try {
				while(true) { // Busy Form of Waiting
					Integer valore = buffer.preleva();
					if(valore!=null) {
						System.out.println("Prelevato: " + valore);
					}
				}			
			} catch (InterruptedException e) {
				System.err.println("Consumatore interrotto!");
			}
		};

		Thread tp = new Thread(produttore);
		Thread tc = new Thread(consumatore);
		tp.start();
		tc.start();

		Thread tc1 = new Thread(consumatore);
		tc1.start();

		Thread tc2 = new Thread(consumatore);
		tc2.start();

		//tp.interrupt();
	}

}
