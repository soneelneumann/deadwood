import java.util.ArrayList;
public class Room{
   
   private ArrayList<Player> occupants;
   private Room goUp;
   private Room goDown;
   private Room goLeft;
   private Room goRight;
   
   public Room(){
      occupants = new ArrayList<>();
      
      goUp = null; //door going up
      goDown = null; //door going down
      goLeft = null; //door going left
      goRight = null; //door going right
   }
   
   public ArrayList<Player> getOccupants(){
      //return occupants.clone();
      return new ArrayList<Player>(); //temp
   }
   
   public boolean isPlayerHere(){
      return false; //temp
   }
}