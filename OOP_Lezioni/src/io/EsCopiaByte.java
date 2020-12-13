package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EsCopiaByte {

	public static void main(String[] args) throws IOException {
		
		String fileInput = "esempio.txt";
		String fileOutput = "copia.txt";
		
		byte[] buffer = new byte[1024];
		
//		FileInputStream in = new FileInputStream(fileInput);
//		FileOutputStream out = new FileOutputStream(fileOutput);
//		
//		try {
//			int n;
//			while( (n=in.read(buffer)) != -1 ) {
//				out.write(buffer,0,n);
//			}
//		}finally {
//			in.close();
//			out.close();
//		}
		
		// OPPURE con il costrutto TRY-WITH-RESOURCE
		
		try(
		FileInputStream in = new FileInputStream(fileInput);
		FileOutputStream out = new FileOutputStream(fileOutput) ){
			int n;
			while( (n=in.read(buffer)) != -1 ) {
				out.write(buffer,0,n);
			}
		}

	}

}
