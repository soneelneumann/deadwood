import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class Display extends JFrame{
   // JLabels
   JLabel boardlabel;
   JLabel cardlabel;
   JLabel playerlabel;
   JLabel mLabel;
     
   //JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;
   
   public boolean hasAction;
  
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
      
      hasAction = false;
      
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
   }
   
   /*
   class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         hasAction = true;
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
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
   */
   
   /*
   public int numberOfPlayers(){
      try{
         int playerNum = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?"));
         if(playerNum < 2 || playerNum > 8){
            JOptionPane.showMessageDialogue("Please type a number between 2 and 8.");
            return numberOfPlayers()
         }
         return playerNum;
         
      }
      catch(Exception numberFormatException){
         JOptionPane.showMessageDialogue("Please type a number.");
         return numberOfPlayers();
      }
      
      
   }
   
   public static void main(String[] args) {
      Display board = new Display();
      board.setVisible(true);
    
      // Take input from the user about number of players
      String input = JOptionPane.showInputDialog(board, "How many players?"); 
      System.out.println(input);
  }
  */
}