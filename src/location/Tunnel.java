package location;

/**
 * This class represents a tunnel. It offers method
 * to create a tunnel object.
 */
public class Tunnel extends AbstractLocation {

  /**
   * Create a tunnel object using the given index and doors status.
   *
   * @param northDoor the status of north door
   * @param southDoor the status of south door
   * @param eastDoor  the status of east door
   * @param westDoor  the status of west door
   */
  public Tunnel(boolean northDoor, boolean southDoor, boolean eastDoor, boolean westDoor) {
    super(northDoor, southDoor, eastDoor, westDoor);
  }
}
