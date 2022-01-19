package treasure;

/**
 * This class represents a sapphire. It offers method
 * to create a Sapphire object using the given name.
 */
public class Sapphire extends AbstractTreasure {
  /**
   * Create a Sapphire object using the given name.
   *
   * @param name the name of treasure
   * @throws IllegalArgumentException if name is null.
   */
  public Sapphire(String name) {
    super(name);
  }
}
