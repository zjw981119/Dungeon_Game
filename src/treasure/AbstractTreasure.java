package treasure;

/**
 * An abstract class that contains all the code that is shared by all types
 * of treasure, which have name.
 */
public abstract class AbstractTreasure implements Treasure {

  private final String name;

  /**
   * protected consturctor for use by subclasses.
   *
   * @param name the name of treasure
   * @throws IllegalArgumentException if name is null.
   */
  protected AbstractTreasure(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name can not be null");
    }
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int compareTo(Treasure other) {
    if (other == null) {
      throw new IllegalArgumentException("Argument can not be null");
    }
    return this.getName().compareTo(other.getName());
  }
}
