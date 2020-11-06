import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class DeadwoodLoader{
   
   /*Loader initializer*/
   public DeadwoodLoader(String fileName) throws ParserConfigurationException{
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