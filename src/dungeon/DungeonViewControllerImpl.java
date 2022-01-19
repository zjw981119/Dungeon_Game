package dungeon;

import location.Location;
import location.Point2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;


/**
 * DungeonViewControllerImpl represents the controller of Dungeon.
 */
public class DungeonViewControllerImpl implements
        DungeonViewController, ActionListener, KeyListener {

  private Dungeon model;
  private DungeonView view;
  private boolean isRPressed;
  private Location[][] reuseLocation;
  private Point2D reuseStart;
  private Point2D reuseEnd;

  /**
   * Controller constructor.
   *
   * @param model mutable model
   * @param view  view
   */
  public DungeonViewControllerImpl(Dungeon model, DungeonView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.model = model;
    this.view = view;
    // This controller can handle both kinds of events directly
    this.view.setListeners(this, this);
    //reset focus for frame to react to keyevent
    this.view.resetFocus();
    isRPressed = false;
    setReUseLocation();
  }

  @Override
  public void playGame(Dungeon m) {
    view.addClickListener(this);
    view.makeVisible();
  }

  @Override
  public void handlePanelClick(int direction) {
    if (!model.isGameOver()) {
      String result = "";
      switch (direction) {
        case 1:
          try {
            model.move(direction);
            result += "You moved to north\n";
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to north location.");
            view.refresh();
          }
          break;
        case 2:
          try {
            model.move(direction);
            result += "You moved to south\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to south location.");
            view.refresh();
          }
          break;
        case 3:
          try {
            model.move(direction);
            result += "You moved to east\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to east location.");
            view.refresh();
          }
          break;

        case 4:
          try {
            model.move(direction);
            result += "You moved to west\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to west location.");
            view.refresh();
          }
          break;
        default:
          break;
      }
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      //quit game
      case "Quit":
        System.exit(0);
        break;
      case "Settings":
        //prompt a setting dialog
        String input = view.createSettings();
        //start a new game
        if (input != null) {
          start(input);
          view.refresh();
          view.resetFocus();
        }
        break;
      case "Restart":
        restart();
        view.refresh();
        view.resetFocus();
        break;
      case "Information":
        //when game is over, no longer react to information action
        if (!model.isGameOver()) {
          view.setInformation(model.getPlayerDescription());
          // set focus back to main frame so that keyboard events work
          view.resetFocus();
          view.refresh();
        }
        break;
      default:
        throw new IllegalStateException("Error: Unknown button");
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //ignore
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //when game is over, no longer react to key event
    if (!model.isGameOver()) {
      int keyCode = e.getKeyCode();
      switch (keyCode) {

        //up key, move north
        case KeyEvent.VK_UP:
          try {
            String result = "";
            model.move(1);
            result += "You moved to north\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to north location.");
            view.refresh();
          }
          break;

        //down key, move south
        case KeyEvent.VK_DOWN:
          try {
            String result = "";
            model.move(2);
            result += "You moved to south\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to south location.");
            view.refresh();
          }
          break;

        //right key, move east
        case KeyEvent.VK_RIGHT:
          try {
            String result = "";
            model.move(3);
            result += "You moved to east\n";
            //get description about smell and combat
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to east location.");
            view.refresh();
          }
          break;

        //left key, move left
        case KeyEvent.VK_LEFT:
          try {
            String result = "";
            model.move(4);
            result += "You moved to west\n";
            result += getMoveDescription();
            //update view
            updateView(result);
          } catch (IllegalArgumentException iae) {
            //invalid move
            view.setOperationDescription("You can't go to west location.");
            view.refresh();
          }
          break;


        //press q, pick arrow
        case KeyEvent.VK_Q:
          String result = "";
          model.pickArrow();
          result += "You choose to pick arrows\n";
          //give operation result to view
          view.setOperationDescription(result);
          //update dungeon to draw
          view.updateDungeon();
          view.refresh();
          break;

        //press e, pick treasure
        case KeyEvent.VK_E:
          result = "";
          model.pickTreasure();
          result += "You choose to pick treasure\n";
          //give operation result to view
          view.setOperationDescription(result);
          //update dungeon to draw
          view.updateDungeon();
          view.refresh();
          break;

        //press W, shooting north
        case KeyEvent.VK_W:
          if (isRPressed) {
            executeShoot(1);
            //update isRpressed state
            isRPressed = false;
          }
          break;

        //press S, shooting south
        case KeyEvent.VK_S:
          if (isRPressed) {
            executeShoot(2);
          }
          break;

        //press E, shooting east
        case KeyEvent.VK_D:
          if (isRPressed) {
            executeShoot(3);
          }
          break;

        //press A, shooting west
        case KeyEvent.VK_A:
          if (isRPressed) {
            executeShoot(4);
          }
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //if game is over, no longer react to key released action
    if (!model.isGameOver()) {
      //R released
      if (e.getKeyCode() == KeyEvent.VK_R) {
        isRPressed = true;
      }
    }
  }

  //get description about smell and combat
  private String getMoveDescription() {
    String result = "";
    int combatResult;
    int stenchLevel;
    stenchLevel = model.getSmellLevel();
    //detect smell level
    if (stenchLevel == 1) {
      result += "You smell something terrible\n";
    } else if (stenchLevel == 2) {
      result += "The smell gets more terrible, be careful\n";
    }
    //player combat with Otyugh
    combatResult = model.combat();
    if (combatResult == 1) {
      result += "Chomp, chomp, chomp.\nYou are eaten by an Otyugh!\nBetter luck next time\n";
    } else if (combatResult == 2) {
      result += "The monster is injured\nyou're lucky to escape this time\n";
    }
    //winner
    if (model.isGameOver() && model.getPlayer().isAlive()) {
      result += "Congratulations, you win!\n";
    }
    return result;
  }

  //execute shoot and give pass result to view
  private void executeShoot(int direction) {
    boolean shootResult;
    String result = "";
    Object input = JOptionPane.showInputDialog(null,
            "Please select shooting distance", "Shooting", JOptionPane.PLAIN_MESSAGE,
            null, new String[]{"1", "2", "3", "4"}, "1");
    //cancel or close dialog, input will be null
    if (input != null) {
      int distance = Integer.parseInt((String) input);
      try {
        shootResult = model.shoot(direction, distance);
        result += getShootDescription(shootResult);
      } catch (IllegalStateException ise) {
        //player out of arrows
        view.setOperationDescription("You are out of arrows\nExplore to find more\n");
        view.refresh();
        //update isRpressed state
        isRPressed = false;
        return;
      }
      //give operation result to view
      view.setOperationDescription(result);
      //update dungeon to draw
      view.updateDungeon();
      view.refresh();
    }
    //update isRpressed state
    isRPressed = false;
  }

  //get shoot description
  private String getShootDescription(boolean result) {
    String description = "";
    description += "You shoot an arrow into the darkness\n";
    //shoot the Otyugh, give extra information
    if (result) {
      description += "You hear a great howl in the distance\n";
    }
    return description;
  }

  //pass operation result to view and update view.
  private void updateView(String result) {
    //give operation result to view
    view.setOperationDescription(result);
    //call view to update visited dungeon
    int x = model.getPlayer().getPosition().getX();
    int y = model.getPlayer().getPosition().getY();
    view.addDungeon(model.getLocations()[x][y], x, y);
    //refresh view
    view.refresh();
  }

  //prepare dungeon for reused
  private void setReUseLocation() {
    //get locations with defensive copies
    Location[][] dungeonLocation = model.getLocations();
    int x = dungeonLocation.length;
    int y = dungeonLocation[0].length;
    //prepare for reusing
    reuseLocation = new Location[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        reuseLocation[i][j] = dungeonLocation[i][j];
      }
    }
    //prepare for reset player
    reuseStart = model.getStartPosition();
    reuseEnd = model.getEndPosition();
  }

  //restart game
  private void restart() {
    //reset locations in model
    model.resetDungeon(reuseLocation);

    //reset start and end position
    model.resetStartCave(reuseStart);
    model.resetEndCave(reuseEnd);
    //need to reset start and end caves first,
    //since reset player need to replace player at start cave
    model.resetPlayer();

    //update reuse location for next restart
    setReUseLocation();
    //reset view
    view.restartGame();
  }

  //start new game
  private void start(String input) {
    String[] parameters = input.split("\n");
    //cast to real type for creating a new dungeon
    try {
      int x = Integer.parseInt(parameters[0]);
      int y = Integer.parseInt(parameters[1]);
      boolean wrapping = Boolean.parseBoolean(parameters[2]);
      int interconnectivity = Integer.parseInt(parameters[3]);
      int percentage = Integer.parseInt(parameters[4]);
      int monsterAmount = Integer.parseInt(parameters[5]);
      //create a new dungeon
      Dungeon newDungeon = new DungeonModel(x, y, wrapping,
              interconnectivity, percentage, monsterAmount);
      //reset locations in model
      model.resetDungeon(newDungeon.getLocations());

      //reset start and end position
      model.resetStartCave(newDungeon.getStartPosition());
      model.resetEndCave(newDungeon.getEndPosition());
      //need to reset start and end caves first,
      //since reset player need to replace player at start cave
      model.resetPlayer();

      //update reuse location for next restart
      setReUseLocation();
      //reset view
      view.restartGame();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid arguments to create a new dungeon.");
    }


  }
}
