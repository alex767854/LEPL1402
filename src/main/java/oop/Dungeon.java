package oop;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    // TODO
    // You can add new fields and methods here.
    public String[][] grid;
    public List<Dungeon> doors = new ArrayList<>();


    /**
     * A dungeon is a rectangular room of a certain dimension.
     * For example, this is the shape of a dungeon of dimension 6 x 3:
     *
     *   ---------> x
     *  |
     *  |  ######
     *  |  ######
     *  |  ######
     *  v
     *  y
     *
     * The horizontal arrow represents the x-axis. The vertical arrow
     * represents the y-axis.
     * You can assume that dimensionX>0 and dimensionY>0.
     **/
    Dungeon(int dimensionX, int dimensionY) {
        // TODO
        // Write your code here
        this.grid = new String[dimensionY][dimensionX];
        for(int i =0;i<dimensionY;i++){
            for (int j = 0; j<dimensionX;j++){
                grid[i][j]="#";
            }
        }
    }

    /*
     A dungeon is an empty room at the beginning.
     You can put objects at the specified position in the dungeon
     using the putTrap(), putHealthFountain(), and putDoor() methods.
     The position is a coordinate pair (x,y).
     The position (0,0) refers to the top left corner
     of the dungeon, as indicated below by the "O":

         O#####    x=0,y=0
         ######
         ######

     And the following example shows the position (3,1):

         ######
         ###O##    x=3,y=1
         ######

     A dungeon can contain many objects, for example doors and traps. But
     there can only be one object at a specific position. If you put another
     object at the same position, the old object at that position must be removed.
    */

    /**
     * This class represents the player. The player is in a dungeon
     * at some position. The player has health points.
     * You don't need to modify this class.
     * You can assume that the player starts the game at an empty
     * position (without objects) in the dungeon.
     */
    public static class Player {
        public Dungeon dungeon;    // The dungeon in which the player currently is.
        public int x, y;           // The position of the player in the dungeon.
        public int healthPoints;   // The health points of the player.

        public Player(Dungeon dungeon, int x, int y, int healthPoints) {
            this.dungeon = dungeon;
            this.x = x;
            this.y = y;
            this.healthPoints = healthPoints;
        }
    }

    /**
     * Puts a trap at the specified position in this dungeon.
     * If there is already an object at that position, that old object
     * is removed.
     * You can assume that the coordinates are valid, i.e., inside the dungeon.
     */
    void putTrap(int x, int y) {
        // TODO
        // Write your code here
        grid[y][x]="trap";
    }

    /**
     * Puts a health fountain at the specified position in this dungeon.
     * If there is already an object at that position, that old object
     * is removed.
     * The parameter _hf_ gives the "strength" of the health fountain.
     * You can assume that _hf_ is a positive value and that the
     * coordinates are valid, i.e., inside the dungeon.
     */
    void putHealthFountain(int x, int y, int hf) {
        // TODO
        // Write your code here
        String buff = String.valueOf(hf);
        grid[y][x]="heal "+ buff;
    }

    /**
     * Puts a door to another dungeon at the specified position in this
     * dungeon. If there is already an object at that position, that
     * old object is removed.
     * You can assume that otherDungeon!=null and that the coordinates
     * are valid, i.e., inside the dungeon.
     */
    void putDoor(int x, int y, Dungeon otherDungeon) {
        // TODO
        // Write your code here
        doors.add(otherDungeon);
        grid[y][x]="door "+ String.valueOf(doors.size()-1);
    }

    public enum Direction { North, West, South, East }

    /**
     * Move the player in the indicated direction.
     *
     *         North               +-------> X
     *           |                 |
     *   West <--+--> East         |  The dungeon
     *           |                 |
     *         South               v
     *                             Y
     *
     *
     * If the move brings the player to a position that contains a trap,
     * health fountain, or door, the move and the respective action for
     * that object must be executed:
     *
     * - If the player steps on a trap, he/she loses 1 health point.
     * - If the player steps on a health fountain, his/her health increases
     *   by _hf_ health points (i.e., the "strength" of the health fountain).
     * - If the player steps on a door, he/she is moved to position (0,0)
     *   in the other dungeon to which the door leads. You can assume that
     *   the new position is empty, i.e., there is no object at position (0,0)
     *   of the other dungeon.
     *
     *  You can directly access the position and health points of the player.
     *
     *  You can assume that the move is valid. For example, the player will not
     *  try to move to the North if the current position is y=0.
     */
    public static void movePlayer(Player player, Direction direction) {
        // TODO
        // Write your code here

        if (direction.equals(Direction.North))player.y--;
        else if (direction.equals(Direction.South))player.y++;
        else if (direction.equals(Direction.East))player.x++;
        else if (direction.equals(Direction.West))player.x--;
        String cell = player.dungeon.grid[player.y][player.x];
        if(cell.equals("trap")) player.healthPoints--;
        else if (cell.split(" ")[0].equals("heal")) player.healthPoints += Integer.parseInt(cell.split(" ")[1]);
        else if (cell.split(" ")[0].equals("door")){
            player.dungeon = player.dungeon.doors.get(Integer.parseInt(cell.split(" ")[1]));
            player.y =0;
            player.x =0;
        }
    }
}
