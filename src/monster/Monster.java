package monster;


/**
 * Treasure interface that contains operations that a monster
 * should support.
 */
public interface Monster {

  /**
   * Get the name of monster.
   *
   * @return the name
   */
  String getName();

  /**
   * Get the health of the monster.
   *
   * @return health
   */
  int getHealth();

  /**
   * Reduce the health of the monster.
   */
  void reduceHealth();

}
