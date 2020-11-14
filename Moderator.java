/*
Soneel Neumann, Chris Brown

Moderator class for Deadwood. Controls the checking of player actions and verifies when the day is over.
*/

import java.util.ArrayList;
import java.util.List;
public class Moderator{
   
   private int daysLeft;
   private int maxDaysLeft;
   
   /*Moderator initializer*/   
   public Moderator(int daysLeft){
      this.daysLeft = daysLeft;
      this.maxDaysLeft = daysLeft;
      //initialize
   }
   
   
   /*
      checkMove(Room start, String dest)
      params:
         start: room player starts in
         dest: room they are moving to
      returns: boolean- true if valid move, false otherwise
      precond: it is currently player's turn, 
         and they are about to move but haven't yet
      postcond: move is checked for legality
   */
   public boolean checkMove(Room start, String dest){
      //
      for(Room r: start.getNeighbors()){
         if(r.getName().equals(dest)){
            return true;
         }
      }
      return false;
   }
   
   /*
      checkRankUp(Player player, int rankRequested, String currencyType)
      params:
         p: player requesting rank
         rankRequested: rank they wish to purhcase
         currencyType: either "c" or "m" for credits or money
      returns: boolean, true if player can purchase, false otherwise
      
      precond: currently player's turn, currencyType == "credits" or "money"
      postcond: validity of request is returned
   */
   public boolean checkRankUp(Player player, int rankRequested, String currencyType){
      //
      if(player.getRank() == 6){
            System.out.println("Your rank is too low");
            return false;
      }
      if(currencyType.equals("credits")){
         
         if(player.getCurrentRoom().getRankCreditPrices() == null){
            System.out.println("You can only purchase a rank in the casting office.");
            return false;
         }
         else if(player.getCredits() < player.getCurrentRoom().getRankCreditPrices()[rankRequested - 2]){
            System.out.println("You do not have enough credits for this rank");
            return false;
         }
         return true;
      }
      else{
         if(player.getCurrentRoom().getRankMoneyPrices() == null){
            System.out.println("You can only purchase a rank in the casting office.");
            return false;
         }
         else if(player.getMoney() < player.getCurrentRoom().getRankMoneyPrices()[rankRequested - 2]){
            System.out.println("You do not have enough money for this rank");
            return false;
         }
         return true;
      }
   }
   
   /*
      checkAct(Player player)
      params: 
         p: 
      returns: boolean
      checks if the player is succesful in acting in their scene
   */
   public boolean checkAct(Player player){
      Dice d = new Dice();
      
      int bonus = player.getPracticeTokens();
      int result = d.rollAct(bonus);
      
      if(result < player.getCurrentRoom().getSceneCard().getSceneBudget()){
         return false;
      }
      player.getCurrentRoom().setShotTokens(player.getCurrentRoom().getShotTokens() - 1);
      return true;
   }
   
   /*
   checkRehearse(Player player)
   returns: boolean
   parameters:
      player: player trying to rehearse for a scene
   precond: player is in a role
   checks whether or not player can rehearse. true if yes, returns true if yes, false otherwise
   
   */
   public boolean checkRehearse(Player player){
      if(player.getPracticeTokens() >= player.getCurrentRoom().getSceneCard().getSceneBudget() - 1){
         return false;
      }
      return true;
   }
   
   /*
   checkRole(Player player, String s)
   returns: boolean
   parameters:
      player: player trying to take a role
      s: the name of the role they are trying to take
   checks if the player can take a certain role in a scene. returns true if yes, false otherwise
   */
   public boolean checkRole(Player player, String s){
      if(player.getCurrentRoom().getRole(s) == null){
         return false;
      }
      if(player.getRank() < player.getCurrentRoom().getRole(s).getRank()){
         return false;
      }
      return true;
   }
   
   /*
   tallyScore(Player player)
   returns : int
   parameters:
      player: player we are tallying the score for
   tallies the score for a given player
   */
   public int tallyScore(Player player){
      return (player.getRank() * 5) + player.getMoney() + player.getCredits();
   }
   
   /*getter for maxDaysLeft, or how many days long the game is*/
   public int getMaxDaysLeft(){
      return maxDaysLeft;
   }
   
   
   /*getter for the current day*/
   public int getDay(){
      return maxDaysLeft - daysLeft + 1;
   }
   
   /*
   removeDay()
   returns: none
   precond: daysLeft != 0
   removes 1 day from daysLeft
   */
   public void removeDay(){
      daysLeft -= 1;
   }
   
   
   
   /*
   checkGameOver()
   params: none
   returns: boolean- tru is game is over
   precond: day is over
   returns true if the game is over
   */
   public boolean checkGameOver(){
      if(daysLeft == 0){
         return true;
      }
      else{
         return false;
      }
   }
}