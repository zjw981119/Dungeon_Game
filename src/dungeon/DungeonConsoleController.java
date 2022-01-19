package dungeon;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * To implement a console controller for the Dungeon.
 */
public class DungeonConsoleController implements DungeonController {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(Dungeon m) {
    if (m == null) {
      throw new IllegalArgumentException("Model can not be null");
    }
    //game start
    try {
      // next operation
      String str = "";
      while (!m.isGameOver()) {
        //check monster at player's current location
        int combatResult = 0;
        combatResult = m.combat();
        if (combatResult == 1) {
          break;
        } else if (combatResult == 2) {
          out.append("The monster is injured, you're lucky to escape this time").append("\n");
        }

        //get smell
        if (m.getSmellLevel() == 1) {
          out.append("You smell something terrible nearby").append("\n");
        } else if (m.getSmellLevel() == 2) {
          out.append("You smell something more terrible nearby, be careful").append("\n");
        }
        //get location description
        out.append(m.getLocationDescription()).append("\n");
        //choose operations
        out.append("Move, Pickup, or Shoot(M-P-S)?").append("\n");
        str = getOperation();
        // quit the game
        if (str == null) {
          out.append("Game quit!").append("\n");
          return;
        }
        switch (str) {
          //move
          case "M":
            out.append("Where to(north-south-east-west)?").append("\n");
            Integer moveDirection = getDirection();
            // quit the game
            if (moveDirection == null) {
              out.append("Game quit!").append("\n");
              return;
            }
            int result = 0;
            try {
              m.move(moveDirection);
            } catch (IllegalArgumentException iae) {
              out.append("Can not go to this direction").append("\n");
            }
            break;

          case "P":
            out.append("What(arrow-treasure)?").append("\n");
            Integer pickOption = getPickOption();
            //quit the game
            if (pickOption == null) {
              out.append("Game quit!").append("\n");
              return;
            } else if (pickOption == 1) {
              //pick treasure
              m.pickTreasure();
            } else {
              //pick arrow
              m.pickArrow();
            }
            break;

          //shoot arrow
          case "S":
            out.append("Where to(north-south-east-west)?").append("\n");
            Integer shootDirection = getDirection();
            // quit the game
            if (shootDirection == null) {
              out.append("Game quit!").append("\n");
              return;
            }
            out.append("Shooting distance(1-4)?").append("\n");
            Integer distance = getInteger();
            if (distance == null) {
              out.append("Game quit!").append("\n");
              return;
            }
            boolean shootResult = false;
            try {
              shootResult = m.shoot(shootDirection, distance);
              out.append("You shoot an arrow into the darkness").append("\n");
            } catch (IllegalStateException ise) {
              //no arrow
              out.append("You are out of arrows, explore to find more").append("\n");
            } catch (IllegalArgumentException iae) {
              //invalid distance
              out.append("Invalid distance: ").append(distance.toString()).append("\n");
            }
            //shoot the monster
            if (shootResult) {
              out.append("You hear a great howl in the distance").append("\n");
            }
            break;

          //quit the game
          case "q":
            out.append("Game quit!").append("\n");
            return;
          default:
            break;
        }
      }

      //game is over
      if (m.getPlayer().isAlive()) {
        out.append("Congratulations, you win!").append("\n");
      } else {
        out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!").append("\n");
        out.append("Better luck next time").append("\n");
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    } catch (NoSuchElementException ignored) {
      //ignore NoSuchElementException
    }

  }


  private boolean isInteger(String str) {
    Pattern pattern = Pattern.compile("^[-｜+]?[\\d]+$");
    return pattern.matcher(str).matches();
  }

  private String getOperation() {
    try {
      String element = scan.next();
      while (!(element.equals("M") || element.equals("P") || element.equals("S"))) {
        if (element.equalsIgnoreCase("q")) {
          return null;
        }
        out.append("Not a valid operation: ").append(element).append("\n");
        element = scan.next();
      }
      return element;
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private Integer getInteger() {
    try {
      String element = scan.next();
      while (!isInteger(element)) {
        if (element.equalsIgnoreCase("q")) {
          return null;
        }
        out.append("Not a valid number: ").append(element).append("\n");
        element = scan.next();
      }
      return Integer.parseInt(element);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  //get valid direction (north/south/east/west)
  private Integer getDirection() {
    try {
      String element = scan.next();
      while (!(element.equals("north") || element.equals("south")
              || element.equals("east") || element.equals("west"))) {
        if (element.equalsIgnoreCase("q")) {
          return null;
        }
        out.append("Not a valid direction: ").append(element).append("\n");
        element = scan.next();
      }
      switch (element) {
        case "north":
          return 1;
        case "south":
          return 2;
        case "east":
          return 3;
        case "west":
          return 4;
        default:
          break;
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
    return null;
  }

  //get valid picking option(treasure/ arrow)
  private Integer getPickOption() {
    try {
      String element = scan.next();
      while (!(element.equals("arrow") || element.equals("treasure"))) {
        if (element.equalsIgnoreCase("q")) {
          return null;
        }
        out.append("Not a valid picking option: ").append(element).append("\n");
        element = scan.next();
      }
      if (element.equals("treasure")) {
        return 1;
      } else {
        return 2;
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

}