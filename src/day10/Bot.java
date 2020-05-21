package day10;

public class Bot {
	
	enum Direction {
		LOW,
		HIGH
	}
	
	private Object high, low;
	public Direction cdHigh, cdLow;
	public int number = 0;

	public Bot(int number) {
		this.number = number;
	}
	
	/**
	 * Gives high/low value to another bot
	 * Example: bot1.gives('low', bot2);
	 * @param type High or Low value to give
	 * @param bot destination bot to receive value
	 * @throws Exception Invalid type passed in
	 */
	public void gives(Direction type, Bot bot) throws Exception {
		if(type == Direction.HIGH) {
			if(high != null && high.getClass().equals(Integer.class) && low != null && low.getClass().equals(Integer.class)) {
				bot.receives((int)high);
			} else {
				bot.receives(this, Direction.HIGH);
			}
		} else if(type == Direction.LOW) {
			if(high != null && high.getClass().equals(Integer.class) && low != null && low.getClass().equals(Integer.class)) {
				bot.receives((int)low);
			} else {
				bot.receives(this, Direction.LOW);
			}
		}
	}
	
	/**
	 * Gives high/low value to another bot
	 * Example: bot2.gives('high');
	 * @param type type High or Low value to give
	 * @throws Exception Invalid type passed in
	 * @return Integer/Object for output
	 */
	public Object gives(Direction type) {
		if(high != null && low != null && high.getClass().equals(Integer.class) && low.getClass().equals(Integer.class)) {
			if(type == Direction.HIGH) {
				System.out.println("-- output high: " + high + " --");
				return high;
			} else {
				System.out.println("-- output low: " + low + " --");
				return low;
			}
		} else {
//			System.out.println("-- output pointer: " + (type == Direction.HIGH ? "high" : "low") + " --");
			return this;
		}
	}
	
	/**
	 * Receives number from another bot or input
	 * Example: bot2.receives(3);
	 * @param number New value coming in
	 * @throws Exception thrown if we already have this number or our bot is full
	 */
	public void receives(int number) throws Exception {
		// Completely empty
		if(high == null && low == null) {
			low = number;
			return;
		}
		
		// Next one int and one bot
		if(high != null && low == null) {
			low = number;
		} else if(low != null && high == null) {
			high = number;
		} else {
			// We shouldn't be here...
		}
		
		// All numbers
		if(high != null && low != null && high.getClass().equals(Integer.class) && low.getClass().equals(Integer.class)) {
			// Make sure high and low are in order
			if((int)high < (int)low) {
				int tmp = (int)high;
				high = low;
				low = tmp;
			}
		}
	}
	
	/**
	 * Receives place holder if we don't know which number is high/low or even have a number
	 * @param b bot passed in for future reference
	 * @param type	direction of which number to use
	 */
	public void receives(Object b, Direction type) {
		if(high == null) {
			high = b;
			cdHigh = type;
		} else {
			low = b;
			cdLow = type;
		}
	}
	
	/**
	 * Recursive function that goes all the way to the end of the bots until we 
	 * find the bot with both numbers and start solving our way back until
	 * we know the correct high/low numbers for each bot
	 * @param d direction of which number we are solving for
	 * @return	number of the direction we requested
	 */
	public int simplify(Direction d) {
		if(high != null && high.getClass().equals(Bot.class)) {
			high = ((Bot)high).simplify(cdHigh);
		}
		if(low != null && low.getClass().equals(Bot.class)) {
			low = ((Bot)low).simplify(cdLow);
		}
		
		// If we have solved number, make sure they are in the right places now
		if(high != null && low != null && high.getClass().equals(Integer.class) && low.getClass().equals(Integer.class)) {
			// Make sure high and low are in order
			if((int)high < (int)low) {
				int tmp = (int)high;
				high = low;
				low = tmp;
			}
		}
		
		if(d == Direction.HIGH)
			return high != null ? (int)high : 0;
		return low != null ? (int)low : 0;
	}
	
	public String toString() {
		return "Bot " + number + " [l" + (low!= null ? (low.getClass().equals(Integer.class) ? ("(" + (int)low + ")") : ("{B" + ((Bot)low).number) + "}") : "_") + "|h" + (high != null ? (high.getClass().equals(Integer.class) ? ("(" + (int)high + ")"): ("{B" + ((Bot)high).number) + "}") : "_") + "]";
	}
	
	public Object getHigh() {
		return high;
	}
	
	public Object getLow() {
		return low;
	}

}
