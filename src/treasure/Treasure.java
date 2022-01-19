package treasure;

/**
 * Treasure interface that contains operations that all types of treasure
 * should support.
 */
public interface Treasure extends Comparable<Treasure> {

  /**
   * Get the name of treasure.
   *
   * @return the name
   */
  String getName();
}
