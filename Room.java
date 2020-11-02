import java.util.ArrayList;
public class Room{
   
   private ArrayList<Player> occupants;
   
   private ArrayList<Room> neighbors;
   
   public Room(){
      occupants = new ArrayList<>();
      neighbors = new ArrayList<Room>();
   }
   
   public ArrayList<Player> getOccupants(){
      return (ArrayList<Player>)occupants.clone();
   }
   
   public boolean isPlayerHere(Player p){
      
      for(Player q : occupants){
         // if p == q, return true
      }
      return false; //not temp, jsut convernient
   }
}