/*
Soneel Neumann and Chris Brown
SceneCard class, keeps track of scenename, scenetext, scenenumber, roles, and the scenebudget. 
*/

import java.util.ArrayList;

public class SceneCard{
   public String sceneName;
   
   private String sceneText;

   private int sceneNumber;
   
   private int sceneBudget;
   
   private ArrayList<Role> roles;
   
   /* Scene card initializer */
   public SceneCard(){
      roles = new ArrayList<Role>();  
   }
   
   /* Scene card initializer given the scene's name */
   public SceneCard(String sceneName){
      this.sceneName = sceneName;   
   }
   
   /* Scene card initializer given the scene's name and the scene's budget */
   public SceneCard(String sceneName, int sceneBudget){
      this.sceneName = sceneName;
      this.sceneBudget = sceneBudget;
   }
   
   /*
   addRole()
   returns: 
   precond: r is a non empty Role
   Adds a new Role to the list of Roles in the scene card
   */
   public void addRole(Role r){
      roles.add(r);
   }
   
   /*
   getSceneBudget() 
   Returns: int
   Precondtion: There is a budget attached to a scene card
   This method gets the budget of the scene card
   */
   public int getSceneBudget(){
      return sceneBudget; 
   }
   
   /*
   setBudget()
   returns:
   precond: scene needs a budget
   Setter for sceneBudget
   */
   public void setSceneBudget(int sceneBudget){
      this.sceneBudget = sceneBudget;
   }
   
   /*
   getRoles()
   Returns: ArrayList
   Precondtion: There is a list of roles associated with a scene card
   This method gets the roles of the scene card available to the user
   */
   public ArrayList<Role> getRoles(){
      return roles; 
   }
   
   
   /*
   getSceneNumber()
   returns: int, the scene number
   precond: scene number
   Getter for the scene number
   */
   public int getSceneNumber(){
      return sceneNumber;
   }
   
   /*
   setSceneNumber()
   returns: none
   precond: 
   Setter for the scene number
   */
   public void setSceneNumber(int sceneNumber){
      this.sceneNumber = sceneNumber;
   }
   
   /*
   getSceneText()
   returns: String, text stored in sceneText
   precond: 
   Getter for scene text
   */
   public String getSceneText(){
      return sceneText;
   }
   
   /*
   setSceneText()
   returns: 
   precond: 
   Setter for sceneText 
   */
   public void setSceneText(String sceneText){
      this.sceneText = sceneText;
   }
}