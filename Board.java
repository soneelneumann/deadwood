import java.util.ArrayList;
import java.util.Collections;

public class Board{

   private Room trailers;
   //private room castingOffice; //superfluous?
   private ArrayList<Room> rooms;
   private ArrayList<Scene> scenes; //needs to be changed, I can't make this work
   
   /* Board initializer */
   public Board(){
      trailers = new Room();
      //castingOffice = new CastingOffice();
      rooms = new ArrayList<Room>();
   }
   
   /* Board initializer */
   /*
   public Board(){
      scenes = new ArrayList<Scene>();
      trailers = new Trailers();
      castingOffice = new CastingOffice();
      
      rooms = new ArrayList<Room>();
      
      //copy contents of scenes into rooms
      Collections.copy(rooms, scenes);
      
      //add trailers and casting office to rooms
      rooms.add(trailers);
      rooms.add(castingOffice);
      
   }
   */
   
   /* setter for rooms */
   public void setRoomList(ArrayList<Room> rooms){
      this.rooms = rooms;
   }
   
   /*
   public void setSceneList(ArrayList<Scene> scenes){
      this.scenes = scenes;
   }
   */
   
   public void setTrailers(Room trailers){
      this.trailers = trailers;
   }
   
   /*
   public void setOffice(CastingOffice castingOffice){
      this.castingOffice = castingOffice;
   }
   */
   
   /*adds a room to roomList*/
   /*
   public void addRoom(Room r){
      rooms.add(r);
   }
   */
   
   /*
   find(Player player)
   returns: Room()
   Precondition: the board needs to find the player
   Postcondition: the board identifies the player and their location.
   This method parses through the rooms and checks to see which room the player is in and returns that room.
   */
   /*
   public Room find(Player p){
      //find which room a given player is in
      for(Room r: rooms){
         if(r.isPlayerHere(p)){
            return r;
         }
      }
      
      return new Room(); //for compiler
   }
   */
   
   /*
   clearBoard()
   returns: null
   Precondition: the day is over.
   Postcondtion: the board is cleared of all scene cards and shot tokens
   This method tidies up the board, getting rid of all the scene cards and tokens.
   */
   public void clearBoard(){
      //clear the board
      for(Scene s: scenes){
         //clear all tokens
         s.setShotTokens(0); //remove all shot tokens
         //clear scene card
         s.removeSceneCard();
      }
   }
   
   /*
   resetBoard()
   returns: null
   Precondition: board is cleared
   Postcondition: players are in their trailers, new scene cards are placed and shot tokens reset.
   This method sets the board back up for the next day to be played.
   */
   public void resetBoard(){
      //put all players in the trailers
      for(Scene s: scenes){
         
      }
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