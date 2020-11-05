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
         String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
         int cardBudget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
         
         //add these to the scene card
         
         NodeList children = card.getChildNodes();
         
         for(int j = 0; j < children.getLength(); j++){
            Node child = children.item(j);
            
            if("scene".equals(child.getNodeName())){
               
               //get the attribute "number" from the scene and convert it from String to in
               int sceneNumber = Integer.parseInt(child.getAttributes().getNamedItem("number").getNodeValue());
               
               //get the scene text from the element content
               String sceneText = child.getTextContent();
               
               /*Now add these to the scene card*/
               
               
               
            }
            
            else if("part".equals(child.getNodeName())){
               //do stuff with the part
               
               //additional for loop
                  //if line, extract that and put it in Scene Card
                  //if area, don't do anything with that we don't need it right now 
            }
         }
         
         //add the Scene Card s to scenecards
      }
      //return scenecards
      return new ArrayList<SceneCard>();//temp
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