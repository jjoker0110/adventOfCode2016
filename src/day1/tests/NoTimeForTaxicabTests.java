package day1.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import day1.NoTimeForTaxicab;

public class NoTimeForTaxicabTests {

	NoTimeForTaxicab taxi;

	@Before
	public void initialize() {
		taxi = new NoTimeForTaxicab();
	}

	@Test
	public void baseTest1() {
		int answ = taxi.howManyBlocksAway("R2, L3");
		assertEquals(answ, 5);
	}

	@Test
	public void testFindMax2() {
		int answ = taxi.howManyBlocksAway("R2, R2, R2");
		assertEquals(answ, 2);
	}

	@Test
	public void testFindMax3() {
		int answ = taxi.howManyBlocksAway("R5, L5, R5, R3");
		assertEquals(answ, 12);
	}

	@Test
	public void testVisitTwiceBase() {
		int answ = taxi.howManyBlocksAway("R8, R4, R4, R8");
		assertEquals(answ, 4);
	}

}
