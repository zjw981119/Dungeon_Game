package treasure;

/**
 * This class represents a ruby. It offers method
 * to create a Ruby object using the given name.
 */
public class Ruby extends AbstractTreasure {

  /**
   * Create a Ruby object using the given name.
   *
   * @param name the name of treasure
   * @throws IllegalArgumentException if name is null.
   */
  public Ruby(String name) {
    super(name);
  }
}
