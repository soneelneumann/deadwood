/*
Soneel Neumann and Chris Brown
Deadwood class, runs the game.
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Deadwood{
   /*
   main(String[] args)
   returns: 
   precondition: game needs to start
   Runs the game
   */
   public static void main(String[] args) throws ParserConfigurationException{
      Scanner scan = new Scanner(System.in);
      
               //STAYS HERE
      XMLParser xml = new XMLParser();
      ArrayList<SceneCard> cardPile = xml.getCards("cards.xml");
      
      Board b = new Board(cardPile);
      
      ArrayList<Room> roomList = xml.getRooms("board.xml");
      
      //set up the board
      for(Room r : roomList){
         if(r.getName().equals("trailer")){
            r.setName("Trailers");
            b.setTrailers(r);
         }
         else if(r.getName().equals("office")){
            r.setName("Casting Office");
         }
      }
      
               //DISPLAY??
      b.setRoomList(roomList); //fill in roomlist for board
      b.resetBoard(); //initializes scene cards and shot tokens
      
      
      
      
                     //GOES IN DISPLAY
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
      
               //DISPLAY, maybe use Association
      Moderator moderator = new Moderator(numberOfDays);
      Banker banker = new Banker();
      Dice dice = new Dice();
      
      
               //DELETE
      System.out.println("The turn ourder for this game will be: ");
      for(int i = 0; i < players.length; i++){
         System.out.println("Turn " + (i+1) + ": Player " + players[i].getPlayerNumber());
      }
      System.out.println("At the end of this list, the turn order wraps around to the top. ");
      System.out.println("Press enter when you're ready to start.\n");
      String waitHere = scan.nextLine();
      
               //DISPLAY
      //start game     
      //move players to the trailers
      for(int i = 0; i < players.length; i++){
         players[i].move(b.getTrailers());
      }
      
               //REMOVE
      displayTutorialText(players.length, numberOfDays);
      
      
               //DISPLAY
      //begin player turnOrder
      int currentTurn = 0;
      boolean continueGame = true; 
      boolean continueDay = true;
      while(continueGame){
         
         
            //REPLACED BY MOUSE LISTENER
         while(continueDay){
            for(int i = 0; i < players.length; i++){
               String[] availableActions = getAvailableActions(players[i]);
               System.out.println("It is now Player " + players[i].getPlayerNumber() + "'s turn.");
               parseTurn(moderator, b, players[i], scan, availableActions); 
               if(b.isDayOver()){
                  
                  moderator.removeDay();

                  break;
               }
            }
            if(b.isDayOver()){
               
               if(moderator.getDay() <= moderator.getMaxDaysLeft()){
                  System.out.println("It is currently day " + moderator.getDay());
                  System.out.println("All Players start in the Trailers.");
               }
               
                  
               b.clearBoard();
               b.resetBoard();
               break;
            }
         }
         if(moderator.checkGameOver()){
            continueGame = false;
            break;
         }
      }
      
               //DISPLAY
      //display each player's score
      System.out.println("Final score:");
      for(Player player : players){
         System.out.println("\tPlayer " + player.getPlayerNumber() + "'s score: " + moderator.tallyScore(player));
      }
      
      System.out.println("Game Over.");
   }
   
   /*runs through a player's turn*/
   /*
   parseTurn(Moderator moderator, Board board, Player player, Scanner scan, String[] availableActions)
   params:
      Moderator moderator, for checking validity of actions a player is attempting
      Board board, performing the actions on the board
      Player player, which player is performing actions
      Scanner scan, input for player actions
      String[] avaialableActions, displays available actions a player can perform
   precond: player needs to go through turn
   Allows player to go through turn   
   */
   
   
   
            //DISPLAY            //dont pass          pass        dont pass      dont           dont
   public static void parseTurn(Moderator moderator, Board board, Player player, Scanner scan, String[] availableActions){
      
               //REMOVE
      System.out.println("Here's what you can do: " + Arrays.toString(availableActions));
      System.out.println("Type your command below: ");
      String command = scan.next();
      List<String> actionList = Arrays.asList(availableActions);
      
               //DISPLAY, REMOVE ALL TEXT RELATED CODE AND REPLACE WITH MOUSE LISTENER
      if(!actionList.contains(command)){
         System.out.println("oops! looks like you typed something other than the avilable commands");
         if(scan.hasNextLine()){
            String temp = scan.nextLine(); //remove any other characters the player may have typed
         }
         parseTurn(moderator, board, player, scan, availableActions);
      }
      else{
         switch(command){
         case "end":
            System.out.println("Player " + player.getPlayerNumber() + "'s turn is over.");
            break;
            
         case "role":
            String read = scan.nextLine();
            String roleWanted = read.substring(1, read.length()); //remove white space in front
            
            //if the wanted role exists in the room the player is in
            if(moderator.checkRole(player, roleWanted)){
               //get the role they want
               Role role = player.getCurrentRoom().getRole(roleWanted);
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
            String destination = ""; //blank, to be filled in later. 
            read = scan.nextLine();
            if(!read.equals("")){
               destination = read.substring(1, read.length());
            }
            else{
               System.out.println("You need to enter in a room name after move.");
               parseTurn(moderator, board, player, scan, availableActions); 
               break;
            }
            if(moderator.checkMove(player.getCurrentRoom(), destination)){
               player.move(board.getRoom(destination));
               System.out.println("You have now moved to " + destination + ".");
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
                  if(currencyType.equals("credits")){
                     player.purchaseRankCredits(rankRequested);
                  }
                  else{
                     player.purchaseRankMoney(rankRequested);
                  }
                  System.out.println("You are now rank " + player.getRank());
                  System.out.println("You now have " + player.getMoney() + " dollars and " + player.getCredits() + " credits.");
               }
               else{
                  parseTurn(moderator, board, player, scan, availableActions);
                  break;
               }
            }
            catch(Exception numberFormatException){
               System.out.println("Looks like you didn't input just a number after \"rank\". Make sure you type \"rank <number>\".");
               parseTurn(moderator, board, player, scan, availableActions);
            }
            String[] newAvailableActions = new String[availableActions.length - 1];
            ArrayList<String> newActionsList = new ArrayList<String>(); //temporary list, to be converted to array for newAvailableActions
            for(int i = 0; i < availableActions.length; i++){
               if(!(availableActions[i].equals("rank"))){
                  newActionsList.add(availableActions[i]);
               }
            }
            newActionsList.toArray(newAvailableActions); //store the list values back into our array
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
                  b.disperseRewards(player, success);
                  System.out.println("Rewards: 2 credits");
               }
               else{
                  b.disperseRewards(player, success);
                  System.out.println("Rewards: 1 credit, 1 dollar");
               }
               System.out.println("Takes left: " + player.getCurrentRoom().getShotTokens());
               
               if(player.getCurrentRoom().getShotTokens() == 0){
                  //end the scene
                  b.dispersePayout(player.getCurrentRoom());
                  for(Player playerInScene : player.getCurrentRoom().getOccupants()){
                     playerInScene.setCurrentRole(null);
                     playerInScene.removePracticeTokens();
                  }
                  
                  player.getCurrentRoom().removeSceneCard();
                  //scene is now wrapped
                  
                  System.out.println("That's a wrap! The scene card is removed and players in the room are removed from their roles.");
               }

            }
            else{
               System.out.println("Drat! You failed.");
               if(!(player.getCurrentRole().isOnCard())){
                  System.out.println("Rewards: 1 dollar");
               }
               b.disperseRewards(player, success);
               System.out.println("Takes left: " + player.getCurrentRoom().getShotTokens());
            }
            
            
            newAvailableActions = new String[]{"end"}; 
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
               System.out.println("Your stats: ");
               System.out.println("\tRank: " + player.getRank());
               System.out.println("\tMoney: " + player.getMoney());
               System.out.println("\tCredits: " + player.getCredits());
            }
            else{
               System.out.println("Your stats:");
               System.out.println("\tPractice Tokens: " + player.getPracticeTokens());
            }
            
            parseTurn(moderator, board, player, scan, availableActions);
         }
      }
   }
   
            //REMOVE
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
   
   /*
   getAvailableActions(Player player)
   returns: String[]
   precond: player needs to know what they can do at beginning of turn
   Player is given set of available actions at start of their turn
   */
   
            //DISPLAY, MAYBE UNDER DIFFERENT METHOD
   public static String[] getAvailableActions(Player player){
      if(player.getCurrentRole() == null){
         String[] availableActions = {"move", "role", "rank", "where", "end", "day", "stats"};
         return availableActions;
      }
      String[] availableActions = {"act", "rehearse", "where", "end", "day", "stats"};
      return availableActions;
   }
   
   /*
   getPlayerNumber(Scanner scan)
   returns: int
   precondition: how many players?
   Each player is assigned a player number, returns amount of players
   */
   
            //REMOVE
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
  
   /*
   where(Room r)
   returns: 
   precond: player needs info about room they're in
   Text is printed to screen about all info about the room the player is currently in
   */
   
            //REMOVE
   public static void where(Room r){
      System.out.println("You are currently in: " + r.getName());
      if(r.getName().equals("Casting Office")){
         System.out.println("Prices:");
         int[] prices_money = r.getRankMoneyPrices();
         int[] prices_credits = r.getRankCreditPrices();
         for(int i = 0; i < prices_money.length; i++){
            System.out.println("Rank " + (i+2) + ": " + prices_money[i] + " dollars, " + prices_credits[i] + " credits");
         }
      }
      if(r.getName().equals("Trailers")){
         System.out.println("You start here each day.");
      }
      else{
         if(r.getSceneCard() != null){
            System.out.println("Scene in this room: " + r.getSceneCard().sceneName);
            System.out.println(r.getSceneCard().getSceneText());
            System.out.println("Scene Budget: " + r.getSceneCard().getSceneBudget());
            System.out.println("Takes Left: " + r.getShotTokens());
            System.out.println("Roles:");
            System.out.println("\tOn Scene Card:");
            for(Role displayRole : r.getSceneCard().getRoles()){
               if(displayRole.getIsTaken()){
                  System.out.println("\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Taken>");
               }
               else{
                  System.out.println("\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Available>");
               } 
            }
            System.out.println("\tOff Scene Card:");
            for(Role displayRole : r.getRoles()){
               if(displayRole.getIsTaken()){
                  System.out.println("\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Taken>");
               }
               else{
                  System.out.println("\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Available>");
               } 
            }
            
         }
         else{
            System.out.println("The scene in this room is currently wrapped.");
         }
      }
      
      System.out.println("Neighbors: ");
      for(Room n : r.getNeighbors()){
         if(n.getName().equals("Casting Office") || n.getName().equals("Trailers")){
            System.out.println("\t" + n.getName());
         }
         else{
            if(n.getSceneCard() == null){
               System.out.println("\t" + n.getName() + " <wrapped>");
            }
            else{
               System.out.println("\t" + n.getName() + " " + "<scene available>");
               System.out.println("\tScene Budget: " + n.getSceneCard().getSceneBudget());
               System.out.println("\tTakes Left: " + n.getShotTokens());
               System.out.println("\tRoles:");
               System.out.println("\t\tOn Scene Card:");
               for(Role displayRole : n.getSceneCard().getRoles()){
                  if(displayRole.getIsTaken()){
                     System.out.println("\t\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Taken>");
                  }
                  else{
                     System.out.println("\t\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Available>");
                  }
                  
               }
               System.out.println("\t\tOff Scene Card:");
               for(Role displayRole : n.getRoles()){
                  if(displayRole.getIsTaken()){
                     System.out.println("\t\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Taken>");
                  }
                  else{
                     System.out.println("\t\t\t" + displayRole.name + " [Rank " + displayRole.getRank() + "]" + " <Available>");
                  }
               }
            }
         }
      }
   }
   
   /* 
   shufflePlayerOrder(Player[] players)
   returns: Player[]
   precondition: order turn needs to be randomized
   Randomizes player order
   */
   
         //DISPLAY
   public static Player[] shufflePlayerOrder(Player[] players){
      List<Player> playerList = Arrays.asList(players);
      Collections.shuffle(playerList);
      playerList.toArray(players);
      return players;
   }
}