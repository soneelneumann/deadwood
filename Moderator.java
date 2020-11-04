import java.util.ArrayList;
public class Moderator{
   
   private int daysLeft;
   
   /*Moderator initializer*/   
   public Moderator(){
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
   public boolean checkMove(Room start, Room dest){
      //
      if(start.neighbors.contains(dest)){
         return true;
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
      
      precond: currently player's turn
      postcond: validity of request is returned
   */
   public boolean checkRankUp(Player p, int rankRequested, String currencyType){
      //
      if(/*p.rank == 6 (p.getRank when it's added)*/|| p.rank >= rankRequested){
         return false;
      }
      /*
      //switch for different currency types
      switch(currencyType){
         case "m":
            if(player.getMoney() >= cost of the rank in money){
               return false; 
            }
            return true;
            break;
         case "c":
            if(player.gerCredits() >= cost of the rank in credits)
      }
      */
      
   }
   
   /*
      checkAct()
      params: Player p, who 
      returns: boolean
      
      this doesn't need to be here maybr. just use Dice to roll and have Player check maybe
   */
   public boolean checkAct(Player p){
      //might be superfluous????????????????
      return false; //temp
   }
   
   private int tallyScore(){
      //
      return -1; //temp
   }
   
   public Player declareWinner(){
      //
      return new Player(); //temp
   }
   
   /*
      disperseRewards()
      params:
         Player p, player who finished acting
         Role r, role the player was acting in
         bool success, true if player succeeded acting, false otherwise
   */
   public void disperseRewards(Player p, Role r, boolean success){
      if(success){
         //disperse victory rewards
      }
      else{
         //disperse loss rewards
      }
   }
   
   /*
      checkGameOver()
      params: 
         Scene s, scene we're paying out for
         Role r, role the player has
      description: pays player for role as scene ends
      returns: int, amount to add to player's money
      precond: scene is being wrapped up
      postcond: player has been payed
   */
   public int dispersePayout(Scene s, Role r){
      //
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