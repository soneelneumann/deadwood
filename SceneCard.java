import java.util.ArrayList;

public class SceneCard{
   public String sceneName;
   private int sceneBudget;
   private ArrayList<Role> roles;
   
   public SceneCard(){
      //stuff goes here   
   }
   
   public SceneCard(String sceneName){
      //stuff goes here   
   }
   
   public SceneCard(String name, int budget){
      //stuff goes here
   }
   
   public int getBudget(){
      return 1; //temp
   }
   
   public ArrayList<Role> getRoles(){
      return new ArrayList<Role>(); //temp
   }
}