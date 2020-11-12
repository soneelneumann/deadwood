import java.util.ArrayList;

public class Scene extends Room{
   
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
      roles = new ArrayList<Role>();
      //stuff goes here
   }
   
   /*Scene initializer, with shot token max*/
   public Scene(int shotTokenMax){
      this.shotTokenMax = shotTokenMax;
      shotTokens = shotTokenMax;
   }
   
   /*adds input role to roles*/
   public void addRole(Role r){
      roles.add(r);
   }
   
   /*Return Role in the scene or scene card. Returns null if none are found*/
   public Role getRole(String s){
      for(Role r: roles){
         if(r.name.equals(s)){
            return r;
         }
      }
      if(sceneCard != null){
         for(Role r: sceneCard.getRoles()){
            if(r.name.equals(s)){
               return r;
            }
         }
      }
      return null;
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
   
   public SceneCard getSceneCard(){
      return sceneCard;
   }
   
}