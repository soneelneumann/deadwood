public class Player{
   public String playerName;
   private int credits;
   private int money;
   private int rank;
   private int practiceTokens;
   private Role currentRole;
   
   public Player(){
      //make everything default
   }
   
   public Player(String playerName){
      //initialize playerName
      //everything else default
   }
   
   public Player(String playerName, int money, int credits){
      //initalize playerName, money, credits
      //everything else default
   }
   
   public Player(String playerName, int money, int credits, int rank){
      //initialize playerName, money, credits, rank
      //everything else default
   }
   
   public void move(Room location){
      //move to a room
   }
   
   public void purchaseRank(int rank){
      //purchase a rank
   }
   
   public void act(Scene scene){
      //act in a scene
   }
   
   public void addPracticeToken(){
      //add a practice token
   }
   
   public void removePracticeTokens(){
      //remove all practice tokens
   }
   
}