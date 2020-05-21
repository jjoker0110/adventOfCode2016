package day4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import day4.SecurityThroughObscurity;

public class SecurityThroughObscurityTests {
	
	SecurityThroughObscurity sec;

	@Before
	public void initialize() {
		sec = new SecurityThroughObscurity();
	}

	@Test
	public void baseTest() {
		String[] codes = {"aaaaa-bbb-z-y-x-123", "a-b-c-d-e-f-g-h-987", "not-a-real-room-404", "totally-real-room-200"};
		String[] checksums = {"abxyz", "abcde", "oarel", "decoy"};
		boolean[] isValid = {true, true, true, false};
		
		for(int x = 0; x < codes.length; x++) {
			if(isValid[x]) {
				assertEquals(sec.calculateChecksum(codes[x]), checksums[x]);
			} else {
				assertNotEquals(sec.calculateChecksum(codes[x]), checksums[x]);
			}
		}
	}
	
	@Test
	public void decryptTest() {
		String code = "qzmt-zixmtkozy-ivhz";
		int value = 343;
		String secret = sec.decrypt(code, value);
		
		assertEquals(secret, "very encrypted name");
	}
	
}
