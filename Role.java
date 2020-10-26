public class Role{
   
   private int rankRequired;
   private boolean isTaken;
   
   public Role(){
      /*
      rankRequired = 1;
      isTaken = false;
      */
   }
   
   public Role(int rank){
      /*
      rankRequired = rank;
      isTaken = false;
      */
   }
   
   public Role(boolean taken){
      /*
      isTaken = taken;
      rankRequired = 1;
      */
   }
   
   public Role(int rank, boolean taken){
      /*
      rankRequired = rank;
      isTaken = taken;
      */   
   }
   
   public int getRequiredRank(){
      //return rankRequired;
      return 1; //temp
   }
   
   public boolean getIsTaken(){
      //return isTaken;
      return false; //temp
   }
}  