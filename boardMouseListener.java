/*mouse listener for the board. test for now*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         Object o = e.getSource();
         JButton b = null; //blank, to be filled in
         if(o instanceof JButton){
            b = (JButton)o;
         }
         if (b.getText().equals("ACT")){
            //playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
         }
         else if (b.getText().equals("REHEARSE")){
            System.out.println("Rehearse is Selected\n");
         }
         else if (b.getText().equals("MOVE")){
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
