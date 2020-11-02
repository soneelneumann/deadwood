import java.util.ArrayList;

public class SceneCard{
   public String sceneName;
   private int sceneBudget;
   private ArrayList<Role> roles;
   
   /* Scene card initializer */
   public SceneCard(){
      //stuff goes here   
   }
   
   /* Scene card initializer given the scene's name */
   public SceneCard(String sceneName){
      //stuff goes here   
   }
   
   /* Scene card initializer given the scene's name and the scene's budget */
   public SceneCard(String sceneName, int sceneBudget){
      //stuff goes here
   }
   
   /*
   getBudget() 
   Returns: int
   Precondtion: There is a budget attached to a scene card
   Postcondiion: The user can access the budget of the scene card
   This method gets the budget of the scene card
   */
   public int getBudget(){
      return 1; //temp
   }
   
   /*
   getRoles()
   Returns: ArrayList
   Precondtion: There is a list of roles associated with a scene card
   Postconditon: The user can access the list of roles that are available
   This method gets the roles of the scene card available to the user
   */
   public ArrayList<Role> getRoles(){
      return new ArrayList<Role>(); //temp
   }
}