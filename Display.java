import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Display extends JFrame{
   // JLabels
   JLabel boardlabel;
   ArrayList<JLabel> cardlabels;
   ArrayList<JLabel> playerlabels; //fill this almost right after initialization
   JLabel mLabel; //?
     
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   
   
   //private ArrayList<String> playerColors; //determines which active players have which color
   private String[] colorOrder; //determines which player number has which colors
  
  // JLayered Pane
   JLayeredPane bPane;
   public Display(){
      // Set the title of the JFrame
      super("Deadwood");
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
      
      
      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
      bAct.addMouseListener(new boardMouseListener());
       
      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
      bRehearse.addMouseListener(new boardMouseListener());
       
      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
      bMove.addMouseListener(new boardMouseListener());
      
      // Place the action buttons in the top layer
      bPane.add(bAct, new Integer(2));
      bPane.add(bRehearse, new Integer(2));
      bPane.add(bMove, new Integer(2));
       
      //important to understand
      int playerNumber = numberOfPlayers(); //number of players in this game
      System.out.println(playerNumber);
      
      //initialize both of our arraylists
      playerlabels = new ArrayList<JLabel>();
      cardlabels = new ArrayList<JLabel>();
      
      //create the players and assign them their colors
      
      
      colorOrder = new String[]{"b", "c", "g", "o", "p", "r", "v", "w"};
      
      int ydisp = 0; //helps keep track of how to order pngs. Represents how far down the screen to display
      int xdisp = 0; //helps track how far right pngs are
      
      //adds all players to the playerlabels arraylist
      for(int i = 1; i <= playerNumber; i++){
         ImageIcon die = new ImageIcon(colorOrder[i-1] + "1.png");
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
   }
   
   
   //listens for activity on the board
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {

         if (e.getSource()== bAct){
            //playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
            
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
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