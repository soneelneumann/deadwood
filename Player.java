/*
Soneel Neumann and Chris Brown
Player class, keeps track of players' rank, credits, number, money, practice tokens, role, and room they're in
*/

import java.util.Scanner;

public class Player{
   
   //name of the Player
   public String playerName;
   
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
   
   /*Player initializer, given player's num*/
   public Player(String playerName){
      this.playerName = playerName;
   }
   
   /*Player initializer, given player's num, their money and their credits, and rank*/
   public Player(String playerName, int money, int credits, int rank){
      this.playerName = playerName;
      this.money = money;
      this.credits = credits;
      this.rank = rank;
   }
   
   /* setter for player's rank */
   //used at the start of the game if there are 7 or 8 players
   public void setRank(int rank){
      this.rank = rank;
   }
   
   /* getter for player's current room */
   public Room getCurrentRoom(){
      return currentRoom;
   }
   
   /* getter for player's current role */
   public Role getCurrentRole(){
      return currentRole;
   }
   
   /* setter for player's current role */
   public void setCurrentRole(Role currentRole){
      this.currentRole = currentRole;
   }
   
    /* setter for player's current money */
   public void setMoney(int pMoney){
      this.money = pMoney;
   }
   
    /* adds credits to player */
   public void addCredits(int credits){
      this.credits += credits;
   }
   
   /* adds money to player */
   public void addMoney(int money){
      this.money += money;
   }
   
   /* setter for player's credits */
   public void setCredits(int credits){
      this.credits = credits;
   }

   /* getter for player's credits */
   public int getCredits(){
      return this.credits;
   }

   /* getter for player's money */
   public int getMoney(){
      return this.money;
   }

   /* getter for player's rank */
   public int getRank(){
      return this.rank;
   }

   /* setter for player's practice tokens */
   public void setPracticeTokens(int pTokens){
      this.practiceTokens = pTokens;
   }
   
   /* getter for player's practice tokens */
   public int getPracticeTokens(){
      return this.practiceTokens;
   }

   
   /*
      move(Room location)
      parameters: Room location
      returns: none
      precond: moving to destination is a valid in game move
      attempts to move player to the given Room
   */
   public void move(Room destination){
      Room temp = currentRoom;
      currentRoom = destination;
      if(temp != null){
         temp.removeOccupant(this);
      }
      currentRoom.addOccupant(this);
   }
   
   /*
      purchaseRankCredits(int rank)
      parameters: int rank
      returns: none
      attempts to purchase a certain rank with credits, based on int given
   */

   public void purchaseRankCredits(int rank){
      String failed = "Purchase failed, insufficient funds.";
      if(rank == 2){
         if(this.credits >= 5){ 
            setCredits(this.credits - 5);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 3){
         if(this.credits >= 10){
            setCredits(this.credits - 10);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 4){
         if(this.credits >= 15){
            setCredits(this.credits - 15);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 5){
         if(this.credits >= 20){
            setCredits(this.credits - 20);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 6){
         if(this.credits >= 25){
            setCredits(this.credits - 25);
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
      purchaseRankCredits(int rank)
      parameters: int rank
      returns: none
      attempts to purchase a certain rank with money, based on int given
   */

   public void purchaseRankMoney(int rank){
      String failed = "Purchase failed, insufficient funds.";
      if(rank == 2){
         if(this.money >= 4){ 
            setMoney(this.money - 4);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 3){
         if(this.money >= 10){
            setMoney(this.money - 10);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 4){
         if(this.money >= 18){
            setMoney(this.money - 18);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 5){
         if(this.money >= 28){
            setMoney(this.money - 28);
            setRank(rank);
         }
         else{
            System.out.println(failed);
         }
      }
      else if(rank == 6){
         if(this.money >= 40){
            setMoney(this.money - 40);
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
      returns: boolean
      precond: Player must have a Role in a scene
      description: player attempts to act in a scene
   */
   public boolean act(Scene scene){
      Dice d = new Dice();

      if((d.rollAct(this.practiceTokens) >= scene.getSceneCard().getSceneBudget())){
         scene.setShotTokens(scene.getShotTokens() - 1);
         return true;
      }
      return false;
   }
   
   
   /*
      addPracticeToken()
      returns: none
      precondition: player must have a Role in a scene
      description: adds a Practice Token to the player's total tokens
   */
   public void addPracticeToken(){
      setPracticeTokens(this.practiceTokens +1);
   }
   
   /*
      rehearse()
      returns: none
      precondition: 
      description: player attempts to rehearse in a scene
   */
   public void rehearse(Scene scene, SceneCard sceneCard){
         if(this.practiceTokens == sceneCard.getSceneBudget()){
            act(scene);
         }
         else{
            addPracticeToken();
         }
   }
   
   /*
      removePracticeTokens()
      returns: none
      precondition: player has 1 or more practice tokens 
      description: player is removed of all practice tokens
   */
   public void removePracticeTokens(){
      this.practiceTokens = 0;
   }
   
   /*
      takeRole()
      returns: 
      precondition: it is this player's turn
      description: player attempts to take a role in a scene
   */
   public void takeRole(Role r){
      setCurrentRole(r);
      r.setIsTaken(true);
   }
   
}