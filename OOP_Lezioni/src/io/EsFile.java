package io;

import java.io.File;
import java.io.IOException;

public class EsFile {

	public static void main(String[] args) throws IOException {
		
		File current = new File(".");
		
		System.out.println(current.getCanonicalPath());
		
		for(File f : current.listFiles()) {
			System.out.println(f.getName() +
								(f.isDirectory()?"/":"") +
								"\t" + f.length());
		}
		
	}
}
