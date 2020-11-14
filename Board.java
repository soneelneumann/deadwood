/*
Soneel Neumann and Chris Brown
Board class, keeps track of players, scenes, rooms, and is responsible for clearing/reseting the board
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Board{

   private Room trailers;
   private ArrayList<Room> rooms;
   private ArrayList<Scene> scenes; 
   private ArrayList<Player> players;
   
   private Stack<SceneCard> cardPile;
   
   /* Board initializer */
   public Board(ArrayList<SceneCard> cards){
      trailers = new Room();
      rooms = new ArrayList<Room>();
      this.cardPile = new Stack<SceneCard>();
      
      Collections.shuffle(cards);
      
      for(SceneCard s: cards){
         this.cardPile.push(s);
      }
      players = new ArrayList<Player>();
   }
   
   /* getter for players list */
   public ArrayList<Player> getPlayerList(){
      return new ArrayList<Player>(players);
   }
   
   /* adds player to players list */
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
   
   /* setter for trailers */
   public void setTrailers(Room trailers){
      this.trailers = trailers;
   }
   
   /* getter for trailers */
   public Room getTrailers(){
      return trailers;
   }
   
   
   /*
   getRoom(String roomName)
   returns:Room()
   Precondition: Board needs to get room
   This returns a room given the room's name.
   */
   public Room getRoom(String roomName){
      for(Room r: rooms){
         if(r.getName().equals(roomName)){
            return r;
         }
      }
      return null;
   }

   /*
   find(Player player)
   returns: Room()
   Precondition: the board needs to find the player
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
   This method tidies up the board, getting rid of all the scene cards and tokens.
   */
   public void clearBoard(){
      //clear the board
      for(Room r: rooms){
         //clear all tokens
         r.setShotTokens(0); //remove all shot tokens
         //clear scene card
         r.removeSceneCard();
      }
   }
   
   /*
   resetBoard()
   returns: null
   Precondition: board is cleared
   This method sets the board back up for the next day to be played.
   */
   public void resetBoard(){
      //put all players in the trailers
      for(Room r : rooms){
         if(!(r.getName().equals("Trailers") || r.getName().equals("Casting Office"))){
            r.resetShotTokens();
            r.addSceneCard(cardPile.pop());
            for(Role role : r.getRoles()){
               role.setIsTaken(false);
            }
         }
         for(Player player : r.getOccupants()){
            player.move(trailers);
            player.setCurrentRole(null);
         }
      }
   }
   
   /*
   isDayOver()
   returns: boolean
   Precondtion: scene is over
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