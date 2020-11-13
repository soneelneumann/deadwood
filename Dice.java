import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
public class Dice{

   //random object used to make dice rolls
   private Random rand;
   
   /*Dice initializer*/
   public Dice(){
      rand = new Random();
   }
   
   /*
      rollPayout(int budget)
      returns: ArrayList<Integer>
      description: rolls the payout at the end of the scene
      precond: the scene we are doing payout for is over
      postcond: payout is calculated correctly and is in order from highest roll to lowest
   */
   public ArrayList<Integer> rollPayout(int budget){
      ArrayList<Integer> payout = new ArrayList<Integer>();
      for(int i = 0; i < budget; i++){
         payout.add(rand.nextInt(6) + 1);
      }
      Collections.sort(payout);
      Collections.reverse(payout);
      return payout;
   }
   
   /*
      rollAct(int bonus)
      returns: ArrayList<Integer> 
      description: rolls the result fo an act attempt
      precond: bonus is greater than or equal to zero
      postcond: acting roll is returned
   */
   public int rollAct(int bonus){
      int roll = rand.nextInt(6) + 1;
      roll += bonus;
      return roll;
   }
   
}