package io;

import java.io.FileWriter;
import java.io.IOException;

public class EsWriter {

	public static void main(String[] args) throws IOException {
		
		FileWriter w = new FileWriter("output.txt");

		w.write("Prova");
		
		w.flush();
		
		//w.close();
	}

}
