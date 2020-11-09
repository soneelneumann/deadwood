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
   
   public XMLParser(){
      //do stuff
   }
   
   /*
      gets card data from file 
   */
   public ArrayList<SceneCard> getCards(String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      return getCardData(doc);
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
   
   /*get room information from a document d*/
   public ArrayList<Room> getRooms(String filename) throws ParserConfigurationException{
      Document doc = getDocFromFile(filename);
      return getBoardData(doc);
   }
   
   /*
   getBoardData()
   returns: ArrayList<Room> of rooms with all neighbors filled in, plus trailers and casting office
   precond: Document is not null
   postcond: rooms are returned with corrected neighboring 
   */
   public ArrayList<Room> getBoardData(Document d){
      Element root = d.getDocumentElement();       
      NodeList scenes = root.getElementsByTagName("set");
      
      ArrayList<Room> roomlist = new ArrayList<Room>(); //what we'll return at the end
      
      //add all scenes to roomlist, with shot tokens
      for(int i = 0; i < scenes.getLength(); i++){
         Node room = scenes.item(i);
         
         
         Scene s = new Scene();
         s.roomName = room.getAttributes().getNamedItem("name").getNodeValue();
         
                     
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
                     //System.out.println(take.getNodeName()); //HERE
                      
                     int takeNumber = Integer.parseInt(take.getAttributes().getNamedItem("number").getNodeValue());
                     
                     //if the take in takes has a higher number than current shot tokens
                     if(shotTokens < takeNumber){
                        shotTokens = takeNumber;
                     }
                  }
                  
               }
               
               //give set appropriate amount of shot tokens 
               s.setShotTokens(shotTokens);
            }
         }
         
         //add full prepped scene to the roomlist
         roomlist.add(s);
      }
      
      //add trailers to roomlist
      Trailers t = new Trailers();
      t.roomName = "trailers"; //different than I thought it would be
      roomlist.add(t);
      
      //add casting office to roomlist
      CastingOffice c = new CastingOffice();
      c.roomName = "office"; //different than I thought it would be
      roomlist.add(c);
      
      //fill in neighbors
      
      //NodeList rooms = root.getChildNodes(); //problem area
      
      NodeList sets = root.getElementsByTagName("set"); //
      
      
      
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
   addNeighbors()
   returns: ArrayList<Room>, modified version of the input arraylist
   precond: Node room != Null, roomlist != null
   postcond: room which corresponds to the Node has all its neighbors filled out
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
         roomName = "trailers";
      }
      
      Room thisRoom = new Room(); //blank, to be filled in later
      for(Room r: roomlist){
         if(r.roomName.equals(roomName)){
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
                     if(r.roomName.equals(neighborName)){
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