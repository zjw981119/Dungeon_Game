package dungeon;

import location.Location;
import location.Point2D;
import player.Player;

import java.util.List;


/**
 * The interface needed for a read-only model for the Dungeon game.
 */
public interface ReadonlyDungeonModel {

  /**
   * Get start cave position in the dungeon.
   *
   * @return the start position
   */
  Point2D getStartPosition();

  /**
   * Get end cave position in the dungeon.
   *
   * @return the start position
   */
  Point2D getEndPosition();

  /**
   * Get the smell from player's current location.
   *
   * @return smell level : level 0 means no smell;
   *                       level 1 means there is a single Otyugh 2 positions
   *                       from the player's current location;
   *                       level 2 means there is a single Otyugh 1 positions
   *                       from the player's current location or there are multiple
   *                       Otyughs within 2 positions from the player's current location.
   */
  int getSmellLevel();

  /**
   * Get the description of player,
   * the information contains player's name, and treasure he/she has.
   *
   * @return string description
   */
  String getPlayerDescription();

  /**
   * Get the current location description of player,
   * the information contains treasure at current location and player's
   * available next move directions.
   *
   * @return string description
   */
  String getLocationDescription();

  /**
   * Get locations in dungeon.
   *
   * @return Location array
   */
  Location[][] getLocations();

  /**
   * Get dungeon description.
   *
   * @return descriptions of each location in the dungeon.
   */
  List<String> getDungeon();

  /**
   * Get player in dungeon.
   *
   * @return player
   */
  Player getPlayer();

  /**
   * Return the game status.
   * @return true if game is over
   *         false otherwise
   */
  boolean isGameOver();

}
