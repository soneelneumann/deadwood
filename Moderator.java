import java.util.ArrayList;
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
      
      precond: currently player's turn, currencyType == "c" or "m"
      postcond: validity of request is returned
   */
   public boolean checkRankUp(Player player, int rankRequested, String currencyType){
      //
      if(player.getRank() == 6){
            return false;
      }
      if(currencyType.equals("c")){
         
         if(player.getCurrentRoom().getRankCreditPrices() == null){
            return false;
         }
         else if(player.getCredits() < player.getCurrentRoom().getRankCreditPrices()[rankRequested - 2]){
            return false;
         }
         return true;
      }
      else{
         if(player.getCurrentRoom().getRankMoneyPrices() == null){
            return false;
         }
         else if(player.getMoney() < player.getCurrentRoom().getRankMoneyPrices()[rankRequested - 2]){
            return false;
         }
         return true;
      }
   }
   
   /*
      checkAct()
      params: Player p, who 
      returns: boolean
      
      this doesn't need to be here maybr. just use Dice to roll and have Player check maybe
   */
   public boolean checkAct(Player player){
      Dice d = new Dice();
      
      int bonus = player.getPracticeTokens();
      int result = d.rollAct(bonus);
      
      if(result < player.getCurrentRoom().getSceneCard().getSceneBudget()){
         return false;
      }
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
   */
   public Role checkRole(Player player, String s){
      return player.getCurrentRoom().getRole(s);
   }
   
   private int tallyScore(){
      //
      return -1; //temp
   }
   
   public Player declareWinner(){
      //
      return new Player(); //temp
   }
   
   /*getter for maxDaysLeft, or how many days long the game is*/
   public int getMaxDaysLeft(){
      return maxDaysLeft;
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