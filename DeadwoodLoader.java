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
      precond:
      postcond:
   */
   public ArrayList<SceneCard> getCardData(Document d){
      Element root = d.getDocumentElement(); //get root of the tree
      NodeList cards = root.getElementsByTagName("card");
      
      //read data from each node
      for(int i = 0; i < cards.getLength(); i++){
      
         Node card = cards.item(i);
         String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
         //int cardBudget = card.getAttributes().getNamedItem("budget").getNodeValue();
         
         
      }
      return new ArrayList<SceneCard>();
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