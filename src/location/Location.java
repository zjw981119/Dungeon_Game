package location;

import monster.Monster;
import treasure.Treasure;
import weapon.Arrow;

import java.util.List;



/**
 * Location interface that contains operations that all types of locations
 * should support.
 */
public interface Location {


  /**
   * Get the status of north door of location.
   * @return the status of north door
   */
  boolean getNorthDoor();

  /**
   * Set the status or north door.
   */
  void setNorthDoor(boolean value);

  /**
   * Get the status of south door of location.
   * @return the status of south door
   */
  boolean getSouthDoor();

  /**
   * Set the status or south door.
   */
  void setSouthDoor(boolean value);

  /**
   * Get the status of east door of location.
   * @return the status of east door
   */
  boolean getEastDoor();

  /**
   * Set the status or east door.
   */
  void setEastDoor(boolean value);

  /**
   * Get the status of west door of location.
   * @return the status of west door
   */
  boolean getWestDoor();

  /**
   * Set the status of west door.
   */
  void setWestDoor(boolean value);

  /**
   * Get monster at current location.
   * @return list of monster.
   */
  List<Monster> getMonster();

  /**
   * Add monster at current location.
   * @param monster monster object
   *
   * @throws IllegalArgumentException if monster is null
   */
  void addMonster(Monster monster);

  /**
   * Remove monster at current location when player slayed it.
   */
  void removeMonster();

  /**
   * Get arrows at current location.
   * @return list of arrows
   */
  List<Arrow> getArrow();

  /**
   * Add arrow at current location.
   * @param arrow arrow object
   *
   * @throws IllegalArgumentException if arrow is null
   */
  void addArrow(Arrow arrow);

  /**
   * Remove arrows at current location when player picked them up.
   */
  void removeArrow();

  /**
   * Get treasure at current location.
   * @return list of treasure
   */
  List<Treasure> getTreasure();

  /**
   * Add treasure at current location.
   * @param treasure treasure object
   *
   * @throws IllegalArgumentException if treasure is null
   */
  void addTreasure(Treasure treasure);

  /**
   * Remove treasure at current location when player picked it up.
   */
  void removeTreasure();
}
