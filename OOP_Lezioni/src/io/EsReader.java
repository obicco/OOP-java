package io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class EsReader {

	public static void main(String[] args) throws IOException {
		
		Reader r = new FileReader("esempio.txt");
		
		int ch;
		while( ( ch=r.read()) != -1) {
			System.out.print((char)ch);
		}
		r.close();

	}

}
