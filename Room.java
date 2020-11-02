import java.util.ArrayList;
public class Room{
   
   private ArrayList<Player> occupants;
   
   private ArrayList<Room> neighbors;
   
   /* Room initializer */
   public Room(){

      occupants = new ArrayList<>();
      neighbors = new ArrayList<Room>();

      occupants =  new ArrayList<>(); 
      
      /*
      goUp = null; //door going up
      goDown = null; //door going down
      goLeft = null; //door going left
      goRight = null; //door going right
      */
   }
   
   /* 
   getOccupants()
   Returns: ArrayList
   Precondition: There are player(s) in a room
   Postcondition: The player(s) are identified and returned
   This method identifies all the players that are in the room
   */
   public ArrayList<Player> getOccupants(){
      return (ArrayList<Player>)occupants.clone();
   }
   
   public boolean isPlayerHere(Player p){
      
      for(Player q : occupants){
         // if p == q, return true
      }
      return false; //not temp, jsut convernient
   }
   
   /* 
   isPlayerHere()
   Returns: boolean
   Precondtion: the board needs to keep track of the player's position
   Postcondtion: the players position is found
   This method checks where the player's postion is, as in, what room they are in
   */
   public boolean isPlayerHere(){
      return false; //temp
   }
}