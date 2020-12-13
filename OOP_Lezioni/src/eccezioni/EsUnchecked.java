package eccezioni;

public class EsUnchecked {

	public static void main(String[] args) {
		
		String s = " 123 000";
		
		try {
			int i = Integer.parseInt(s.trim());
		
			System.out.println("Doppio: " + (i*2));
		}catch(NumberFormatException nfe) {
			System.out.println("Numero non valido");
		}
		
		String[] numeri = {"1","2", " III", "4"};
		
		int sum=0;
		for(int i=0; i<numeri.length; ++i) {
			try {
				sum += Integer.parseInt(numeri[i]);
			}catch(NumberFormatException nfe) {
				System.out.println("Attenzione: saltato l'elemento " + numeri[i]);
				// salto il numero
			}
		}
		System.out.println(sum);
		
	    sum=0;
		try {
			for(int i=0; i<numeri.length; ++i) {
				sum += Integer.parseInt(numeri[i]);
			}
		}catch(NumberFormatException nfe) {
			System.out.println("Attenzione: somma incompleta");
			// salto il numero
		}
		System.out.println(sum);
	}
}
