/*
Soneël Neumann, Chris Brown
The main initializer for Deadwood. To run Deadwood, run this file. 
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class Deadwood{
   public static void main(String[] args) throws ParserConfigurationException{
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
      b.setRoomList(roomList); //fill in roomlist for board
      b.resetBoard(); //initializes scene cards and shot tokens

      Display d = new Display(b);
      d.setVisible(true);
   }
}