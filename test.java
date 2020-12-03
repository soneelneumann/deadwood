import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class test{
   public static void main(String[] args){
      Display d = new Display(new ArrayList<Room>());
      d.setVisible(true);
   }
}