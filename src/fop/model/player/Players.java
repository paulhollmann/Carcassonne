package fop.model.player;

import java.util.LinkedList;


/**
 * handles the current players while the game is started via LinkedList<Players>
 */
public class Players {

	private static LinkedList<Player> players= new LinkedList<Player>(); 
	
	/**
	 * adds a player to the static list
	 * @param player
	 */
	public static void addPlayer(Player player) {	
		players.add(player); 
	}
	/**
	 * return true if the color can be chosen
	 * return false if the color has already been chosen 
	 */
	public static boolean isColorAvailable(Player player) {		
		return players.stream().filter(x-> x.getColor().equals(player.getColor())).count() == 0; 
	}
	
	/**
	 * returns the current Players
	 * @return
	 */
	public static LinkedList<Player> getPLayers(){
		
		return players; 
	}
	/**
	 * resets the current list of Players 
	 */
	public static void resetPlayers() {
		
		players = new LinkedList<Player>();
	}
}
