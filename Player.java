import java.util.Scanner;

public class Player{
   
   //name of the Player
   public int playerNumber;
   
   //number of credits this player has
   private int credits = 0;
   
   //number of dollars this player has
   private int money = 0;
   
   //rank of the given player
   private int rank = 1;
   
   //number of practice tokens this player has
   private int practiceTokens = 0;
   
   //player's current role, null if they are not in a scene role
   private Role currentRole;
   
   //room the player is currently in 
   private Room currentRoom;
   
   /*
      Player initializer
   */
   public Player(){
      //make everything default values
   }
   
   /*Player initializer, given player's name*/
   public Player(int playerNumber){
      this.playerNumber = playerNumber;
   }
   
   /*Player initializer, given player's name, their money and their credits*/
   public Player(int playerNumber, int money, int credits, int rank){
      this.playerNumber = playerNumber;
      this.money = money;
      this.credits = credits;
      this.rank = rank;
   }

   public void setPlayerNum(int playerNum){
      this.playerNumber = playerNum;
   }

   public void setRank(int pRank){
      this.rank = pRank;
   }
   
   /*
   getter for the currentRoom
   */
   public Room getCurrentRoom(){
      return currentRoom;
   }
   
   public Role getCurrentRole(){
      return currentRole;
   }
   
   public void setCurrentRole(Role currentRole){
      this.currentRole = currentRole;
   }
   
   public void setMoney(int pMoney){
      this.money = pMoney;
   }

   public void setCredits(int pCredits){
      this.credits = pCredits;
   }

   public int getCredits(){
      return this.credits;
   }

   public int getMoney(){
      return this.money;
   }

   public int getRank(){
      return this.rank;
   }

   public int getPlayerNumber(){
      return this.playerNumber;
   }
   public void setPracticeTokens(int pTokens){
      this.practiceTokens = pTokens;
   }
   public int getPracticeTokens(){
      return this.practiceTokens;
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

   //logically so wrong
   public void purchaseRank(int rank){
      String failed = "Purchase failed";
      if(rank == 2){
         if((getMoney() >= 4) || (getCredits() >= 5)){ //NEED TO ASK FOR CREDITS OR MONEY, MAYBE 2 SEPARATE METHODS? purhcaseRankCredits and purchaseRankMoney
            setMoney(getMoney() - 4);
            setCredits(getCredits() - 5);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 3){
         if((getMoney() >= 10) || (getCredits() >= 10)){
            setMoney(getMoney() - 10);
            setCredits(getCredits() - 10);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 4){
         if((getMoney() >= 18) || (getCredits() >= 15)){
            setMoney(getMoney() - 18);
            setCredits(getCredits() - 15);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 5){
         if((getMoney() >= 28) || (getCredits() >= 20)){
            setMoney(getMoney() - 28);
            setCredits(getCredits() - 20);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 6){
         if((getMoney() >= 40) || (getCredits() >= 25)){
            setMoney(getMoney() - 40);
            setCredits(getCredits() - 25);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else{
         System.out.println("Rank isn't offered");
      }
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
      setPracticeTokens(getPracticeTokens() +1);
   }
   
   /*
      rehearse()
      returns: none
      description: player attempts to rehearse in a scene
      precondition: 
      postcondition: player either gets a shot token or is forced to act
   */
   public void rehearse(Scene scene, SceneCard sceneCard){
         if(getPracticeTokens() == sceneCard.getSceneBudget()){
            act(scene);
         }
         else{
            addPracticeToken();
         }
   }
   
   /*
      removePracticeTokens()
      returns: none
      description: player is removed of all practice tokens
      precondition: player has 1 or more practice tokens 
      postcondition: all practice tokens are removed
   */
   public void removePracticeTokens(){
      this.practiceTokens = 0;
   }
   
   /*
      takeRole()
      returns: boolean
      description: player attempts to take a role in a scene
      precondition: it is this player's turn
      postcondition: player returns boolean for success(t) or failure(f)
   */
   public boolean takeRole(Role r){
      //try to take a role
      //Moderator m = new Moderator(); //temp
      return false; //temp
   }
   
}