package dungeon;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Run a TicTacToe game interactively on the console.
 */
public class Main {

  /**
   * Call this method to run the text-based game.
   */
  public static void textBasedGame() {
    //create the dungeon
    System.out.println("Please enter valid arguments to create Dungeon:");
    Scanner scanner = new Scanner(System.in);
    String str = "";
    str = scanner.next();
    int x = Integer.parseInt(str);
    str = scanner.next();
    int y = Integer.parseInt(str);
    str = scanner.next();
    boolean wrapping = Boolean.parseBoolean(str);
    str = scanner.next();
    int interconnectivity = Integer.parseInt(str);
    str = scanner.next();
    int percentage = Integer.parseInt(str);
    str = scanner.next();
    int monsterAmount = Integer.parseInt(str);
    Dungeon dungeon = new DungeonModel(x, y, wrapping,
            interconnectivity, percentage, monsterAmount);
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    new DungeonConsoleController(input, output).playGame(dungeon);
  }

  /**
   * Call this method to run the GUI-based game.
   */
  public static void guiBasedGame() {
    Dungeon model = new DungeonModel(7, 7, true, 15, 50, 2);
    DungeonView view = new DungeonSwingView(model);
    DungeonViewController controller = new DungeonViewControllerImpl(model, view);
    controller.playGame(model);
  }

  /**
   * Create a dungeon model, pass it to controller and start game.
   */
  public static void main(String[] args) {
    //textBasedGame();
    System.out.println("Please enter game type you want to play:");
    Scanner scanner = new Scanner(System.in);
    String str = "";
    str = scanner.next();
    //run text-based game
    if (str.equals("text")) {
      textBasedGame();
    } else if (str.equals("gui")) {
      guiBasedGame();
    }
  }
}
