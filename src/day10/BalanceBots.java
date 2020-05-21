package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class BalanceBots {
	
	HashMap<Integer, Bot> bots = new HashMap<Integer, Bot>();
	HashMap<Integer, Object> outputs = new HashMap<Integer, Object>();
	HashMap<Integer, Bot.Direction> outputTypes = new HashMap<Integer, Bot.Direction>();
	
	public Object getOutput(int id) {
		return outputs.get(id);
	}
	
	public Bot getBot(int id) {
		return bots.get(id);
	}
	
	public void parseDirection(String direction) throws Exception, NumberFormatException {
		StringTokenizer st = new StringTokenizer(direction);
		String focus = "";
		
		if(st.hasMoreTokens()) {
			focus = st.nextToken().toLowerCase();
			
			if(focus.equals("value")) {
				focus = st.nextToken();
				int newValue = Integer.parseInt(focus);
				
				if(!st.nextToken().equals("goes"))
					throw new Exception("Invalid direction format: expecting [goes]");
				if(!st.nextToken().equals("to"))
					throw new Exception("Invalid direction format: expecting [to]");
				if(!st.nextToken().equals("bot"))
					throw new Exception("Invalid direction format: expecting [bot]");
				
				focus = st.nextToken();
				int botIndex = Integer.parseInt(focus);
				
				if(bots.containsKey(botIndex)) {
					Bot focusBot = bots.get(botIndex);
					focusBot.receives(newValue);
				} else {
					Bot newBot = new Bot(botIndex);
					newBot.receives(newValue);
					bots.put(botIndex, newBot);
				}
			} else if(focus.equals("bot")) {
				focus = st.nextToken();
				
				// Do we need to check if this exists? Looks like we don't and just assume when we see it come up
				int startBotID = Integer.parseInt(focus);
				Bot startBot = bots.containsKey(startBotID) ? bots.get(startBotID) : new Bot(startBotID);
				bots.put(startBotID, startBot);
				
				if(!st.nextToken().equals("gives"))
					throw new Exception("Invalid direction format: expecting [gives]");
				
				String type = st.nextToken().toLowerCase();
				if(!type.equals("high") && !type.equals("low"))
					throw new Exception("Invalid direction format: expecting [high|low]");
				if(!st.nextToken().equals("to"))
					throw new Exception("Invalid direction format: expecting [to]");
				Bot.Direction dType = type.equals("high") ? Bot.Direction.HIGH : Bot.Direction.LOW;
				
				focus = st.nextToken().toLowerCase();
				if(focus.equals("bot")) {
					focus = st.nextToken();
					int destBotID = Integer.parseInt(focus);
					
					Bot destBot = bots.containsKey(destBotID) ? bots.get(destBotID) : new Bot(destBotID);
					startBot.gives(dType, destBot);
					bots.put(destBotID, destBot);
				} else if(focus.equals("output")) {
					focus = st.nextToken();
					int destOut = Integer.parseInt(focus);
					
					outputs.put(destOut, startBot.gives(dType));
					outputTypes.put(destOut, dType);
				} else {
					throw new Exception("Invalid direction format: expecting [bot|output]");
				}
				
				// If there is no second part, return
				if(!st.hasMoreTokens())
					return;
				
				if(!st.nextToken().equals("and"))
					throw new Exception("Invalid direction format: expecting [and]");
				type = st.nextToken().toLowerCase();
				if(!type.equals("high") && !type.equals("low"))
					throw new Exception("Invalid direction format: expecting [high|low]");
				if(!st.nextToken().equals("to"))
					throw new Exception("Invalid direction format: expecting [to]");
				
				dType = type.equals("high") ? Bot.Direction.HIGH : Bot.Direction.LOW;
				
				focus = st.nextToken().toLowerCase();
				if(focus.equals("bot")) {
					focus = st.nextToken();
					int destBotID = Integer.parseInt(focus);
					
					Bot destBot = bots.containsKey(destBotID) ? bots.get(destBotID) : new Bot(destBotID); 
					startBot.gives(dType, destBot);
					bots.put(destBotID, destBot);
				} else if(focus.equals("output")) {
					focus = st.nextToken();
					int destOut = Integer.parseInt(focus);
					
					outputs.put(destOut, startBot.gives(dType));
					outputTypes.put(destOut, dType);
				} else {
					throw new Exception("Invalid direction format: expecting [bot|output]");
				}
			} else {
				throw new Exception("Invalid direction format: expecting [value|bot]");
			}
		}
	}
	
	public void simplify() {
		for (int key : bots.keySet() ) {
			Bot focusBot = bots.get(key);
			focusBot.simplify(Bot.Direction.LOW);
	    }
		
		for (int key : outputs.keySet()) {
			if(outputs.get(key).getClass().equals(Bot.class)) {
				Bot opBot = (Bot)outputs.get(key);
				Bot.Direction type = outputTypes.get(key);
				outputs.put(key, opBot.simplify(type));
			}
		}
	}
	
	public void printBots() {
		for (int key : bots.keySet() ) {
	        System.out.println(bots.get(key));
	    }
	}
	
	public void printOutputBins() {
		for (int key : outputs.keySet()) {
			System.out.println("Output bin " + key + ": " + outputs.get(key));
		}
	}

	public static void main(String[] args) {
		BalanceBots bb = new BalanceBots();
		
		try(BufferedReader br = new BufferedReader(new FileReader("./data/day10/day10"))) {
		    String line = "";

		    while (line != null) {
		        line = br.readLine();
		        
		        if(line != null && !line.equals(""))
			        bb.parseDirection(line);
		    }
		    
		    bb.simplify();
		    bb.printBots();
		    bb.printOutputBins();
		    
		} catch (NumberFormatException nfe) {
        	nfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
