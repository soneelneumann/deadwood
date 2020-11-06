public class Role{
   
   private int rank;
   private boolean isTaken;
   private String line;
   
   public String name;
   
   /*Role initializer*/
   public Role(){
      /*
      rankRequired = 1;
      isTaken = false;
      */
   }
   
   /*
      getRank()
      returns: int 
      description: returns the rank required to take this role
      precond: Role is in a scene/scene card on the board
      postcond: required rank is returned
   */
   public int getRank(){

      return rank; 
   }
   
   /*
      setRank()
      returns:
      precond: rank is between 1 and 6
      post cond: Role's rank value is changed
      setter for role's required rank
   */
   public void setRank(int rank){
      this.rank = rank;
   }
   
   /*
      getIsTaken()
      returns: boolean
      description: returns if the role is taken Player
      precond: Role is in a scene/scene card on the board
      postcond: isTaken is returned
   */
   public boolean getIsTaken(){
      return isTaken;
   }
   
   /*
   getLine()
   returns: String
   precond:
   postcond: line is returned
   getter for the role's line
   */
   public String getLine(){
      return line;
   }
   
   /*
   setLine()
   returns: 
   precond:
   postcond: line is set to the value
   setter for line
   */
   public void setLine(String line){
      this.line = line;
   }
   
}  