package dungeon;

/**
 * Represents a Controller for Dungeon: handle user operations by executing them using the model;
 * convey operations outcomes to the user in some form.
 */
public interface DungeonController {

  /**
   * Execute a single game of dungeon given a dungeon Model. When the game is over,
   * the playGame method ends.
   *
   * @param m a non-null dungeon Model
   */
  void playGame(Dungeon m);
}
