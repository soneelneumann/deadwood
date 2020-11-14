/*
Chris Brown, Soneel Neumann

One of the room types which makes up the Deadwood board. Allows players to increase their rank here. Also contains rank purchasing information. 
*/

import java.util.TreeMap;

public class CastingOffice extends Room{
   private int[] prices_money; //rank prices in money
   private int[] prices_credits; //rank prices in credit
   
   /* Casting office initialization */
   public CastingOffice(){
      //set up the rank and money prices
      prices_money = new int[5];
      prices_credits = new int[5];
   }
   
   public CastingOffice(int[] prices_money, int[] prices_credits){
      this.prices_money = prices_money;
      this.prices_credits = prices_credits;
   }
   
   /*
   getRankMoneyPrices()
   returns: int
   precondition: none
   returns rank prices in dollars
   */
   public int[] getRankMoneyPrices(){
      int[] temp = new int[prices_money.length]; //this will need to be changed to a Set<Integer>
      //put shallow copy of prices_money into temp
      System.arraycopy(prices_money, 0, temp, 0, temp.length);
      return temp;
      
   }
   
   /*
   getRankCreditPrices()
   returns: int
   precondition: none
   returns rank prices in credits
   */
   public int[] getRankCreditPrices(){
      int[] temp = new int[prices_credits.length]; 
      //put shallow copy of prices_credits into temp
      System.arraycopy(prices_credits, 0, temp, 0, temp.length);
      return temp;

   }
}