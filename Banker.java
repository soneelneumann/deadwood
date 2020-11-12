public class Banker{
   
   /*initializer*/
   public Banker(){
      
   }
   
   /*
      disperseRewards()
      params:
         Player p, player who finished acting
         Role r, role the player was acting in
         bool success, true if player succeeded acting, false otherwise
   */
   public void disperseRewards(Player player, boolean success){
      if(success){
         //if player is in a role on a scene card
         if(player.getCurrentRole().isOnCard()){
            player.addCredits(2);
         }
         else{
            player.addCredits(1);
            player.addMoney(1);
         }
      }
      else{
         //disperse loss rewards
         //if player is not on a scene card role
         if(!(player.getCurrentRole().isOnCard())){
            player.addMoney(1);
         }
      }
   }
   
   /*
      dispersePayout()
      params: 
         Scene s, scene we're paying out for
         Role r, role the player has
      description: pays player for role as scene ends
      returns: int, amount to add to player's money
      precond: scene is being wrapped up
      postcond: player has been payed
   */
   public int[] dispersePayout(Scene s, Role r){
      //
      return new int[2];
   }
}