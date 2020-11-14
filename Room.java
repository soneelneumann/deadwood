/*
Chris Brown, Soneel Neumann

Base Room class for all rooms on the Deadwood board. Not instantiated, but forms the framework for responsibilities.
*/

import java.util.ArrayList;
public class Room{
   
   private String roomName;
   
   private ArrayList<Player> occupants;
   
   private ArrayList<Room> neighbors;
   
   /* Room initializer */
   public Room(){
      
      occupants = new ArrayList<Player>();
      neighbors = new ArrayList<Room>();
      roomName = "";
   }
   
   
   /* toString method, used in printing for tests mostly */
   public String toString(){
      
      return roomName;
   }
   
   /*room initializer, with roomName*/
   public Room(String roomName){
      occupants = new ArrayList<Player>();
      neighbors = new ArrayList<Room>();
      this.roomName = roomName;
   }
   
   public ArrayList<Room> getNeighbors(){
      return new ArrayList<Room>(neighbors);
   }
   
   /*
   addNeighbor()
   params: 
      room: room we're adding
   returns: none
   precond: room is not already a neighbor
   Adds a room as a neighbor
   */
   public void addNeighbor(Room room){
      neighbors.add(room);
   }
   
   /*getter for the room name*/
   public String getName(){
      return roomName; 
   }
   
   /*setter for the room name*/
   public void setName(String roomName){
      this.roomName = roomName;
   }
   
   /*
   addOccupant(Player player)
   params: 
      player: player we're adding
   returns: none
   precond: player is not currently in room
   adds player to the list of occupants
   */
   public void addOccupant(Player player){
      occupants.add(player);
   }
   
   /*
   removeOccupant(Player player)
   params: 
      p: player we're removing
   returns: none
   precond: player is in this room
   removes a player from occupants
   */
   public void removeOccupant(Player player){
      occupants.remove(player);
   }
   
   /* 
   getOccupants()
   returns: ArrayList
   precondition: There are player(s) in a room
   identifies all the players that are in the room
   */
   public ArrayList<Player> getOccupants(){
      //return shallow copy of occupants
      return new ArrayList<Player>(occupants); 
   }
   
   
   /* 
   isPlayerHere(Player player)
   params: 
      p: player we are looking for
   returns: boolean
   precondtion: the board needs to keep track of the player's position
   returns true if player is in occupants, returns false otherwise
   */
   public boolean isPlayerHere(Player player){
      if(occupants.contains(player)){
         return true;
      }
      return false; 
   }
   
   //The rest of these are base methods, to solve class inheritance conflicts
   
   /*returns null, since bare rooms have no roles*/
   public Role getRole(String s){
      return null;
   }
   
   /*returns null, since bare rooms do not have prices*/
   public int[] getRankMoneyPrices(){
      return null;
   }
   
   /*returns null, since bare rooms do not have prices*/
   public int[] getRankCreditPrices(){
      return null;
   }
   
   /*returns null, since bare rooms do not have prices*/
   public ArrayList<Role> getRoles(){
      return null;
   }
   
   /*returns null, since bare rooms do not have scene cards*/
   public SceneCard getSceneCard(){
      return null;
   }
   
   
   /*returns 0, because bare rooms do not have shot tokens*/
   public int getShotTokens(){
      return 0;
   }
   
   /*Does nothing, since blank rooms do not have shot tokens*/
   public void resetShotTokens(){
      System.out.println("Tried to access shot tokens when there are none.");
   }
   
   /*Does nothing, since blank rooms cannot have scene cards*/
   public void addSceneCard(SceneCard s){
      System.out.println("Tried to add scene card where no slot was present.");
   }
   
   /*Returns 0, since bare rooms have no shot tokens*/
   public int getMaxShotTokens(){
      return 0;
   }
   
   /*does nothing, bare rooms have no shot tokens*/
   public void setShotTokens(int shotTokens){
      //System.out.println("You tried to set shot tokens when there are no places for any.");
   }
   
   /*does nothing, bare scenes do not wrap up*/
   public void removeSceneCard(){
      //System.out.println("You tried to remove the scene card when there was none.");
   }
}