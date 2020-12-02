import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class test{
   
   public static void main(String[] args){
      
      Display board = new Display();
      board.setVisible(true);
      
      //does this need to be here?
      boardMouseListener listen = new boardMouseListener();
   }
}
