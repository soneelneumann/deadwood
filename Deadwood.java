import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Deadwood{
   public static void main(String[] args) throws ParserConfigurationException{
//       initialize board
      Scanner scan = new Scanner(System.in);
      Board b = new Board();
      
      XMLParser xml = new XMLParser();
      
      ArrayList<Room> roomList = xml.getRooms("board.xml");
      
      //set up the board
      for(Room r : roomList){
         if(r.getName().equals("trailers")){
            r.setName("Trailers");
            b.setTrailers(r);
         }
         else if(r.getName().equals("office")){
            r.setName("Casting Office");
         }
      }
      b.setRoomList(roomList); //fill in roomlist for board
      
      
      
      System.out.println("Welcome to Deadwood! \nPlease enter in the number of Players (2 to 8)");
      int playerAmount = getPlayerNumber(scan);
      System.out.println("Please assign yourselves as Players 1 through " + playerAmount);
      
      //initialize our Players
      Player[] players = new Player[playerAmount];
      
      for(int i = 0; i < players.length; i++){
         Player p = new Player(i + 1); //create new player with corresponding playerNumber
         players[i] = p;
      }
      
      //put players in the board's player list
      for(int i = 0; i < players.length; i++){
         b.addPlayer(players[i]);
      }
      
      players = shufflePlayerOrder(players);
      
      int numberOfDays = 4;
      
      //modify starting conditions based on number of players
      if(players.length < 4){
         numberOfDays = 3;
      }
      else if(players.length == 5){
         for(int i = 0; i < players.length; i++){
            players[i].addCredits(2);
         } 
      }
      else if(players.length == 6){
         for(int i = 0; i < players.length; i++){
            players[i].addCredits(4);
         }
      }
      else if(players.length > 6){
         for(int i = 0; i < players.length; i++){
            players[i].setRank(2);
         }
      }
      
      Moderator moderator = new Moderator(numberOfDays);
      Banker banker = new Banker();
      Dice dice = new Dice();
      
      System.out.println("The turn ourder for this game will be: ");
      for(int i = 0; i < players.length; i++){
         System.out.println("Turn " + (i+1) + ": Player " + players[i].getPlayerNumber());
      }
      System.out.println("At the end of this list, the turn order wraps around to the top. ");
      System.out.println("Press enter when you're ready to start.\n");
      String waitHere = scan.nextLine();
      
//       
//       start game
//       
      //move players to the trailers
      for(int i = 0; i < players.length; i++){
         players[i].move(b.getTrailers());
      }
      
      displayTutorialText(players.length, numberOfDays);
      
      //begin player turnOrder
      int currentTurn = 0;
      boolean continueGame = true; //boolean for if the game is still running
      
      while(continueGame){
         for(int i = 0; i < players.length; i++){
            String[] availableActions = getAvailableActions(players[i]);
            System.out.println("It is now Player " + players[i].getPlayerNumber() + "'s turn.");
            
         }
      }
      
      
//       put Players in the trailers
//       display tutorial text 
         //potential actions:
         //move <roomname> : moves to room
         //role <rolename> : take a role in a scene
         //where : displays current room and relevant info
            //shows name of the room you're in 
            //shows neighbors
            //in scene, shows roles and if they're taken
            //in office, shows credit and money prices
            //in trailer, shows "you'll start here each day."
         //rank <ranknumber> <money/credits> 
         //act : act in a scene you're in
         //rehearse : rehearse for a scene you're in
         //end : ends turn
         //day : displays current day
//       
//       boolean continueGame = true;
//       currentTurn = 0; <- used to cycle through turnOrder
//       while(continueGame) <- continue is set to false on end of game
            //continueTurn = true; //false when turn is done
            //announce whose turn it is
            //display their current situation
            //take in player command
            //do corresponding action to command
            //depending on action, end turn
   }
   
   /*runs through a player's turn*/
   public static void parseTurn(Moderator moderator, Board board, Player player, Scanner scan, String[] availableActions){
      System.out.println("Here's what you can do: " + Arrays.toString(availableActions));
      System.out.println("Type your command below: ");
      String command = scan.next();
      List<String> actionList = Arrays.asList(availableActions);
      
      if(!actionList.contains(command)){
         System.out.println("oops! looks like you typed something other than the avilable commands");
         parseTurn(moderator, board, player, scan, availableActions);
      }
      else{
         switch(command){
         case "end":
            System.out.print("Player " + player.getPlayerNumber() + "'s turn is over.");
            break;
            
         case "role":
            String roleWanted = scan.next();
            
            if(moderator.checkRole(player, roleWanted) != null){
               Role role = moderator.checkRole(player, roleWanted);
               player.takeRole(role);
               String[] newAvailableActions = new String[]{"end"};
               parseTurn(moderator, board, player, scan, newAvailableActions);
            }
            else{
               System.out.println("Looks like you did not enter a valid role to take. Use \"where\" to look at available roles you can take.");
               parseTurn(moderator, board, player, scan, availableActions);
            }
            break;
            
         case "move":
            String destination = scan.next();
            if(moderator.checkMove(player.getCurrentRoom(), destination)){
               player.move(board.getRoom(destination));
               
               String[] newAvailableActions = new String[]{"role", "where", "day", "rank", "stats", "end"};
               parseTurn(moderator, board, player, scan, newAvailableActions);
            }
            else{
               System.out.println("Looks like you entered in somewhere you cannot go. Use \"where\" to look at available rooms to travel to.");
               parseTurn(moderator, board, player, scan, availableActions);
            }
            break;
            
         case "rank":
            try{
               int rankRequested = Integer.parseInt(scan.next());
               String currencyType = scan.next();
               if(!(currencyType.toLowerCase().equals("money") || currencyType.toLowerCase().equals("credits"))){
                  System.out.println("Please enter in \"credits\" or \"money\" after the rank you would like.");
               }
               else if(moderator.checkRankUp(player, rankRequested, currencyType)){
                  //player.rankUp()
                  System.out.println("You now are now rank " + player.getRank());
                  System.out.println("You now have " + player.getMoney() + " dollars and " + player.getCredits() + " credits.");
               }
            }
            catch(Exception numberFormatException){
               System.out.println("Looks like you didn't input just a number after \"rank\". Make sure you type \"rank <number>\".");
               parseTurn(moderator, board, player, scan, availableActions);
            }
            
            String[] newAvailableActions = new String[]{"move", "where", "role", "day", "stats", "end"};
            parseTurn(moderator, board, player, scan, newAvailableActions);
            break;
            
         case "where":
            where(player.getCurrentRoom());
            parseTurn(moderator, board, player, scan, availableActions);
            break;
         
         case "act":
            System.out.println(player.getCurrentRole().getLine());
            Banker b = new Banker();
            boolean success = moderator.checkAct(player);
            if(success){
               System.out.println("Nice going! You've succeeded.");
               if(player.getCurrentRole().isOnCard()){
                  System.out.println("Rewards: 2 credits");
               }
               else{
                  System.out.println("Rewards: 1 credit, 1 dollar");
               }
               
            }
            else{
               System.out.println("Drat! You failed.");
               if(!(player.getCurrentRole().isOnCard())){
                  System.out.println("Rewards: 1 dollar");
               }
            }
            b.disperseRewards(player, success);
            
            newAvailableActions = new String[]{"end"}; //weird error here made me not declare this
            parseTurn(moderator, board, player, scan, newAvailableActions);
            break;
            
         case "rehearse":
            if(moderator.checkRehearse(player)){
               System.out.println(player.getCurrentRole().getLine());
               System.out.println("You've earned 1 practice token.");
               player.addPracticeToken();
               newAvailableActions = new String[]{"end"};
               parseTurn(moderator, board, player, scan, newAvailableActions);
            }
            else{
               System.out.println("You've rehearsed enough! Now you must act.");
               
               newAvailableActions = new String[]{"act", "where", "day", "stats", "end"};
               parseTurn(moderator, board, player, scan, newAvailableActions);
            }
            break;
            
         case "day":
            System.out.println("There are " + moderator.getMaxDaysLeft());
            parseTurn(moderator, board, player, scan, availableActions);
            break;
            
         case "stats":
            if(player.getCurrentRole() == null){
               System.out.println("Your stats:");
               System.out.println("\tRank: " + player.getRank());
               System.out.println("\tMoney: " + player.getMoney());
               System.out.println("\tCredits: " + player.getCredits());
            }
            else{
               System.out.println("Your stats:");
               System.out.println("\tPractice Tokens: " + player.getPracticeTokens());
               System.out.println("\tScene shot tokens: " + player.getCurrentRoom().getShotTokens());
            }
            
            parseTurn(moderator, board, player, scan, availableActions);
         }
      }
   }
   
   /*displays the tutorial text for the game*/
   public static void displayTutorialText(int playerNum, int daysLeft){
      System.out.println("This game has the same rules the board game does. The available commands are:");
      System.out.println("move <room name> : Move to another room.");
      System.out.println("role <role name> : Take a role with this name.");
      System.out.println("rank <rank number> <money/credits>: purchase a rank. Must be ");
      System.out.println("where : shows relevant info of the room you're in and its neighbors");
      System.out.println("act : Act in a scene you're in");
      System.out.println("rehearse : rehearse in a scene you're in");
      System.out.println("end : ends your turn");
      System.out.println("day : displays the current day");
      System.out.println("\nSince there are " + playerNum + " players, the game will have " + daysLeft + " days.");
      System.out.println("Press enter when you have read the above. ");
      
   }
   
   /*returns a set of available actions for a player at the start of turn*/
   public static String[] getAvailableActions(Player player){
      if(player.getCurrentRole() == null){
         String[] availableActions = {"move", "role", "rank", "where", "end", "day"};
         return availableActions;
      }
      String[] availableActions = {"act, rehearse, where, end, day"};
      return availableActions;
   }
   
   /*returns the user's inputted amount of players*/
   public static int getPlayerNumber(Scanner scan){
      
      String playerEntry = scan.nextLine();
      
      try{
         int playerAmount = Integer.parseInt(playerEntry);
         if(playerAmount > 8 || playerAmount < 2){
            System.out.println("Please enter a number between 2 and 8.");
            return getPlayerNumber(scan);
         }
         return playerAmount;
      }
      catch(Exception numberFormatException){
         System.out.println("Please enter a number");
         return getPlayerNumber(scan);
      }
   }
   
   /*displays all relevant info about the room the player is in*/
   public static void where(Room r){
      System.out.println("You are currently in: " + r.getName());
      if(r.getName().equals("Casting Office")){
         System.out.println("Prices:");
         int[] prices_money = r.getRankMoneyPrices();
         int[] prices_credits = r.getRankCreditPrices();
         for(int i = 0; i < prices_money.length; i++){
            System.out.println("Rank: " + (i+2) + prices_money[i] + " dollars, " + prices_credits[i] + " credits");
         }
      }
      if(r.getName().equals("Trailers")){
         System.out.println("You start here each day.");
      }
      else{
         if(r.getSceneCard() != null){
            System.out.println("Scene in this room: " + r.getSceneCard().sceneName);
            System.out.println("\n\"+" + r.getSceneCard().getSceneText() + "\"");
         }
         else{
            System.out.println("the scene in this room is currently wrapped.");
         }
      }
      
      System.out.println();
      System.out.println("Neighbors: ");
      for(Room n : r.getNeighbors()){
         if(n.getName().equals("Casting Office") || n.getName().equals("Trailers")){
            System.out.println(n.getName());
         }
         else{
            if(n.getSceneCard() == null){
               System.out.println(n.getName() + " <wrapped>");
            }
            else{
               System.out.println(n.getName() + " " + "<scene available>");
            }
         }
      }
   }
   
   public static Player[] shufflePlayerOrder(Player[] players){
      List<Player> playerList = Arrays.asList(players);
      Collections.shuffle(playerList);
      playerList.toArray(players);
      return players;
   }
   
   /*
      Displays where the player is and if they are in a scene
   */
   public static void where(Player p, Board b){

   }
   
   /*
   
   */
}