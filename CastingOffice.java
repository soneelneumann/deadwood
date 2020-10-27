public class CastingOffice{
   private int[] prices_money; //rank prices in money
   private int[] prices_credit; //rank prices in credit
   
   public CastingOffice(){
      //set up the rank and money prices
   }
   
   public int[] getRankMoneyPrices(){
      /*
      int[] temp = new int[prices_money.length];
      System.arraycopy(prices_money, 0, temp, 0, temp.length);
      return temp;
      */
      
      return new int[1]; //temp
   }
   
   public int[] getRankCreditPrices(){
      /*
      int[] temp = new int[prices_credit.length];
      System.arraycopy(prices_credit, 0, temp, 0, temp.length);
      return temp;
      */
      
      return new int[1]; //temp
   }
}