import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
   public void dispersePayout(Room room){
      //
      Dice d = new Dice();
      ArrayList<Integer> payoutVals = d.rollPayout(room.getSceneCard().getSceneBudget());
      
      ArrayList<Player> players = room.getOccupants();
      
      ArrayList<Role> roles = room.getSceneCard().getRoles();
      ArrayList<Integer> ranks = new ArrayList<Integer>();
      
      for(Role r : roles){
         ranks.add(r.getRank());
      }
      
      Collections.sort(ranks);
      Collections.reverse(ranks);
      
      //loop through roles and pay respective players
      for(int i = 0; i < payoutVals.size(); i++){
         int roleRank = ranks.get(i % ranks.size()); 
         
         for(Player p : players){
            if(p.getCurrentRole().isOnCard() && p.getCurrentRole().getRank() == roleRank){
               p.addMoney(payoutVals.get(i));
            }
         }
      }
   }
}