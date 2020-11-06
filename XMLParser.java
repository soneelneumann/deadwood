import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class XMLParser{
   
   /*Loader initializer*/
   public XMLParser(String fileName) throws ParserConfigurationException{
      //get a document from xml file with name fileName
      Document doc = getDocFromFile(fileName);
      
   }
   
   
   /*
      getCardData(Document d)
      description: gets data for Scene Cards from d
      returns: ArrayList of Scene Cards for board
      precond: Document is not empty
      postcond: ArrayList with full scene cards is returned
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
            }
         }
         
         //add the Scene Card s to scenecards
         scenecards.add(s);
      }
      return scenecards;
      //return new ArrayList<SceneCard>();//temp
   }
   
   /*
   getBoardData()
   returns: ArrayList<Room> of rooms with all neighbors filled in, plus trailers and casting office
   precond: Document is not null
   postcond: rooms are returned with corrected neighboring 
   */
   public ArrayList<Room> getBoardData(Document d){
      Element root = d.getDocumentElement();
      NodeList scenes = root.getChildNodes();
      ArrayList<Room> roomlist = new ArrayList<Room>(); //what we'll return at the end
      
      //add all rooms to roomlist without neighbors
      for(int i = 0; i < scenes.getLength(); i++){
         Node room = scenes.item(i);
         
         if("trailer".equals(room.getNodeName())){
            Trailers t = new Trailers();
            t.roomName = "Trailers";
            roomlist.add(t); 
         }
         else if("office".equals(room.getNodeName())){
            CastingOffice c = new CastingOffice();
            c.roomName = "Casting Office";
         }
         else{
            Scene s = new Scene();
            s.roomName = room.getAttributes().getNamedItem("name").getNodeValue();
            roomlist.add(s);
         }
      }
      
      //fill in neighbors
      
      NodeList rooms = root.getChildNodes();
      
      for(int i = 0; i < rooms.getLength(); i++){
         Node room = rooms.item(i);
         
         String roomName = ""; //blank, to be filled in
         
         //get the Node's name
         if("set".equals(room.getNodeName())){
            roomName = room.getAttributes().getNamedItem("name").getNodeValue();
         }
         else if("office".equals(room.getNodeName())){
             roomName = "Casting Office";
         }
         else{
            roomName = "Trailers";
         }
         
         Room thisRoom = new Room(); //blank, to be filled in
         
         //find the Room object in the roomlist Array with the same name
         for(Room r : roomlist){
            if(r.roomName.equals(roomName)){
               thisRoom = r;
            }
         }
         
         //NodeList neighbors = room.getElementsByTagName("neighbors").item(0).getChildNodes();
         NodeList neighbors = root.getChildNodes(); //blank, to be filled in
         
         NodeList children = room.getChildNodes();
         for(int k = 0; k<children.getLength(); k++){
            Node child = children.item(k);
            if(child.getNodeName().equals("neighbors")){
               neighbors = child.getChildNodes();
            }
         }
         
         for(int j = 0; j < neighbors.getLength(); j++){
            Node n = neighbors.item(j);
            String neighborName = n.getAttributes().getNamedItem("name").getNodeValue();
            
            for(Room r: roomlist){
               if(r.roomName.equals(neighborName)){
                  thisRoom.addNeighbor(r);
               }
            }
         }
      }
      
      return roomlist;
      //return new ArrayList<Room>();//temp
   }
   
   /*
      getDocFromFile(String fileName)
      returns: parsed Document object
      precond: 
      postcond: Document object parsed from xml file is returned
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
   
   
}