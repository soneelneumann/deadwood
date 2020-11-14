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