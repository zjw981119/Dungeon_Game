package dungeon;

/**
 * Represents a Controller for Dungeon: handle user operations by executing them using the model;
 * convey operation outcomes to the user in some form.
 */
public interface DungeonViewController {

  /**
   * Execute a single game of TicTacToe given a TicTacToe Model. When the game is over,
   * the playGame method ends.
   *
   * @param m a non-null TicTacToe Model
   */
  void playGame(Dungeon m);

  /**
   * Handle an action in a single label of the panel, such as to make a move.
   *
   * @param direction the direction compared to player
   *                  1: north
   *                  2: south
   *                  3: east
   *                  4: west
   */
  void handlePanelClick(int direction);

}
