/*
Chris Brown, Soneel Neumann

Parses XML documents for game information, specifically about the Board and Scene Cards. 
   Used at the start of the game during initialization.
*/

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.TreeMap;

public class XMLParser{
   
   
   /*XMLParser Initializer*/
   public XMLParser(){
      //nothing to do here yet
   }
   
   /*
   getCards(String filename)
   returns: ArrayList<SceneCard>
   parameters:
      filename: name of the xml file we want information from
   precond: filename.xml must be full of properly formatted card data and in the same directory.
   */
   public ArrayList<SceneCard> getCards(String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      return getCardData(doc);
   }
   
   /*
   getCardData(Document d)
   returns: ArrayList of Scene Cards for board
   parameters:
      d: Document object we're parsing
   precond: d is not empty and is formatted properly
   gets data for Scene Cards from a Document object
   */
   public ArrayList<SceneCard> getCardData(Document d){
      
      Element root = d.getDocumentElement(); //get root of the tree
      NodeList cards = root.getElementsByTagName("card");
      ArrayList<SceneCard> scenecards = new ArrayList<SceneCard>();
      
      //read data from each card node
      for(int i = 0; i < cards.getLength(); i++){
         
         SceneCard s = new SceneCard();
         
         Node card = cards.item(i);
         
         //get the scene name and budget
         String sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
         int sceneBudget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
         
         //add these to the scene card
         s.setSceneBudget(sceneBudget);
         s.sceneName = sceneName;
         
         //create a nodelists for all the card's child nodes
         NodeList children = card.getChildNodes();
         
         for(int j = 0; j < children.getLength(); j++){
            Node child = children.item(j);
            
            if("scene".equals(child.getNodeName())){
               
               //get the attribute "number" from the scene and convert it from String to int
               int sceneNumber = Integer.parseInt(child.getAttributes().getNamedItem("number").getNodeValue());
               
               //get the scene text from the element content
               String sceneText = child.getTextContent();
               
               /*Now add these to the scene card*/
               s.setSceneNumber(sceneNumber);
               s.setSceneText(sceneText);
               
            }
            
            else if("part".equals(child.getNodeName())){
                  
               //create a new role to add to the scene card
               Role r = new Role();
                  
               String partName = child.getAttributes().getNamedItem("name").getNodeValue();
               
               int partRank = Integer.parseInt(child.getAttributes().getNamedItem("level").getNodeValue());
               
               //add these values to newly created role
               r.name = partName;
               r.setRank(partRank);
               
               NodeList part_children = child.getChildNodes();
               
               for(int k = 0; k < part_children.getLength(); k++){
                  Node part_child = part_children.item(k);
                  
                  if("line".equals(part_child.getNodeName())){
                     
                     //get role line from this element's text content
                     String roleLine = part_child.getTextContent();
                     
                     r.setLine(roleLine);
                  }
               }
               //add role to scene card
               s.addRole(r);
               r.setOnCard(true); //lets role know it is on a scene card
            }
         }
         
         //add the Scene Card s to scenecards
         scenecards.add(s);
      }
      return scenecards;
      //return new ArrayList<SceneCard>();//temp
   }
   
   /*get room information from a document d*/
   public ArrayList<Room> getRooms(String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      return getBoardData(doc);
   }
   
   /*
   getBoardData(Document d)
   returns: ArrayList<Room> 
   parameters:
      d: Document object we're parsing
   precond: Document is not empty and is formatted correctly
   returns a list of rooms with filled in values, ready for use in Deadwood
   */
   public ArrayList<Room> getBoardData(Document d){
      Element root = d.getDocumentElement();       
      NodeList scenes = root.getElementsByTagName("set");
      
      ArrayList<Room> roomlist = new ArrayList<Room>(); //what we'll return at the end
      
      //add all scenes to roomlist, with shot tokens
      for(int i = 0; i < scenes.getLength(); i++){
         Node room = scenes.item(i);
         
         Scene s = new Scene();
         s.setName(room.getAttributes().getNamedItem("name").getNodeValue());
         
                     
         //set up scene's shot tokens
         NodeList children = room.getChildNodes(); 
         for(int j = 0; j < children.getLength(); j++){
            Node child = children.item(j);
            
            
            if(child.getNodeName().equals("takes")){
               int shotTokens = 0; //scene has no shot tokens unless file says otherwise
               NodeList takes = child.getChildNodes();
               for(int k = 0; k < takes.getLength(); k++){
                  Node take = takes.item(k);
                  if(take.getNodeType() == Node.ELEMENT_NODE){
                     shotTokens ++;
                  }
                  
               }
               
               //give set appropriate amount of shot tokens 
               s.setMaxShotTokens(shotTokens);
            }
            else if(child.getNodeName().equals("parts")){
               NodeList parts = child.getChildNodes();
               for(int k = 0; k < parts.getLength(); k++){
                  if(parts.item(k) != null){
                     Node part = parts.item(k);
                     if(part.getNodeName().equals("part")){
                        if(part.getNodeType() == Node.ELEMENT_NODE){
                           Role r = new Role();
                           String partName = part.getAttributes().getNamedItem("name").getNodeValue();
                           int partRank = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
                           
                           r.name = partName; 
                           r.setRank(partRank);
                           
                           NodeList partChildren = part.getChildNodes();
                           for(int m = 0; m < partChildren.getLength(); m++){
                              Node partChild = partChildren.item(m);
                              if(partChild.getNodeType() == Node.ELEMENT_NODE){
                                 if(partChild.getNodeName().equals("line")){
                                    String partLine = partChild.getTextContent();
                                    r.setLine(partLine);
                                    r.setOnCard(false);
                                 }
                              }
                           }
                           s.addRole(r); //add role to the scene
                        } 
                     }
                  }
                  
               }
            }
            //add full prepped scene to the roomlist 
         }
         roomlist.add(s);
      }
      
      
      //add trailers to roomlist
      Trailers t = new Trailers();
      t.setName("trailer"); 
      roomlist.add(t);
      
      //add casting office to roomlist
      
      
         //get prices for money and credits
      int[] creditPrices = getPrices(root, "credit");
      int[] moneyPrices = getPrices(root, "dollar");
      CastingOffice c = new CastingOffice(moneyPrices, creditPrices);
      c.setName("office"); 
      roomlist.add(c);
      
      //fill in neighbors
      
      NodeList sets = root.getElementsByTagName("set"); 
      
      //fill in neighbors for the sets
      
      for(int i = 0; i < sets.getLength(); i++){
         Node set = sets.item(i);
         if(set.getNodeType() == Node.ELEMENT_NODE){
            roomlist = addNeighbors(set, roomlist);
         }  
      }
      
      //fill in neighbors for the trailers
      NodeList trailers = root.getElementsByTagName("trailer");
      
      for(int i = 0; i < trailers.getLength(); i++){
         Node trailer = trailers.item(i);
         if(trailer.getNodeType() == Node.ELEMENT_NODE){
            roomlist = addNeighbors(trailer, roomlist);
         }
      }
      
      //fill in neighbors for the casting office
      NodeList offices = root.getElementsByTagName("office");
      
      for(int i = 0; i < offices.getLength(); i++){
         Node office = offices.item(i);
         if(office.getNodeType() == Node.ELEMENT_NODE){
            roomlist = addNeighbors(office, roomlist);
         }
      }
      
      return roomlist;
   }
   
   /*
   addNeighbors(Node room, ArrayList<Room> roomlist)
   returns: ArrayList<Room>
      room: room to use as a potential neighbor
      roomlist: list of all rooms, which will be modified
   precond: Node room != Null, roomlist != null
   fills out the neighbors fields for any rooms which have a passed room as a neighbor
   */
   private ArrayList<Room> addNeighbors(Node room, ArrayList<Room> roomlist){
      String roomName = ""; //blank, to be filled in later
      
      if(room.getNodeName().equals("set")){
         roomName = room.getAttributes().getNamedItem("name").getNodeValue();

      }
      else if(room.getNodeName().equals("office")){
         roomName = "office";
      }
      else{
         roomName = "trailer";
      }
      
      Room thisRoom = new Room(); //blank, to be filled in later
      for(Room r: roomlist){
         if(r.getName().equals(roomName)){
            thisRoom = r; //specific room object to fill in neighbors for
         }
      }
      NodeList children = room.getChildNodes();
      for(int i = 0; i < children.getLength(); i++){
         Node child = children.item(i);
         if(child.getNodeName().equals("neighbors")){
            NodeList neighbors = child.getChildNodes();
            for(int j = 0; j < neighbors.getLength(); j++){
               Node neighbor = neighbors.item(j);
               //if the node isn't blank space:
               if(neighbor.getNodeType() == Node.ELEMENT_NODE){
                  String neighborName = neighbor.getAttributes().getNamedItem("name").getNodeValue();
                  for(Room r: roomlist){
                     if(r.getName().equals(neighborName)){
                        thisRoom.addNeighbor(r); //add r as a neighbor of thisRoom
                     }
                  }
               }
            }
         }
      }
      return roomlist; //return modified roomlist
   }
   
   /*
   getPrices(Element root, String currency)
   returns: int[] 
   parameters:
      root: root element of document
      currency: string containing a currency type to get prices for
   precond: currency == "credit" or "dollar"
   returns an array of the relevant rank prices in a certain currency. 
   */
   public int[] getPrices(Element root, String currency){
      int[] prices = new int[5];
      
      NodeList offices = root.getElementsByTagName("office");
      
      Node office = offices.item(0);
      
      NodeList officeChildren = office.getChildNodes();
      for(int i = 0; i < officeChildren.getLength(); i++){
         Node child = officeChildren.item(i);
         if(child.getNodeType() == Node.ELEMENT_NODE){
            if(child.getNodeName().equals("upgrades")){
               NodeList upgrades = child.getChildNodes();
               for(int j = 0; j < upgrades.getLength(); j++){
                  Node upgrade = upgrades.item(j);
                  if(upgrade.getNodeType() == Node.ELEMENT_NODE){
                     String upgradeCurrency = upgrade.getAttributes().getNamedItem("currency").getNodeValue();
                     if(upgradeCurrency.equals(currency)){
                        int level = Integer.parseInt(upgrade.getAttributes().getNamedItem("level").getNodeValue());
                        int amt = Integer.parseInt(upgrade.getAttributes().getNamedItem("amt").getNodeValue());
                        
                        prices[level-2] = amt;
                     }
                  } 
               }
            }
         }
      } 
      return prices;
   }
   
   /*
   getDocFromFile(String fileName)
   returns: Document
   parameters:
      filename: name of the file thet we're parsing from
   precond: file.xml exists in the directory and is nonempty
   returns Document object parsed from xml file
   */
   public Document getDocFromFile(String fileName) throws ParserConfigurationException{
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = null;
      
      try{
         doc = db.parse(fileName);
      }catch (Exception ex){
         System.out.println("Failure to parse XML");
         ex.printStackTrace();
      }
      
      return doc;
   }
   
   
   /*
   getRoomCoordinates
   returns string array with coordinates as elements
   */
   public String[] getRoomCoordinates(String roomname, String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      Element root = doc.getDocumentElement();
      
      
      NodeList trailers = root.getElementsByTagName("trailer");
      Node trailer = trailers.item(0);
      if(trailer.getNodeName().equals(roomname)){
         NodeList children = trailer.getChildNodes();
         for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("area")){
               String x = child.getAttributes().getNamedItem("x").getNodeValue();
               String y = child.getAttributes().getNamedItem("y").getNodeValue();
               String h = child.getAttributes().getNamedItem("h").getNodeValue();
               String w = child.getAttributes().getNamedItem("w").getNodeValue();
               return new String[]{x, y, h, w}; //returns coordinates as a new String[]
            }
         }
      }
      
      Node office = root.getElementsByTagName("office").item(0);
      if(office.getNodeName().equals(roomname)){
         NodeList children = office.getChildNodes();
         for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("area")){
               String x = child.getAttributes().getNamedItem("x").getNodeValue();
               String y = child.getAttributes().getNamedItem("y").getNodeValue();
               String h = child.getAttributes().getNamedItem("h").getNodeValue();
               String w = child.getAttributes().getNamedItem("w").getNodeValue();
               return new String[]{x, y, h, w}; //returns coordinates as a new String[]
            }
         }
      }
      
      //search through each room to find the one we're looking for
      NodeList rooms = root.getChildNodes();
      for(int i = 0; i < rooms.getLength(); i++){
         Node room = rooms.item(i);
         //f this is the right room
         if(room.getNodeType() == Node.ELEMENT_NODE && room.getAttributes().getNamedItem("name").getNodeValue().equals(roomname)){
            NodeList children = room.getChildNodes();
            for(int j = 0; j < children.getLength(); j ++){
               Node child = children.item(j);
               if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("area")){
                  String x = child.getAttributes().getNamedItem("x").getNodeValue();
                  String y = child.getAttributes().getNamedItem("y").getNodeValue();
                  String h = child.getAttributes().getNamedItem("h").getNodeValue();
                  String w = child.getAttributes().getNamedItem("w").getNodeValue();
                  return new String[]{x, y, h, w}; //returns coordinates as a new String[]
               }
            }
         }
      }
      return new String[]{};
   }
   
   //returns coords for a role off the scene card
   //precond: rolename is name of role contained within the room named "roomname"
   public String[] getOffCardRoleCoords(String roomname, String rolename, String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      Element root = doc.getDocumentElement();
      
      NodeList sets = root.getElementsByTagName("set");
      for(int i = 0; i < sets.getLength(); i++){
         Node set = sets.item(i);
         if(set.getAttributes().getNamedItem("name").getNodeValue().equals(roomname)){
            NodeList children = set.getChildNodes();
            for(int j = 0; j < children.getLength(); j++){
               Node child = children.item(j);
               if(child.getNodeName().equals("parts")){
                  NodeList parts = child.getChildNodes();
                  for(int k = 0; k < parts.getLength(); k++){
                     Node part = parts.item(k);
                     if(part.getAttributes().getNamedItem("name").equals(rolename)){
                        NodeList partChildren = part.getChildNodes();
                        for(int m = 0; m < partChildren.getLength(); m++){
                           Node partChild = partChildren.item(m);
                           if(partChild.getNodeName().equals("area")){
                              String x = partChild.getAttributes().getNamedItem("x").getNodeValue();
                              String y = partChild.getAttributes().getNamedItem("y").getNodeValue();
                              String h = partChild.getAttributes().getNamedItem("h").getNodeValue();
                              String w = partChild.getAttributes().getNamedItem("w").getNodeValue();
                              
                              return new String[]{x, y, h, w}; //return coords as a new String[]
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      return new String[]{};
   }
   
   //returns coords for a role on the scene card
   //precond: rolename is a role on card "cardname"
   public String[] getOnCardRoleCoords(String rolename, String cardname, String filename)throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      Element root = doc.getDocumentElement();
      
      NodeList cards = root.getElementsByTagName("card");
      for(int i = 0; i < cards.getLength(); i++){
         Node card = cards.item(i);
         if(card.getAttributes().getNamedItem("name").getNodeValue().equals(cardname)){
            NodeList cardChildren = card.getChildNodes();
            for(int j = 0; j < cardChildren.getLength(); j++){
               Node cardChild = cardChildren.item(j); 
               if(cardChild.getNodeName().equals("part") && 
                     cardChild.getAttributes().getNamedItem("name").getNodeValue().equals(rolename)){
                  
                  NodeList partChildren = cardChild.getChildNodes();
                  for(int k = 0; k < partChildren.getLength(); k++){
                     Node partChild = partChildren.item(k);
                     if(partChild.getAttributes().getNamedItem("name").getNodeValue().equals("area")){
                        String x = partChild.getAttributes().getNamedItem("x").getNodeValue();
                        String y = partChild.getAttributes().getNamedItem("y").getNodeValue();
                        String h = partChild.getAttributes().getNamedItem("h").getNodeValue();
                        String w = partChild.getAttributes().getNamedItem("w").getNodeValue();
                        
                        return new String[]{x, y, h, w}; //return coords as a new String[]
                     }
                  }
               }
            }
         }
      }
      return new String[]{};
   }
}