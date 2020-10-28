public class CastingOffice{
   private int[] prices_money; //rank prices in money
   private int[] prices_credit; //rank prices in credit
   
   /* Casting office initialization */
   public CastingOffice(){
      //set up the rank and money prices
   }
   
   /*
   getRankMoneyPrices()
   returns: int
   Precondition: the player wants to know the ranks value in monetary value.
   Postcondition: the player knows the desired ranks value in monetary value.
   This method gets the money price of the desired rank.
   */
   public int[] getRankMoneyPrices(){
      /*
      int[] temp = new int[prices_money.length];
      System.arraycopy(prices_money, 0, temp, 0, temp.length);
      return temp;
      */
      
      return new int[1]; //temp
   }
   
   /*
   getRankCreditPrices()
   returns: int
   Precondition: the player wants to know the ranks value in credit value. 
   Postcondition: the player knows the desired ranks value in credit valiue.
   This method gets the credit price of the desired rank.
   */
   public int[] getRankCreditPrices(){
      /*
      int[] temp = new int[prices_credit.length];
      System.arraycopy(prices_credit, 0, temp, 0, temp.length);
      return temp;
      */
      
      return new int[1]; //temp
   }
}