import java.util.TreeMap;

public class CastingOffice extends Room{
   private int[] prices_money; //rank prices in money
   private int[] prices_credits; //rank prices in credit
   
   /* Casting office initialization */
   public CastingOffice(){
      //set up the rank and money prices
   }
   
   public CastingOffice(int[] prices_money, int[] prices_credits){
      this.prices_money = prices_money;
      this.prices_credits = prices_credits;
   }
   
   /*
   getRankMoneyPrices()
   returns: int
   Precondition: the player wants to know the ranks value in monetary value.
   Postcondition: the player knows the desired ranks value in monetary value.
   This method gets the money price of the desired rank.
   */
   public int[] getRankMoneyPrices(){
      int[] temp = new int[prices_money.length]; //this will need to be changed to a Set<Integer>
      //put shallow copy of prices_money into temp
      //System.arraycopy(prices_money, 0, temp, 0, temp.length);
      return temp;
      
   }
   
   /*
   getRankCreditPrices()
   returns: int
   Precondition: the player wants to know the ranks value in credit value. 
   Postcondition: the player knows the desired ranks value in credit valiue.
   This method gets the credit price of the desired rank.
   */
   public int[] getRankCreditPrices(){
      int[] temp = new int[prices_credits.length]; 
      //put shallow copy of prices_credits into temp
      System.arraycopy(prices_credits, 0, temp, 0, temp.length);
      return temp;

   }
}