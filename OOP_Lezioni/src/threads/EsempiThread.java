package threads;

public class EsempiThread {

	public static void main(String[] args) {
		
		Runnable cosaFare = () ->{
			System.out.println("Ciao soono un thread...");
			
			for(int i=0; i<20; ++i) {
				System.out.println(Thread.currentThread().getName() 
						+ ": " + i);
			}
			
			System.out.println("Ho finito!");
		};
		
		Thread t = new Thread( cosaFare , "Bob");
		t.start();

		Thread kevin = new Thread( cosaFare , "Kevin");
		kevin.start();
		
		Thread stuart = new Thread( cosaFare , "Stuart");
		stuart.start();
	
		
		for(int i=0; i<20; ++i) {
			System.out.println("\t" + i);
		}

	}

}
