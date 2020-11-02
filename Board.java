import java.util.ArrayList;

public class Board{
   
   private ArrayList<Scene> scenes;
   private Trailers trailers;
   private CastingOffice castingOffice;
   
   /* Board initializer */
   public Board(){
      scenes = new ArrayList<Scene>();
      trailers = new Trailers();
      castingOffice = new CastingOffice();
   }
   
   /*
   find(Player player)
   returns: Room()
   Precondition: the board needs to find the player
   Postcondition: the board identifies the player and their location.
   This method parses through the rooms and checks to see which room the player is in and returns that room.
   */
   public Room find(Player player){
      //find which room a given player is in
      return new Room(); //temp
   }
   
   /*
   clearBoard()
   returns: null
   Precondition: the day is over.
   Postcondtion: the board is cleared of all scene cards and shot tokens
   This method tidies up the board, getting rid of all the scene cards and tokens.
   */
   public void clearBoard(){
      //clear the board
   }
   
   /*
   resetBoard()
   returns: null
   Precondition: board is cleared
   Postcondition: players are in their trialers, new scene cards are placed and shot tokens reset.
   This method sets the board back up for the next day to be played.
   */
   public void resetBoard(){
      //put all players in the trailers
      //reset tokens and replenish scene cards
   }
   
   /*
   isDayOver()
   returns: boolean
   Precondtion: scene is over
   Postconditon: returns true if second to last scene, false if otherwise
   This method checks to see if the day is over
   */
   public boolean isDayOver(){
      //if only one scene remains, return true. otherwise, return false
      return false; //temp
   }
}