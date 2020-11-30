import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class test{
    public static int numberOfPlayers(Display board){
      try{
         int playerNum = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?"));
         if(playerNum < 2 || playerNum > 8){
            JOptionPane.showMessageDialog(board, "Please type a number between 2 and 8.");
            return numberOfPlayers(board);
         }
         return playerNum;
         
      }
      catch(Exception numberFormatException){
         JOptionPane.showMessageDialog(board, "Please type a number.");
         return numberOfPlayers(board);
      }
      
   }
   
   public static void main(String[] args){
      
      Display board = new Display();
      board.setVisible(true);
      boardMouseListener listen = new boardMouseListener();
      
   }
}