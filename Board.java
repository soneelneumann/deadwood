import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Board{

   private Room trailers;
   //private room castingOffice; //superfluous?
   private ArrayList<Room> rooms;
   private ArrayList<Scene> scenes; //needs to be changed, I can't make this work
   private ArrayList<Player> players;
   
   private Stack<SceneCard> cardPile;
   
   /* Board initializer */
   public Board(ArrayList<SceneCard> cards){
      trailers = new Room();
      //castingOffice = new CastingOffice();
      rooms = new ArrayList<Room>();
      this.cardPile = new Stack<SceneCard>();
      
      Collections.shuffle(cards);
      
      for(SceneCard s: cards){
         this.cardPile.push(s);
      }
      players = new ArrayList<Player>();
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
   
   public ArrayList<Player> getPlayerList(){
      return new ArrayList<Player>(players);
   }
   
   public void addPlayer(Player player){
      players.add(player);
   }
   
   /* setter for rooms */
   public void setRoomList(ArrayList<Room> rooms){
      this.rooms = rooms;
   }
   
   /*adds passed scene to scenes*/
   public void addScene(Scene s){
      scenes.add(s);
   }
   
   /*
   public void setSceneList(ArrayList<Scene> scenes){
      this.scenes = scenes;
   }
   */
   
   public void setTrailers(Room trailers){
      this.trailers = trailers;
   }
   
   public Room getTrailers(){
      return trailers;
   }
   
   
   /*returns a Room if there is one which has a matching name to the passed string. Returns null if none is found*/
   public Room getRoom(String roomName){
      for(Room r: rooms){
         if(r.getName().equals(roomName)){
            return r;
         }
      }
      return null;
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
   
   public Room find(Player p){
      //find which room a given player is in
      for(Room r: rooms){
         if(r.isPlayerHere(p)){
            return r;
         }
      }
      
      return new Room(); //for compiler
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
      for(Room r : rooms){
         if(!(r.getName().equals("Trailers") || r.getName().equals("Casting Office"))){
            r.resetShotTokens();
            r.addSceneCard(cardPile.pop());
         }
      }
   }
   
   /*
   isDayOver()
   returns: boolean
   Precondtion: scene is over
   Postconditon: returns true if second to last scene, false if otherwise
   This method checks to see if the day is over
   */
   public boolean isDayOver(){
      int remainingScenes = 0;
      for(Room r: rooms){
         if(r.getSceneCard() != null){
            remainingScenes ++;
         }
      }
      if(remainingScenes > 1){
         return false;
      }
      return true;
   }
}