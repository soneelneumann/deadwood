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
   //ArrayList<JLabel> cardlabels; //??
   ArrayList<JLabel> playerlabels; //fill this almost right after initialization
   JLabel mLabel; //?
     
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   JButton bRank;
   JButton bEnd;
   JButton bRole;
   
   ArrayList<Room> roomlist;
   ArrayList<Player> players;
   
   TreeMap<String, JLabel> cardLabels; //string is the room the card is located

   Board b;
   
   int numberOfDays;
   int boardWidth;
   int boardHeight;
   
   //private ArrayList<String> playerColors; //determines which active players have which color
   private String[] colors; //determines which player number has which colors
  
  // JLayered Pane
   JLayeredPane bPane;
   public Display(ArrayList<Room> roomlist, Board board){
      // Set the title of the JFrame
      super("Deadwood");
      
      
      numberOfDays = 4; //standard number of days

      b = board;
      
      cardLabels = new TreeMap<String, JLabel>(); //Initializes it so we can use it later
   
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
      
      //replaced by something to do with the Board later:
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
      
      
      this.roomlist = roomlist; //maybe at the top?? for clarity
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
   
   
   //listens for activity on the board
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e){

         if (e.getSource()== bAct){
            //playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
            
         }
         else if (e.getSource()== bRehearse){
            //System.out.println("Rehearse is Selected\n");
            clearButtons();
            displayStandardActions();
         }
         else if(e.getSource() == bRank){
            String rank = JOptionPane.showInputDialog(boardlabel, "Enter in your desired rank as a number.");
            ImageIcon rankFace = new ImageIcon("b" + rank + ".png");
            playerlabels.get(0).setIcon(rankFace);
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
            //move player 1 for now, once developed more could be any player depending on turn
            Moderator moderator = new Moderator(numberOfDays);

            
            if(moderator.checkMove(players.get(0).getCurrentRoom(), dest2)){
               players.get(0).move(b.getRoom(dest2));
               Icon playerIcon = playerlabels.get(0).getIcon();
               int x = Integer.parseInt(coords[0]);
               int y = Integer.parseInt(coords[1]);
               playerlabels.get(0).setBounds(x /*+ 5*/, y /*+ 5*/, playerIcon.getIconWidth(), playerIcon.getIconHeight());
            }
         }
         
         else if(e.getSource() == bEnd){
            clearButtons();
            displayActingActions();
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