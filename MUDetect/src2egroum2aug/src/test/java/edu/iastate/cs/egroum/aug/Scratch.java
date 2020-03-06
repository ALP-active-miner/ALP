package edu.iastate.cs.egroum.aug;

import java.io.File;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Scratch {

	 private static final String DELIMITERS =
	            " \n.();-\"=,*/{}_<>+\r:'@[]&\\!#|?$^%~`\t";
	 
	public static void main(String...strings ) {
//		File f = new File("hello_world"); 
//		try (PrintWriter pw = new PrintWriter(f)) {
//			
//			int i = 5 + 5;
//		} catch(Exception e) {
//			
//			
//		} finally {
//			System.out.println("hello world");
//		}
//		
//		
//		Long l = null;
////		long ll = l;
//		if (l instanceof Long) {
//			System.out.println("");
//		}
		
		String a  = "hello " + null;
		System.out.println(a);
//		Object s = (int) l;
		
		StringTokenizer tokenizer = new StringTokenizer(" ", DELIMITERS);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            char first = token.charAt(0);
            System.out.println(first);
            System.out.println("===");
        }
			
	}
	
}
