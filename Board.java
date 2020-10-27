import java.util.ArrayList;

public class Board{
   
   private ArrayList<Scene> scenes;
   private Trailers trailers;
   private CastingOffice castingOffice;
   
   public Board(){
      scenes = new ArrayList<Scene>();
      trailers = new Trailers();
      castingOffice = new CastingOffice();
   }
   
   public Room find(Player player){
      //find which room a given player is in
      return new Room(); //temp
   }
   
   public void clearBoard(){
      //clear the board
   }
   
   public void resetBoard(){
      //put all players in the trailers
      //reset tokens and replenish scene cards
   }
   
   public boolean isDayOver(){
      //if only one scene remains, return true. otherwise, return false
      return false; //temp
   }
}