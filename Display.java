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
     
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   JButton bRank;
   JButton bEnd;
   JButton bRole;
   
   ArrayList<Player> players;
   Player currentPlayer;
   
   TreeMap<String, JLabel> cardLabels; //string is the room the card is located
   
   //stores shot tokens for each scene; string is roomname, list is shot tokens
   TreeMap<String, ArrayList<JLabel>> shotTokens; 
   
   Board b;
   
   int numberOfDays;
   int boardWidth;
   int boardHeight;
   
   private String[] colors; //determines which player number has which colors
  
  // JLayered Pane
   JLayeredPane bPane;
   public Display(Board board){
      // Set the title of the JFrame
      super("Deadwood");
      
      
      numberOfDays = 4; //standard number of days

      b = board;
      
      cardLabels = new TreeMap<String, JLabel>(); //Initializes it so we can use it later
      
      shotTokens = new TreeMap<String, ArrayList<JLabel>>();
      
      playerName = new JLabel();
      playerMoney = new JLabel();
      playerCredits = new JLabel();
   
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
      
      //puts jbuttons in their proper place, displays normal menu
      setUpMenus(icon);
      

      //important to understand
      int playerNumber = numberOfPlayers(); //number of players in this game
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

      
      
      int ydisp = 0; //helps keep track of how to order pngs. Represents how far down the screen to display
      int xdisp = 0; //helps track how far right pngs are
      
      
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
         
         bPane.add(playerlabel, new Integer(1)); //CRUCIAL, MAKES THE THING VISIBLE
      }
      
      
      //add scene card labels:
      
      //replaced by something to do with the Board later: ///////////////////////////////////////////////////////
      //use board.getRoomNames, put an if to exclude Trailers and Casting Office////////////////////////////////////////////////
      
      
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
            //error message maybe
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
   }
   
   /*
      sets up the labels for shot tokens 
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
      displays move, role, rank, end
      
      precond: no other buttons are showing
   */
   public void displayStandardActions(){
      
      bPane.add(bMove);
      bPane.moveToFront(bMove);
      bPane.add(bRole);
      bPane.moveToFront(bRole);
      bPane.add(bRank);
      bPane.moveToFront(bRank);
      bPane.add(bEnd);
      bPane.moveToFront(bEnd);
   }
   
   /*
      displays act, rehearse, end
   */
   public void displayActingActions(){
      
      bPane.add(bAct);
      bPane.moveToFront(bAct);
      bPane.add(bRehearse);
      bPane.moveToFront(bRehearse);
      bPane.add(bEnd);
      bPane.moveToFront(bEnd);
   }
   
   /*
      clears buttons for new menu if needed
   */
   public void clearButtons(){
      bPane.removeAll();
      bPane.moveToBack(bMove);
      bPane.moveToBack(bRole);
      bPane.moveToBack(bRank);
      bPane.moveToBack(bEnd);
   }
   
   /*
      sets up menus, used in initialization- adds standard menu to bPane
      params:
         icon- icon for the board
      precond: all buttons are initialized with text
   */
   public void setUpMenus(Icon icon){
      JButton[] standardMenu = new JButton[]{bMove, bRole, bRank, bEnd};
      JButton[] alternateMenu = new JButton[]{bAct, bRehearse};
      
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
      
      
      for(int i = 0; i < alternateMenu.length; i++){
         alternateMenu[i].setBackground(Color.white);
         alternateMenu[i].setBounds(icon.getIconWidth() + 10, 30 * (i+1), 100, 20);
         alternateMenu[i].addMouseListener(new boardMouseListener());
         //bPane.add(alternateMenu[i], new Integer(2));
      }
   }
   
   /*
   sets the starting conditions for the game
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
         }
      }
   }
   
   
   /*
   displays the current player's stats
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
      
      bPane.add(playerName, new Integer(2));
      bPane.add(playerMoney, new Integer(2));
      bPane.add(playerCredits, new Integer(2));
   }
   
   public void move(Player player, String roomname) throws ParserConfigurationException{
      XMLParser xml = new XMLParser();
      
      String[] coords = xml.getRoomCoordinates(roomname, "board.xml");
      int x = Integer.parseInt(coords[0]);
      int y = Integer.parseInt(coords[1]);
      
      JLabel playerlabel = playerlabels.get(players.indexOf(player));
      Icon playerIcon = playerlabel.getIcon();
      playerlabel.setBounds(x,y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
   }
   
   //listens for activity on the board
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e){
         
         if (e.getSource()== bAct){
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
               int tokenRemoved = currentPlayer.getCurrentRoom().getMaxShotTokens() - currentPlayer.getCurrentRoom().getShotTokens();
               
               JLabel shotToken = shotTokens.get(currentPlayer.getCurrentRoom().getName()).get(tokenRemoved);
               shotToken.setVisible(false); //remove it??
               
               
               if(currentPlayer.getCurrentRoom().getShotTokens() == 0){
                  //end the scene
                  banker.dispersePayout(currentPlayer.getCurrentRoom());
                  for(Player playerInScene : currentPlayer.getCurrentRoom().getOccupants()){
                     playerInScene.setCurrentRole(null);
                     playerInScene.removePracticeTokens();
                     try{
                        move(playerInScene, playerInScene.getCurrentRoom().getName()); //move player labesl off the card
                     }
                     catch(Exception ParserConfigurationException){
                        //display error message
                     }
                  }
                  
                  currentPlayer.getCurrentRoom().removeSceneCard();
                  //scene is now wrapped
                  
                  JOptionPane.showMessageDialog(boardlabel, "That's a wrap!\nThe scene is now over.");
                  //System.out.println("That's a wrap! The scene card is removed and players in the room are removed from their roles.");
               }

            }
            else{
               //System.out.println("Drat! You failed.");
               if(!(currentPlayer.getCurrentRole().isOnCard())){
                  //System.out.println("Rewards: 1 dollar");
                  JOptionPane.showMessageDialog(boardlabel, "Drat! You failed.\nRewards: 1 dollar");
               }
               else{
                  JOptionPane.showMessageDialog(boardlabel, "Drat! You failed.\nRewards: none");
               }
               banker.disperseRewards(currentPlayer, success);
               updateStats();
            }
            
         }
         
         else if (e.getSource()== bRehearse){
            Moderator moderator = new Moderator(numberOfDays);
            
            if(moderator.checkRehearse(currentPlayer)){
               currentPlayer.addPracticeToken();
               
               updateStats();
            }
            else{
               JOptionPane.showMessageDialog(boardlabel, "You have practiced enough! You must now act.");
            }
         }
         else if(e.getSource() == bRank){
            String rank = JOptionPane.showInputDialog(boardlabel, "Enter in your desired rank as a number.");
            ImageIcon rankFace = new ImageIcon(currentPlayer.playerName.substring(0,1) + rank + ".png");
            playerlabels.get(players.indexOf(currentPlayer)).setIcon(rankFace);
         }
         else if (e.getSource()== bMove){
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

            
            if(moderator.checkMove(currentPlayer.getCurrentRoom(), dest2)){
               currentPlayer.move(b.getRoom(dest2));
               Icon playerIcon = playerlabels.get(players.indexOf(currentPlayer)).getIcon();
               int x = Integer.parseInt(coords[0]);
               int y = Integer.parseInt(coords[1]);
               playerlabels.get(players.indexOf(currentPlayer)).setBounds(x, y, playerIcon.getIconWidth(), playerIcon.getIconHeight());
            }
            if(!(dest2.equals("Casting Office") || dest2.equals("Trailers"))){
               if(!(currentPlayer.getCurrentRoom().getSceneCard().isDiscovered())){
                  currentPlayer.getCurrentRoom().getSceneCard().setDiscovered();
                  JLabel card = cardLabels.get(currentPlayer.getCurrentRoom().getName());
                  ImageIcon cardIcon = new ImageIcon(); //blank, filled in later
                  
                  try{
                     cardIcon = new ImageIcon(xml.getCardImageName(currentPlayer.getCurrentRoom().getSceneCard().sceneName, "cards.xml"));
                  }
                  catch(Exception ParserConfigurationException){
                     //error message goes here
                  }
                  card.setIcon(cardIcon);
               }
            }
         }
         
         else if(e.getSource() == bRole){
            String roleWanted = JOptionPane.showInputDialog(boardlabel, "Select a Role");
            
            Moderator moderator = new Moderator(numberOfDays);
            
            XMLParser xml = new XMLParser();
            
            if(moderator.checkRole(currentPlayer, roleWanted)){
               Role role = currentPlayer.getCurrentRoom().getRole(roleWanted);
               currentPlayer.takeRole(role);
               
               String[] coords = new String[]{}; //blank, to be filled in later
               try{
                  coords = xml.getOnCardRoleCoords(currentPlayer.getCurrentRoom().getSceneCard().sceneName, role.name, "cards.xml");
                  
                  String[] roomCoords = xml.getRoomCoordinates(currentPlayer.getCurrentRoom().getName(), "board.xml");
                  
                  int x = Integer.parseInt(coords[0]);
                  int y = Integer.parseInt(coords[1]);
                  int roomx = Integer.parseInt(roomCoords[0]);
                  int roomy = Integer.parseInt(roomCoords[1]);
                  
                  coords[0] = "" + (x + roomx);
                  coords[1] = "" + (y + roomy);
               }
               catch(Exception ParserConfigurationException){
                  //error message
               }
               if(coords.length == 0){
                  try{
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
            }
         }
         
         else if(e.getSource() == bEnd){
            if(players.indexOf(currentPlayer) == (players.size() - 1)){
               currentPlayer = players.get(0);
            }
            else{
               currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            }
            
            //check if the day is over, if true check if the game is over
            
            clearButtons();
            if(currentPlayer.getCurrentRole() != null){
               displayActingActions();
            }
            else{
               displayStandardActions();
            }
            //now display the next player's stats
            updateStats();
            
            JOptionPane.showMessageDialog(boardlabel, "It is now " + currentPlayer.playerName + "'s turn.");
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
}