/**
 * 
 */
package com.risk6441.strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Territory;
import com.risk6441.exception.InvalidGameActionException;
import com.risk6441.gameutils.GameUtils;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public interface IStrategy extends Serializable{
	
	/**
	 * This method is responsible for reinforcement phase.
	 * @param territoryList Territory list
	 * @param territory Selected territory.
	 * @param currentPlayer Current player.
	 * @param terrArList List of territories available to player.
	 * @param adjTerrArList List of adjacent territories.
	 */
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory,
			Player currentPlayer,
			ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList);
	
	/**
	 * This method implements the attack phase of the strategy.
	 * @param terrList listview of territory which belong to a player
	 * @param adjTerrList adjacent territory listview for a particular territory
	 * @param playerModel object of {@link PlayerModel}
	 * @param playerList list of players
	 * @param terrArList List of territories available to player.
	 * @param adjTerrArList List of adjacent territories.
	 * @throws InvalidGameActionException throws InvalidGameActionException if move is not valid
	 */
	void attackPhase(ListView<Territory> terrList, ListView<Territory> adjTerrList,
			PlayerModel playerModel, List<Player> playerList,
			ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList) throws InvalidGameActionException;
	

	/**
	 * This method implements the fortification phase of the strategy.
	 * @param selectedTerritory List of selected territories for fortification.
	 * @param adjTerritory List of adjacent territories for fortification.
	 * @param currentPlayer The current player object.
	 * @param map The current map loaded.
	 * @param terrArList List of territories.
	 * @param adjTerrArList List of adjacent territories.
	 * @return Returns a true value if fortification is possible.
	 */
	boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory, Player currentPlayer, Map map,
			ArrayList<Territory> terrArList,ArrayList<Territory> adjTerrArList);
	
	

	/**
	 * Check if the player has a valid attack move
	 * @param territories List of territories with the player.
	 * @return Returns true if the player has a valid move available.
	 */
	default public boolean hasAValidAttackMove(ArrayList<Territory> territories) {
		boolean isValidAttackMove = false;
		for (Territory territory : territories) {
			if (territory.getArmy() > 1 && getDefendingTerr(territory).size() > 0) {
				isValidAttackMove = true;
				return isValidAttackMove;
			}
		}
		if (!isValidAttackMove) {
			GameUtils.addTextToLog("No valid attack move avialble move to Fortification phase.\n");
			GameUtils.addTextToLog("===Attack phase ended! === \n");
			return isValidAttackMove;
		}
		return isValidAttackMove;
	}
	
	/**
	 * This method gives the defending territories for given territory.
	 * @param terr Selected territory
	 * @return List of defending territories.
	 */
	default public List<Territory> getDefendingTerr(Territory terr) {
		List<Territory> defTerrList = terr.getAdjacentTerritories().stream()
				.filter(t -> (terr.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defTerrList;
	}
}
