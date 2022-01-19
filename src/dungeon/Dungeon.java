package dungeon;

import location.Location;
import location.Point2D;

/**
 * Dungeon interface which contains operations that a dungeon game should support.
 */
public interface Dungeon extends ReadonlyDungeonModel {
  /**
   * Move the player in the dungeon, the position of player will be changed.
   *
   * @param direction move direction
   *                  1: north
   *                  2: south
   *                  3: east
   *                  4: west
   * @throws IllegalArgumentException if direction is not in the range of [1,4];
   *                                  or unavailable move at current location;
   */
  void move(int direction);

  /**
   * Player shoots the arrow in the specified direction with given distance.
   *
   * @param direction shooting distance
   * @param distance  shooting direction
   *                  1: north
   *                  2: south
   *                  3: east
   *                  4: west
   * @return true if player shot the monster successfully, false otherwise
   * @throws IllegalArgumentException if direction or distance is not in the range of [1,4]
   */
  boolean shoot(int direction, int distance);

  /**
   * Pick treasure at current location, the bag of player will be updated,
   * and the treasure at the location will be removed.
   */
  void pickTreasure();

  /**
   * Pick arrow at current location, the quiver of player will be updated,
   * and the arrow at the location will be removed.
   */
  void pickArrow();

  /**
   * Player combats with monster.
   *
   * @return 0 if there's no monster at player's location
   *         1 if player is eaten by monster
   *         2 if player is able to escape from an injured monster
   */
  int combat();

  /**
   * Reset player for new game, including position, arrows, treasure.
   */
  void resetPlayer();

  /**
   * Reset start cave position for new game.
   *
   * @param position start cave position
   */
  void resetStartCave(Point2D position);

  /**
   * Reset end cave position for new game.
   *
   * @param position end cave position
   */
  void resetEndCave(Point2D position);

  /**
   * Reset dungeon for new game.
   *
   * @param locations locations
   */
  void resetDungeon(Location[][] locations);
}
