package day10.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import day10.BalanceBots;
import day10.Bot;

public class BalanceBotsTests {
	
	BalanceBots bbots;
	
	@Before
	public void initialize() {
		bbots = new BalanceBots();
	}

	@Test
	public void baseTest() {
		try {
			bbots.parseDirection("value 5 goes to bot 2");
			bbots.parseDirection("bot 2 gives low to bot 1 and high to bot 0");
			bbots.parseDirection("value 3 goes to bot 1");
			bbots.parseDirection("bot 1 gives low to output 1 and high to bot 0");
			bbots.parseDirection("bot 0 gives low to output 2 and high to output 0");
			bbots.parseDirection("value 2 goes to bot 2");
			
			bbots.simplify();
			bbots.printBots();
			bbots.printOutputBins();
			
			assertEquals((int)bbots.getOutput(0), 5);
			assertEquals((int)bbots.getOutput(1), 2);
			assertEquals((int)bbots.getOutput(2), 3);
			
			Bot bot0 = bbots.getBot(0);
			Bot bot1 = bbots.getBot(1);
			Bot bot2 = bbots.getBot(2);

			assertEquals((int)bot0.getHigh(), 5);
			assertEquals((int)bot0.getLow(), 3);
			assertEquals((int)bot1.getHigh(), 3);
			assertEquals((int)bot1.getLow(), 2);
			assertEquals((int)bot2.getHigh(), 5);
			assertEquals((int)bot2.getLow(), 2);
		} catch (NumberFormatException nfe) {
			System.out.println("Number format exception");
			fail();
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			fail();
		}
	}

}
