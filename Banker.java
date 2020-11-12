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
   public void disperseRewards(Player p, Role r, boolean success){
      if(success){
         if(p.getCurrentRole)
      }
      else{
         //disperse loss rewards
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
   public int dispersePayout(Scene s, Role r){
      //
      
   }
}