import java.util.ArrayList;

public class SceneCard{
   public String sceneName;
   
   private String sceneText;
   private String sceneLine;
   private int sceneNumber;
   
   private int sceneBudget;
   
   private ArrayList<Role> roles;
   
   /* Scene card initializer */
   public SceneCard(){
      //stuff goes here 
      roles = new ArrayList<Role>();  
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
   addRole()
   returns: 
   precond: r is a non empty Role
   postcond: r is added into roles
   Adds a new Role to the list of Roles in the scene card
   */
   public void addRole(Role r){
      roles.add(r);
   }
   
   /*
   getSceneBudget() 
   Returns: int
   Precondtion: There is a budget attached to a scene card
   Postcondiion: The user can access the budget of the scene card
   
   This method gets the budget of the scene card
   */
   public int getSceneBudget(){
      return sceneBudget; 
   }
   
   /*
   setBudget()
   returns:
   precond: 
   postcond: budget is set to the passed value 
   
   Setter for sceneBudget
   */
   public void setSceneBudget(int sceneBudget){
      this.sceneBudget = sceneBudget;
   }
   
   /*
   getRoles()
   Returns: ArrayList
   Precondtion: There is a list of roles associated with a scene card
   Postconditon: The user can access the list of roles that are available
   
   This method gets the roles of the scene card available to the user
   */
   public ArrayList<Role> getRoles(){
      return roles; 
   }
   
   
   /*
   getSceneNumber()
   returns: int, the scene number
   precond: scene number
   postcond: scene number is returned
   
   Getter for the scene number
   */
   public int getSceneNumber(){
      return sceneNumber;
   }
   
   /*
   setSceneNumber()
   returns: none
   precond: 
   postcond: sceneNumber is set to given value
   
   Setter for the scene number
   */
   public void setSceneNumber(int sceneNumber){
      this.sceneNumber = sceneNumber;
   }
   
   /*
   getSceneText()
   returns: String, text stored in sceneText
   precond: 
   postcond: returns sceneText
   
   Getter for scene text
   */
   public String getSceneText(){
      return sceneText;
   }
   
   /*
   setSceneText()
   returns: 
   precond: 
   postcond: sceneText is changed to input String
   
   Setter for sceneText 
   */
   public void setSceneText(String sceneText){
      this.sceneText = sceneText;
   }
   
   /*
   getSceneLine()
   returns: String, sceneLine text
   precond:
   postcond: sceneLine text is returned
   
   getter for sceneLine
   */
   public String getSceneLine(){
      return sceneLine;
   }
   
   /*
   setSceneLine()
   returns: 
   precond: 
   postcond: sceneLine is changed to input String
   
   Setter for sceneLine 
   */
   public void setSceneLine(String sceneLine){
      this.sceneLine = sceneLine;
   }
}