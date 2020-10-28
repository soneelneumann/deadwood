public class Player{
   
   //name of the Player
   public String playerName;
   
   //number of credits this player has
   private int credits;
   
   //number of dollars this player has
   private int money;
   
   //rank of the given player
   private int rank;
   
   //number of practice tokens this player has
   private int practiceTokens;
   
   //player's current role, null if they are not in a scene role
   private Role currentRole;
   
   /*
      Player initializer
   */
   public Player(){
      //make everything default values
   }
   
   /*Player initializer, given player's name*/
   public Player(String playerName){
      //initialize playerName
      //everything else default
   }
   
   /*Player initializer, given player's name, their money and their credits*/
   public Player(String playerName, int money, int credits){
      //initalize playerName, money, credits
      //everything else default
   }
   
   /*
      move(Room location)
      parameters: Room location
      returns: none
      
      attempts to move player to the given Room
   */
   public void move(Room location){
      //move to a room
   }
   
   /*
      purchaseRank(int rank)
      parameters: int rank
      returns: none
      
      attempts to purchase a certain rank, based on int given
   */
   public void purchaseRank(int rank){
      //purchase a rank
   }
   
   /*
      act(Scene scene)
      returns: none
      description: player attempts to act in a scene
      precond: Player must have a Role in a scene
      postcond: Player succeeds or fails in acting
      
   */
   public void act(Scene scene){
      //act in a scene
   }
   
   
   /*
      addPracticeToken()
      returns: none
      description: adds a Practice Token to the player's total tokens
      precondition: player must have a Role in a scene
      postcondition: practiceTokens increases by 1
   */
   public void addPracticeToken(){
      //add a practice token
   }
   
   /*
      rehearse()
      returns: none
      description: player attempts to rehearse in a scene
      precondition: 
      postcondition: player either gets a shot token or is forced to act
   */
   public void rehearse(){
         
   }
   
   /*
      removePracticeTokens()
      returns: none
      description: player is removed of all practice tokens
      precondition: player has 1 or more practice tokens 
      postcondition: all practice tokens are removed
   */
   public void removePracticeTokens(){
      //remove all practice tokens
   }
   
}