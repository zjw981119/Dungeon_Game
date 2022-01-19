package player;

import location.Point2D;
import treasure.Treasure;
import weapon.Arrow;

import java.util.List;


/**
 * Player interface that contains operations that a player should support.
 */
public interface Player {

  /**
   * Get the name of player.
   *
   * @return the name
   */
  String getName();

  /**
   * Get the life of player.
   *
   * @return life
   */
  int getLife();

  /**
   * Reduce the life of player by 1.
   */
  void reduceLife();

  /**
   * Check if player is alive.
   * @return true if player is alive
   *         false otherwise
   */
  boolean isAlive();

  /**
   * Get the object of Point2D(which represents the location of player).
   *
   * @return Point2D object
   */
  Point2D getPosition();

  /**
   * Set the object of Point2D(which represents the location of player).
   */
  void setPosition(int x, int y);

  /**
   * Get the player's bag(which stores every treasure he picked).
   *
   * @return list of Treasure objects
   */
  List<Treasure> getBag();

  /**
   * Add the treasure to player's bag.
   *
   * @param treasure Treasure object
   * @throws IllegalArgumentException when treasure is null
   */
  void addBag(Treasure treasure);

  /**
   * Get the player's quiver(which stores every arrow he picked).
   *
   * @return list of Arrow objects
   */
  List<Arrow> getQuiver();

  /**
   * Add the arrow to player's quiver.
   *
   * @param arrow Arrow object
   * @throws IllegalArgumentException when arrow is null
   */
  void addQuiver(Arrow arrow);

  /**
   * Remove arrow from quiver after player shoot it.
   */
  void reduceArrow();

}
