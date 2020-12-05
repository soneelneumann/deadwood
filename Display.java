/*
Soneël Neumann, Chris Brown
Display class which serves as a merging point for our View and our Controller. 
Contains a class called boardMouseListener which controls most game functions and logic.
*/


import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;


public class Display extends JFrame{
   // JLabels
   JLabel boardlabel;
   ArrayList<JLabel> playerlabels; //fill this almost right after initialization
   JLabel playerName;
   JLabel playerMoney;
   JLabel playerCredits;
   JLabel playerTokens;
   TreeMap<String, JLabel> cardLabels; //string is the room the card is located
   
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   JButton bRank;
   JButton bEnd;
   JButton bRole;
   
   //Player Class related
   ArrayList<Player> players;
   Player currentPlayer;
   
   
   
   //Stores shot tokens relating to each room
   TreeMap<String, ArrayList<JLabel>> shotTokens; 
   
   //Board object, serves as our "model", holds all the data
   Board b;
   
   //basic information that is necessary to access
   int numberOfDays;
   int boardWidth;
   int boardHeight;
   
   //boolean for if the player has moved on their turn yet.
   boolean hasMoved;
   
   private String[] colors; //determines which player number has which colors
  
   // JLayered Pane
   JLayeredPane bPane;
   
   public Display(Board board){
      // Set the title of the JFrame
      super("Deadwood");

      numberOfDays = 4; //standard number of days for Deadwood
      b = board; 
      hasMoved = false; 
      
      cardLabels = new TreeMap<String, JLabel>(); //Initializes it so we can use it later
      shotTokens = new TreeMap<String, ArrayList<JLabel>>();
      
      //Player labels
      playerName = new JLabel();
      playerMoney = new JLabel();
      playerCredits = new JLabel();
      playerTokens = new JLabel();
   
      // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();
       
      // Create the deadwood board
      boardlabel = new JLabel();
      ImageIcon icon =  new ImageIcon("board.jpg");
      boardlabel.setIcon(icon); 
      boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
      // Add the board to the lowest layer
      bPane.add(boardlabel, new Integer(0));
      
      // Set the size of the GUI
      setSize(icon.getIconWidth()+200,icon.getIconHeight());
      
      //store width and height of the board
      boardWidth = icon.getIconWidth();
      boardHeight = icon.getIconHeight();
      
      // Create Action buttons
      bAct = new JButton("ACT");
      bRehearse = new JButton("REHEARSE");
      bMove = new JButton("MOVE");
      bRank = new JButton("RANK");
      bEnd = new JButton("END");
      bRole = new JButton("ROLE");
      
      //put jbuttons in their proper place, displays menu
      setUpMenus(icon);

      //number of players in this game
      int playerNumber = numberOfPlayers(); 
      System.out.println(playerNumber);
      
      //initialize both of our arraylists
      playerlabels = new ArrayList<JLabel>();
      //cardabels = new ArrayList<JLabel>();
      
      //create the players and assign them their colors
      
      colors = new String[]{"blue", "cyan", "green", "orange", "pink", "red", "violet", "white"};
      
      //initialize player list
      players = new ArrayList<Player>();
      
      //fill out player list
      for(int i = 0; i < playerNumber; i++){
         players.add(new Player(colors[i]));
      }
      
      //randomize the players
      Collections.shuffle(players);
      
      //set first player to be the first in the player list
      currentPlayer = players.get(0); 

      for(int i = 0; i < players.size(); i++){
         players.get(i).move(board.getTrailers());
      }
      
      //blank, used later
      int ydisp = 0; 
      int xdisp = 0; 
      
      //adds all players to the playerlabels arraylist
      for(int i = 1; i <= playerNumber; i++){
      
         //uses the first character of name of player at index i for label file name
         ImageIcon die = new ImageIcon(players.get(i-1).playerName.substring(0, 1) + "1.png");
         JLabel playerlabel = new JLabel();
         playerlabel.setIcon(die);
         playerlabels.add(playerlabel);
         
         
         if(i % 2 != 0){
            ydisp = ((i -1) / 2)* (die.getIconWidth() + 3);
            xdisp = 0;
         }
         else{
            xdisp = die.getIconWidth() + 3;
         }
         playerlabel.setBounds(1002 + xdisp, 258 + ydisp, die.getIconWidth(), die.getIconHeight());
         playerlabel.setVisible(true);
         
         bPane.add(playerlabel, new Integer(1)); 
      }   
      
      String[] setnames = new String[]{"Train Station", "Secret Hideout", "Church", "Hotel", "Main Street", "Jail", "General Store", "Ranch", "Bank", "Saloon"};
      
      XMLParser xml = new XMLParser();
      
      for(int i = 0; i < setnames.length; i++){
         JLabel sceneCard = new JLabel();
         ImageIcon cardBackIcon = new ImageIcon("CardBack-small.jpg");
         sceneCard.setIcon(cardBackIcon);
         
         String[] coords = new String[]{}; //blank, filled in later
         
         try{
            coords = xml.getRoomCoordinates(setnames[i], "board.xml");
         }
         catch(Exception ParserConfigurationException){
            //any error messages would go here
         }
         int x = Integer.parseInt(coords[0]);
         int y = Integer.parseInt(coords[1]);
         int h = Integer.parseInt(coords[2]);
         int w = Integer.parseInt(coords[3]);
         sceneCard.setBounds(x, y, cardBackIcon.getIconWidth(), cardBackIcon.getIconHeight()); //changed
         
         bPane.add(sceneCard, new Integer(1));
         cardLabels.put(setnames[i], sceneCard);
      }
      
      
      displayShotTokens();
      updateStats();
      setStartingCondition(playerNumber);
   }
   
   /*
   displayShotTokens()
   returns: none
   parameters: none
   displays all the shot tokens on the board, and adds their labels to a map field
   */
   public void displayShotTokens(){
      XMLParser xml = new XMLParser();
      for(String roomname : b.getRoomNames()){
         int numberOfTokens = b.getRoom(roomname).getMaxShotTokens();
         ArrayList<JLabel> tokenList = new ArrayList<JLabel>(); //to be added to our map
         for(int i = 1; i <= numberOfTokens; i++){
            String[] coords = new String[]{}; //blank, filled in later
            try{ 
               coords = xml.getShotTokenCoords("" + i, roomname, "board.xml");
            }
            catch(Exception ParserConfigurationException){
               //error message
            }
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            
            JLabel token = new JLabel();
            ImageIcon shot = new ImageIcon("shot.png");
            token.setIcon(shot);
            token.setBounds(x, y, shot.getIconWidth(), shot.getIconHeight());
            
            bPane.add(token, new Integer(1));
            tokenList.add(token);
            
         }
         shotTokens.put(roomname, tokenList);
      }
   }
   
   /*
   setUpMenus(Icon icon)
   returns: none
   parameters:
      Icon icon: icon for the board
   precond: all buttons are initialized with text
   sets up menus, used in initialization- adds standard menu to bPane
   */
   public void setUpMenus(Icon icon){
      JButton[] standardMenu = new JButton[]{bMove, bRole, bRank, bAct, bRehearse, bEnd};
      
      for(int i = 0; i < standardMenu.length - 1; i++){
         standardMenu[i].setBackground(Color.white);
         standardMenu[i].setBounds(icon.getIconWidth() + 10, 30 * (i+1), 100, 20);
         standardMenu[i].addMouseListener(new boardMouseListener());
         bPane.add(standardMenu[i], new Integer(2));
      }
      
      bEnd.setBackground(Color.white);
      bEnd.setBounds(icon.getIconWidth() + 120, 30, 100, 20);
      bEnd.addMouseListener(new boardMouseListener());
      bPane.add(bEnd, new Integer(2));
   }

   
   /*
   setStartingCondition(int playerNumber)
   returns: none
   parameters:
      int playerNumber: number of players
   sets up starting conditions based on number of players
   */
   public void setStartingCondition(int playerNumber){
      if(playerNumber < 4){
         numberOfDays = 3;      
      }
      else if(playerNumber == 5){
         for(Player p : players){
            p.addCredits(2);
         }
      }
      else if(playerNumber == 6){
         for(Player p: players){
            p.addCredits(4);
         }
      }
      else if(playerNumber > 6){
         for(Player p: players){
            p.setRank(2);
            
            JLabel playerlabel = playerlabels.get(players.indexOf(p));
            ImageIcon playerIcon= new ImageIcon(p.playerName.substring(0,1) + "2" + ".png");
            playerlabel.setIcon(playerIcon);
         }
      }
   }
   
   
   /*
   updateStats()
   returns: none
   parameters: none
   displays/updates the current player's stats
   */
   public void updateStats(){
      playerName.setText("Player Name: " + currentPlayer.playerName);
      playerName.setFont(new Font("SansSerif", Font.PLAIN, 18));
      playerName.setBounds(boardWidth + 10, 300, 300, 100);
      
      playerMoney.setText("Money: " + currentPlayer.getMoney());
      playerMoney.setFont(new Font("SansSerif", Font.PLAIN, 18));
      playerMoney.setBounds(boardWidth + 10, 320, 300, 100);
      
      playerCredits.setText("Credits: " + currentPlayer.getCredits());
      playerCredits.setFont(new Font("SansSerif", Font.PLAIN, 18));
      playerCredits.setBounds(boardWidth + 10, 340, 300, 100);
      
      playerTokens.setText("Practice Tokens: " + currentPlayer.getPracticeTokens());
      playerTokens.setFont(new Font("SansSerif", Font.PLAIN, 18));
      playerTokens.setBounds(boardWidth + 10, 360, 300, 100);
      
      bPane.add(playerName, new Integer(2));
      bPane.add(playerMoney, new Integer(2));
      bPane.add(playerCredits, new Integer(2));
      bPane.add(playerTokens, new Integer(2));
   }
   
   
   /*
   endTurn()
   returns: none
   parameters: none
   ends current player's turn, and checks if the day/game is over
   */
   public void endTurn(){
      
      hasMoved = false;
      
      Moderator moderator = new Moderator(numberOfDays);
      XMLParser xml = new XMLParser();
      
      //bPane.add(playerlabels.get(players.indexOf(currentPlayer)), new Integer(3));
      
      if(b.isDayOver()){
         numberOfDays--;
         if(numberOfDays == 0){
            String scoreOutput = "The game is now over.\nFinal Score:\n";
            for(Player player : players){
               scoreOutput += "\tPlayer " + player.playerName + "'s score: " + moderator.tallyScore(player) + "\n";
            }
            JOptionPane.showMessageDialog(boardlabel, scoreOutput);
            return;
         }
         else{
            JOptionPane.showMessageDialog(boardlabel, "There are now " + numberOfDays + " days left.");
            b.clearBoard();
            b.resetBoard();
            String[] coords = new String[]{};
            try{
               coords = xml.getRoomCoordinates("trailer", "board.xml");
            }
            catch(Exception ParserConfigurationException){
               //error message
            }
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            
            for(Player player : players){
               JLabel playerlabel = playerlabels.get(players.indexOf(player));
               Icon playerIcon = playerlabel.getIcon();
               
               playerlabel.setBounds(x, y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
               
            }
            currentPlayer = players.get(0);
            bPane.add(playerlabels.get(0), new Integer(3));
            updateStats();
         }
         
         //make all cards visible and face down again
         for(String roomname : cardLabels.keySet()){
            JLabel cardlabel = cardLabels.get(roomname);
            ImageIcon cardBackIcon = new ImageIcon("CardBack-small.jpg");
            cardlabel.setIcon(cardBackIcon);
            cardlabel.setVisible(true);
         }
      }
      else{
         
         if(players.indexOf(currentPlayer) == (players.size() - 1)){
         currentPlayer = players.get(0);
         }
         else{
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
         }
         updateStats();
         bPane.add(playerlabels.get(players.indexOf(currentPlayer)), new Integer(3));
         JOptionPane.showMessageDialog(boardlabel, "It is now " + currentPlayer.playerName + "'s turn.");
         
      }
      
   }
   
   /*
   move(Player, String)
   returns: none
   parameters:
      Player player: player we're moving
      String roomname: name of the room to move them to
   moves the player to another location on the board
   */
   public void move(Player player, String roomname) throws ParserConfigurationException{
      XMLParser xml = new XMLParser();
      
      String[] coords = xml.getRoomCoordinates(roomname, "board.xml");
      int x = Integer.parseInt(coords[0]);
      int y = Integer.parseInt(coords[1]);
      
      JLabel playerlabel = playerlabels.get(players.indexOf(player));
      Icon playerIcon = playerlabel.getIcon();
      playerlabel.setBounds(x,y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
   }
   
   /*
   numberOfPlayers()
   returns: int
   parameters: none
   returns the number of players for the game
   */
   public int numberOfPlayers(){
      try{
         int playerNum = Integer.parseInt(JOptionPane.showInputDialog(boardlabel, "How many players?"));
         if(playerNum < 2 || playerNum > 8){
            JOptionPane.showMessageDialog(boardlabel, "Please type a number between 2 and 8.");
            return numberOfPlayers();
         }
         return playerNum;
         
      }
      catch(Exception numberFormatException){
         JOptionPane.showMessageDialog(boardlabel, "Please type a number.");
         return numberOfPlayers();
      } 
   }
   
   
   /*
   Makes decisions based on user activity. Controls most in-game logic.
   */
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e){
         
         if (e.getSource()== bAct){
            
            //do nothing if player can't act
            if(currentPlayer.getCurrentRole() == null){
               return;
            }
            
            Moderator moderator = new Moderator(numberOfDays);
            Banker banker = new Banker();
            
            boolean success = moderator.checkAct(currentPlayer); 
            if(success){
               
               if(currentPlayer.getCurrentRole().isOnCard()){
                  banker.disperseRewards(currentPlayer, success);
                  updateStats();
                  JOptionPane.showMessageDialog(boardlabel, "Nice going! You've succeeded.\nRewards: 2 credits");
               }
               else{
                  banker.disperseRewards(currentPlayer, success);
                  updateStats();
                  JOptionPane.showMessageDialog(boardlabel, "Nice going! You've succeeded.\nRewards: 1 credit, 1 dollar");
               }
               
               //index of token to remove
               int tokenRemoved = currentPlayer.getCurrentRoom().getMaxShotTokens() - currentPlayer.getCurrentRoom().getShotTokens() -1;
               
               JLabel shotToken = shotTokens.get(currentPlayer.getCurrentRoom().getName()).get(tokenRemoved);
               shotToken.setVisible(false); //remove it??
               
               //if the player's current scene has 0 shot tokens
               if(currentPlayer.getCurrentRoom().getShotTokens() == 0){
                  
                  //end the scene
                  banker.dispersePayout(currentPlayer.getCurrentRoom()); 
                  updateStats();
                  
                  for(Player playerInScene : currentPlayer.getCurrentRoom().getOccupants()){
                     playerInScene.setCurrentRole(null);
                     playerInScene.removePracticeTokens();
                     try{
                        //move player labesl off the card
                        move(playerInScene, playerInScene.getCurrentRoom().getName()); 
                     }
                     catch(Exception ParserConfigurationException){
                        //display error message
                     }
                  }
                  
                  currentPlayer.getCurrentRoom().removeSceneCard();
                  
                  //scene is now wrapped
                  
                  JOptionPane.showMessageDialog(boardlabel, "That's a wrap!\nThe scene is now over.");
                  
                  cardLabels.get(currentPlayer.getCurrentRoom().getName()).setVisible(false); //make card disappear
                  
               }
               
               
            }
            else{
               if(!(currentPlayer.getCurrentRole().isOnCard())){

                  JOptionPane.showMessageDialog(boardlabel, "Drat! You failed.\nRewards: 1 dollar");
               }
               else{
                  JOptionPane.showMessageDialog(boardlabel, "Drat! You failed.\nRewards: none");
               }
               banker.disperseRewards(currentPlayer, success);
               updateStats();
            }
            endTurn(); 
         }
         
         
         else if (e.getSource()== bRehearse){
            Moderator moderator = new Moderator(numberOfDays);
            
            //if the player is able to rehearse
            if(moderator.checkRehearse(currentPlayer)){
               currentPlayer.addPracticeToken();
               JOptionPane.showMessageDialog(boardlabel, "You recieve 1 practice token.");
               
               updateStats();
               endTurn(); 
            }
            else{
               JOptionPane.showMessageDialog(boardlabel, "You have practiced enough! You must now act.");
            }

         }
         
         
         else if(e.getSource() == bRank){
            
            Moderator moderator = new Moderator(numberOfDays);
            
            String rankString = JOptionPane.showInputDialog(boardlabel, "Enter in your desired rank as a number.");
            
            //display error if the rank enetered is invalid
            if(!"123456".contains("" + rankString) || rankString.length()!=1){
               JOptionPane.showMessageDialog(boardlabel, "It loos like your enetered an invalid rank. Make sure to enter a number between 1 and 6.");
               return;
            }
            //display error if player is not in the casting office
            if(currentPlayer.getCurrentRoom().getName() != "Casting Office"){
               JOptionPane.showMessageDialog(boardlabel, "You must be in the Casting Office to purchase a rank");
               return;
            }
            
            int rankRequested = Integer.parseInt(rankString);
            
            String currencyType = JOptionPane.showInputDialog(boardlabel, "Enter in the currency you're using to pay (money/credits)");
            
            //display error if the currency type they entered was invalid
            if(!(currencyType.toLowerCase().equals("money") || currencyType.toLowerCase().equals("credits"))){
                  System.out.println("Please enter in \"credits\" or \"money\" after the rank you would like.");
                  return;
            }
            
            //if the player is allowed to rank up to the requested rank
            else if(moderator.checkRankUp(currentPlayer, rankRequested, currencyType)){
               if(currencyType.equals("credits")){
                  currentPlayer.purchaseRankCredits(rankRequested);
               }
               else{
                  currentPlayer.purchaseRankMoney(rankRequested);
               }
               updateStats();
               ImageIcon rankFace = new ImageIcon(currentPlayer.playerName.substring(0,1) + rankRequested + ".png");
               playerlabels.get(players.indexOf(currentPlayer)).setIcon(rankFace);
            }
            else{
               JOptionPane.showMessageDialog(boardlabel, "You are unable to purchase that rank");
            }   
         }
         
         
         else if (e.getSource()== bMove){
            
            //if player is in a role, do nothing.
            if(currentPlayer.getCurrentRole() != null){
               JOptionPane.showMessageDialog(boardlabel, "You cannot move while in a role.");
               return;
            }
            
            //if the player has already moved, tell them they cannot move again. 
            if(hasMoved){
               JOptionPane.showMessageDialog(boardlabel, "You cannot move twice in a turn.");
               return;
            }
            
            System.out.println("Player " + currentPlayer.playerName + " moved"); 
            
            String dest = JOptionPane.showInputDialog(boardlabel, "Enter in your destination:");
            String dest2 = dest;
            
            if(dest.equals("Casting Office")){
               dest = "office";
            }
            else if(dest.equals("Trailers")){
               dest = "trailer";
            }
            
            XMLParser xml = new XMLParser();
            String[] coords = new String[0]; //blank, filled in later
            
            try{
               coords = xml.getRoomCoordinates(dest, "board.xml");
            }
            catch(Exception ParserConfigurationException){
               //any error catches go here
            }

            Moderator moderator = new Moderator(numberOfDays);

            //if player is allowed to move to their destination
            if(moderator.checkMove(currentPlayer.getCurrentRoom(), dest2)){
               currentPlayer.move(b.getRoom(dest2));
               Icon playerIcon = playerlabels.get(players.indexOf(currentPlayer)).getIcon();
               int x = Integer.parseInt(coords[0]);
               int y = Integer.parseInt(coords[1]);
               
               System.out.println("Player Coords: " +  playerlabels.get(players.indexOf(currentPlayer)).getX() + " " + playerlabels.get(players.indexOf(currentPlayer)).getX());
               System.out.println("New coords: " + x + " " + y); //TEST
               
               playerlabels.get(players.indexOf(currentPlayer)).setBounds(x, y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
               
               System.out.println("Player Index: " + players.indexOf(currentPlayer));
               //System.out.println("Icon at index: " + )
               System.out.println("Finished moving player label"); //TEST
               
               hasMoved = true;
            }
            
            //if player is not moving to Casting Office or Trailers, turn scene card over
            if(!(dest2.equals("Casting Office") || dest2.equals("Trailers"))){
               if(!(currentPlayer.getCurrentRoom().getSceneCard().isDiscovered())){
                  currentPlayer.getCurrentRoom().getSceneCard().setDiscovered();
                  
                  JLabel card = cardLabels.get(currentPlayer.getCurrentRoom().getName());
                  ImageIcon cardIcon = new ImageIcon(); //blank, filled in later
                  
                  try{
                     //get the front of the scene card
                     cardIcon = new ImageIcon(xml.getCardImageName(currentPlayer.getCurrentRoom().getSceneCard().sceneName, "cards.xml"));
                  }
                  catch(Exception ParserConfigurationException){
                     //error message goes here
                  }
                  //turn scene card over
                  card.setIcon(cardIcon);
               }
            }
            //for testing purposes
            System.out.println("Player " + currentPlayer.playerName + " is now in " + currentPlayer.getCurrentRoom().getName());
         }
         
         else if(e.getSource() == bRole){
            
            //if the player already has a role, tell them so
            if(currentPlayer.getCurrentRole() != null){
               JOptionPane.showMessageDialog(boardlabel, "You already have a role.");
               return;
            }
            
            String roleWanted = JOptionPane.showInputDialog(boardlabel, "Select a Role");
            
            Moderator moderator = new Moderator(numberOfDays);
            
            XMLParser xml = new XMLParser();
            
            //if the player is allowed to take the role they want
            if(moderator.checkRole(currentPlayer, roleWanted)){
               
               //for testing purposes
               System.out.println("Player allowed to take role.");
               
               Role role = currentPlayer.getCurrentRoom().getRole(roleWanted);
               currentPlayer.takeRole(role);
               
               String[] coords = new String[]{}; //blank, to be filled in later
               try{
                  coords = xml.getOnCardRoleCoords(currentPlayer.getCurrentRoom().getSceneCard().sceneName, role.name, "cards.xml");
                  
                  String[] roomCoords = xml.getRoomCoordinates(currentPlayer.getCurrentRoom().getName(), "board.xml");
                  
                  //get coordinates of the top left of the scene card
                  int x = Integer.parseInt(coords[0]);
                  int y = Integer.parseInt(coords[1]);
                  
                  //get internal coordinates for role on the scene card
                  int roomx = Integer.parseInt(roomCoords[0]);
                  int roomy = Integer.parseInt(roomCoords[1]);
                  
                  //add internal and board coordinates together
                  coords[0] = "" + (x + roomx);
                  coords[1] = "" + (y + roomy);
               }
               catch(Exception ParserConfigurationException){
                  //error message
               }
               
               //if the role was on the baord, not the scene card
               if(coords.length == 0){
                  try{
                     //get role coordinates from the board
                     coords = xml.getOffCardRoleCoords(currentPlayer.getCurrentRoom().getName(), role.name, "board.xml");
                  }
                  catch(Exception ParserConfigurationException){
                     //error message
                  }
               }
               
               int x = Integer.parseInt(coords[0]);
               int y = Integer.parseInt(coords[1]);
               
               Icon playerIcon = playerlabels.get(players.indexOf(currentPlayer)).getIcon();
               playerlabels.get(players.indexOf(currentPlayer)).setBounds(x, y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
               
               endTurn(); 
            }
         }
         
         else if(e.getSource() == bEnd){
            endTurn();
         }   
         
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }
}