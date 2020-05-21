package day4;

import java.util.*;
import java.io.*;

class hashCounter implements Comparable<hashCounter> {

	public char c;
	public int count;
	public static String[] alph = {};

	public hashCounter(char c) {
		this.c = c;
		this.count = 1;
	}

	public hashCounter(char c, int count) {
		this.c = c;
		this.count = count;
	}

	@Override
	public int compareTo(hashCounter o) {
		if(count == o.count) {
			// If our count is the same, check who is first in the alphabet
			if(Character.toLowerCase(c) < Character.toLowerCase(o.c))
				return 1;
			else if(Character.toLowerCase(c) > Character.toLowerCase(o.c))
				return -1;
			return 0;
		} else if(count > o.count)  
			return 1;
		else  
			return -1;  
	}

}

public class SecurityThroughObscurity {
	
	public String calculateChecksum(String code) {
		HashMap<Character, hashCounter> letterMap = new HashMap<Character, hashCounter>();
        
        for(int x = 0; x < code.length(); x++) {
        	char letter = code.charAt(x);
        	if(Character.isLetter(letter)) {
	        	if(letterMap.containsKey(letter)) {
	        		hashCounter hc = letterMap.get(letter);
	        		hc.count++;
	        	} else {
	        		letterMap.put(letter, new hashCounter(letter));
	        	}
        	}
        }
        
        ArrayList<hashCounter> sortLetters = new ArrayList<hashCounter>(letterMap.values());
		Collections.sort(sortLetters);
		Collections.reverse(sortLetters);
		
		String calculatedChecksum = "";
		for(int x = 0; x < sortLetters.size() && x < 5; x++) {
			calculatedChecksum += sortLetters.get(x).c;
		}
		
		return calculatedChecksum;
	}

	public String decrypt(String code, int value) {
		value = value % 26;
//		System.out.println("Encryption: " + value);
		
		StringBuilder sb = new StringBuilder();
		Character dash = new Character('-');
		
		for(int x = 0; x < code.length(); x++) {
			char c = code.charAt(x);
			Character cCheck = new Character(c);
			if(cCheck.equals(dash)) {
				sb.append(" ");
//				System.out.println("[-: ]");
			} else {
//				System.out.print("Int: " + ((int)c + value));
//				System.out.print("[" + c + ":");
				if((int)c + value > 122) {
					c -= (26 - value);
				} else {
					c += value;
				}
//	            System.out.println(c + "]");
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SecurityThroughObscurity sec = new SecurityThroughObscurity();
		
		try(BufferedReader br = new BufferedReader(new FileReader("./data/day4/day4"))) {
		    String line = "";

		    int total = 0;
		    while (line != null) {
		        line = br.readLine();
		        
		        if(line != null && !line.equals("")) {
			        String code = line.substring(0, line.indexOf('['));
			        String checksum = line.substring(line.indexOf('[') + 1, line.length() - 1);
			        String sValue = code.substring(code.lastIndexOf('-') + 1);
			        int value = Integer.parseInt(sValue);
			        
//			        System.out.println(code);
//			        System.out.println(checksum);
//			        System.out.println(value);

					String calculatedChecksum = sec.calculateChecksum(code);
					if(calculatedChecksum.equals(checksum)) {
						System.out.println(sec.decrypt(code, value) + " {" + value + "}");
						total += value;
					}
		        }
		    }
		    System.out.println("Total: " + total);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
