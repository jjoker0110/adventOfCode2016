package day1;

import java.util.*;

public class NoTimeForTaxicab {
	
	public static char[] compass = {'N', 'E', 'S', 'W'};


	public static void main(String[] args) {
		NoTimeForTaxicab taxi = new NoTimeForTaxicab();
		String directions = "L4, R2, R4, L5, L3, L1, R4, R5, R1, R3, L3, L2, L2, R5, R1, L1, L2, R2, R2, L5, R5, R5, L2, R1, R2, L2, L4, L1, R5, R2, R1, R1, L2, L3, R2, L5, L186, L5, L3, R3, L5, R4, R2, L5, R1, R4, L1, L3, R3, R1, L1, R4, R2, L1, L4, R5, L1, R50, L4, R3, R78, R4, R2, L4, R3, L4, R4, L1, R5, L4, R1, L2, R3, L2, R5, R5, L4, L1, L2, R185, L5, R2, R1, L3, R4, L5, R2, R4, L3, R4, L2, L5, R1, R2, L2, L1, L2, R2, L2, R1, L5, L3, L4, L3, L4, L2, L5, L5, R2, L3, L4, R4, R4, R5, L4, L2, R4, L5, R3, R1, L1, R3, L2, R2, R1, R5, L4, R5, L3, R2, R3, R1, R4, L4, R1, R3, L5, L1, L3, R2, R1, R4, L4, R3, L3, R3, R2, L3, L3, R4, L2, R4, L3, L4, R5, R1, L1, R5, R3, R1, R3, R4, L1, R4, R3, R1, L5, L5, L4, R4, R3, L2, R1, R5, L3, R4, R5, L4, L5, R2";
		
		int answer = taxi.howManyBlocksAway(directions);
		System.out.println("Blocks away: " + answer);
	}
	
	public int howManyBlocksAway(String directions) {
		int ns = 0, ew = 0, heading = 0;
		StringTokenizer t = new StringTokenizer(directions, ", ");
		HashSet<String> history = new HashSet<String>();
		
		while(t.hasMoreTokens()) {
			String cDir = t.nextToken();
			Boolean isLeft = cDir.substring(0, 1).contentEquals("L");
			int steps = Integer.parseInt(cDir.substring(1));
			
//			System.out.println("[" + (isLeft ? "L" : "R") + "] " + steps);
			if(isLeft) {
				heading--;
				heading = heading < 0 ? 3 : heading;
			} else {
				heading++;
				heading = heading > 3 ? 0 : heading;
			}
			
//			System.out.println(" |--> [" + compass[heading] + "] ");
			switch(compass[heading]) {
				case 'N':
					while(steps > 0) {
						ns++;
						steps--;
						
						if(history.contains(ew + "," + ns)) {
							return Math.abs(ns) + Math.abs(ew);
						} else {
							history.add(ew + "," + ns);
						}
					}
					break;
				case 'S':
					while(steps > 0) {
						ns--;
						steps--;
						
						if(history.contains(ew + "," + ns)) {
							return Math.abs(ns) + Math.abs(ew);
						} else {
							history.add(ew + "," + ns);
						}
					}
					break;
				case 'E':
					while(steps > 0) {
						ew++;
						steps--;
						
						if(history.contains(ew + "," + ns)) {
							return Math.abs(ns) + Math.abs(ew);
						} else {
							history.add(ew + "," + ns);
						}
					}
					break;
				case 'W':
					while(steps > 0) {
						ew--;
						steps--;
						
						if(history.contains(ew + "," + ns)) {
							return Math.abs(ns) + Math.abs(ew);
						} else {
							history.add(ew + "," + ns);
						}
					}
					break;
			}
		}

//		System.out.println(" |--> NS: " + ns);
//		System.out.println(" |--> EW: " + ew);
//		System.out.println(" |--> Answer: " + (Math.abs(ns) + Math.abs(ew)));
		return Math.abs(ns) + Math.abs(ew);
	}

}
