import java.util.ArrayList;

public class Scene extends Room{
   
   //name of the set
   private String setName; //MIGHT NOT BE NECESSARY
   
   //scene card in the card slot, null when there is no scene card
   private SceneCard sceneCard;
   
   //roles off the scene card
   private ArrayList<Role> roles;
   
   //max shot tokens for scene set
   private int shotTokenMax;
   
   //current shot tokens left
   private int shotTokens;
   
   /*Scene initializer*/
   public Scene(){
      //stuff goes here
   }
   
   /*Scene initializer, with shot token max*/
   public Scene(int shotTokenMax){
      this.shotTokenMax = shotTokenMax;
      shotTokens = shotTokenMax;
   }
   
   /*
      removeShotTokens()
      returns:
      description: removes all shot tokens
      precond: 
      postcond: all shot tokens are removed
   */
   public void removeShotTokens(){
      shotTokens = 0;
   }
   
   /*setter for shot tokens*/
   public void setShotTokens(int shotTokens){
      this.shotTokens = shotTokens;
   }
   
   /*getter for shot tokens*/
   public int getShotTokens(){
      return shotTokens;
   }
   
   /*
      resetShotTokens()
      returns:
      description: refills shot tokens to max
      precond:
      postcond: shot tokens are refilled
   */
   public void resetShotTokens(){
      shotTokens = shotTokenMax;
   }
   
   /*
      addSceneCard()
      returns:
      description: adds scene card to this scene
      precond: scene doesn't already have a scene card
      postcond: scene card is added
   */
   public void addSceneCard(SceneCard s){
      //stuff
   }
   
   /*
      removeSceneCard()
      returns:
      description: removes the scene card from this scene 
         if it has one
      precond:
      postcond: scene card is removed if necessary
   */
   public void removeSceneCard(){
      //remove it I guess, you heard the man
   }
   
   /*
      endScene()
      returns:
      description: ends the scene
      precond:
      postcond: scene is completely finished, wrap up is complete
   */
   public void endScene(){
      //do stuff
   }
   
}