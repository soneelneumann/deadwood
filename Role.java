/*
Soneel Neumann and Chris Brown
Role class, keeps track of the rank linked to a role, whether or not a role is taken, the line of the role, the name of the role, and if role is on a scenecard.
*/

public class Role{
   
   private int rank;
   private boolean isTaken;
   private String line;
   private boolean onCard; 
   
   public String name;
   
   /*Role initializer*/
   public Role(){
     //
   }
   
   /* Displays info about role */
   public String toString(){
      return ("rank: " + rank + "|taken? " + isTaken + "|line: " + line);
   }
   
   /*
      getRank()
      returns: int 
      precond: Role is in a scene/scene card on the board
      description: returns the rank required to take this role
   */
   public int getRank(){

      return rank; 
   }
   
   /*
      setRank()
      returns:
      precond: rank is between 1 and 6
      setter for role's required rank
   */
   public void setRank(int rank){
      this.rank = rank;
   }
   
   /*
      getIsTaken()
      returns: boolean
      precond: Role is in a scene/scene card on the board
      description: returns if the role is taken Player
   */
   public boolean getIsTaken(){
      return isTaken;
   }
   
   /*setter for isTaken*/
   public void setIsTaken(boolean isTaken){
      this.isTaken = isTaken;
   }
   
   /* getter for the role's line */
   public String getLine(){
      return line;
   }
   
   /* setter for line */
   public void setLine(String line){
      this.line = line;
   }
   
   /*setter for onCard*/
   public void setOnCard(boolean onCard){
      this.onCard = onCard;
   }
   
   /*getter for onCard*/
   public boolean isOnCard(){
      return onCard;
   }
}  