package treasure;

/**
 * This class represents a diamond. It offers method
 * to create a Diamond object using the given name.
 */
public class Diamond extends AbstractTreasure {

  /**
   * Create a Diamond object using the given name.
   *
   * @param name the name of treasure
   * @throws IllegalArgumentException if name is null.
   */
  public Diamond(String name) {
    super(name);
  }

}
