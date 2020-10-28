public class Role{
   
   private int rankRequired;
   private boolean isTaken;
   
   /*Role initializer*/
   public Role(){
      /*
      rankRequired = 1;
      isTaken = false;
      */
   }
   
   /*
      getRequiredRank()
      returns: int 
      description: returns the rank required to take this role
      precond: Role is in a scene/scene card on the board
      postcond: required rank is returned
   */
   public int getRequiredRank(){
      //return rankRequired;
      return 1; //temp
   }
   
   /*
      getIsTaken()
      returns: boolean
      description: returns if the role is taken Player
      precond: Role is in a scene/scene card on the board
      postcond: isTaken is returned
   */
   public boolean getIsTaken(){
      //return isTaken;
      return false; //temp
   }
}  