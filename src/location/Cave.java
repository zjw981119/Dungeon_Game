package location;

import monster.Monster;
import treasure.Treasure;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cave. It offers methods
 * to create a cave object and get treasure at this location.
 */
public class Cave extends AbstractLocation {
  private List<Treasure> treasureList;
  private List<Monster> monsterList;

  /**
   * Create a cave object using the given index and doors status.
   *
   * @param northDoor the status of north door
   * @param southDoor the status of south door
   * @param eastDoor  the status of east door
   * @param westDoor  the status of west door
   */
  public Cave(boolean northDoor, boolean southDoor,
              boolean eastDoor, boolean westDoor) {
    super(northDoor, southDoor, eastDoor, westDoor);
    treasureList = new ArrayList<>();
    monsterList = new ArrayList<>();
  }

  @Override
  public List<Monster> getMonster() {
    //cannot use defensive copy, need to update monster's health in dungeon
    //TODO using defensive copy
    return monsterList;
    //defensive copy
    //    List<Monster> tmpList = new ArrayList<>();
    //    for (Monster m : monsterList) {
    //      String name = m.getName();
    //      int health = m.getHealth();
    //      if (health == 2) {
    //        tmpList.add(new Otyugh(name));
    //      } else {
    //        Otyugh tmpMonster = new Otyugh(name);
    //        tmpMonster.reduceHealth();
    //        tmpList.add(tmpMonster);
    //      }
    //      }
  }

  @Override
  public void addMonster(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }
    monsterList.add(monster);
  }

  @Override
  public void removeMonster() {
    monsterList.clear();
  }


  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> tmpList = new ArrayList<>();
    //defensive copy, since treasure is immutable
    for (Treasure t : treasureList) {
      tmpList.add(t);
    }
    return tmpList;
  }

  @Override
  public void addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Argument can not be null");
    }
    treasureList.add(treasure);
  }

  @Override
  public void removeTreasure() {
    treasureList.clear();
  }
}
