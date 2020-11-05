import java.util.Random;
import java.util.ArrayList;
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
      return new ArrayList<Integer>(); //temp
   }
   
   /*
      rollAct(int bonus)
      returns: ArrayList<Integer> 
      description: rolls the result fo an act attempt
      precond: bonus is greater than or equal to zero
      postcond: acting roll is returned
   */
   public int rollAct(int bonus){
      int roll = (int)(Math.random()*6) + 1;
      roll += bonus;
      return roll;
   }
   
}