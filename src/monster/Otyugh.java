package monster;

/**
 * Otyugh represents a monster with name and health.
 * An Otyugh can eat a player or be slayed by a player.
 */
public class Otyugh implements Monster {

  private final String name;
  private int health;

  /**
   * Create a Otyugh object using the given name.
   *
   * @param name name of Otyugh monster
   */
  public Otyugh(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
    this.health = 2;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public void reduceHealth() {
    health -= 1;
  }
}
