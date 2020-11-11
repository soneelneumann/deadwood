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
   
   
   /*toString method, used in printing for tests mostly*/
   public String toString(){
      /*
      String output = "";
      
      output += ("Name: " + roomName);
      output += ("Neighbors: \n");
      for(Room r: neighbors){
         output += (r.roomName + "\n");
      }
      
      return output;
      */
      
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
      params: Room r, room we're adding
      returns: none
      precond:
      postcond: room is added as a neighbor
   */
   public void addNeighbor(Room r){
      neighbors.add(r);
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
      addOcupant()
      params: Player p, player we're adding
      returns: none
      precond: player is not currently in room
      postcond: player is added to occupants
   */
   public void addOccupant(Player p){
      occupants.add(p);
   }
   
   /*
      removeOccupant()
      params: Player p, player we're removing
      returns: none
      precond: player is in this room
      postcond: player is removed from occupants
   */
   public void removeOccupant(Player p){
      occupants.remove(p);
   }
   
   /* 
   getOccupants()
   Returns: ArrayList
   Precondition: There are player(s) in a room
   Postcondition: The player(s) are identified and returned
   This method identifies all the players that are in the room
   */
   public ArrayList<Player> getOccupants(){
      //return shallow copy of occupants
      return new ArrayList<Player>(occupants); 
   }
   
   /* 
   isPlayerHere()
   params: Player p, player we are looking for
   Returns: boolean
   Precondtion: the board needs to keep track of the player's position
   Postcondtion: the players position is found
   This method checks where the player's postion is, as in, what room they are in
   */
   public boolean isPlayerHere(Player p){
      if(occupants.contains(p)){
         return true;
      }
      return false; 
   }
   
   
}