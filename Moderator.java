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
      checkMove()
      params:
         Room start: room player starts in
         Room dest: room they are moving to
      returns: boolean- true if valid move
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
      checkRankUp()
      
      params:
         Player p, player requesting rank
         int rankRequested, rank they wish to purhcase
         String currencyType, either "c" or "m" for credits or money
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
      checkAct()
      params: Player p, who 
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
   checkRehearse()
   checks whether or not player can rehearse. true if yes, false if no.
   precond: player is in a role
   */
   public boolean checkRehearse(Player player){
      if(player.getPracticeTokens() >= player.getCurrentRoom().getSceneCard().getSceneBudget() - 1){
         return false;
      }
      return true;
   }
   
   /*
      checkRole()
      checks if the player can get a role with given string as a name. Returns the Role if they can, returns null if they cannot
      
      
      C H A N G E
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
   
   /*tallies the final score for the passed in Player*/
   public int tallyScore(Player player){
      return (player.getRank() * 5) + player.getMoney() + player.getCredits();
   }
   
   public ArrayList<Player> declareWinner(List<Player> players){
      int highScore = 0;
      //player we will declare as the winner, empty for now
      ArrayList<Player> winningPlayers = new ArrayList<Player>(); 
      for(Player player : players){
         int playerScore = (player.getRank() * 5) + player.getCredits() + player.getMoney();
         if(playerScore > highScore){
            winningPlayers.clear();
            winningPlayers.add(player);
         }
         if(playerScore == highScore){
            winningPlayers.add(player);
         }
      }
      
      return winningPlayers; 
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
   returns:
   precond: daysLeft != 0
   postcond: daysLeft is 1 less
   */
   public void removeDay(){
      daysLeft -= 1;
   }
   
   
   
   /*
      checkGameOver()
      params: none
      returns: boolean- tru is game is over
      precond: day is over
      postcond: returns if the gaem is over
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