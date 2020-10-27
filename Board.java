import java.util.ArrayList;

public class Board{
   
   private ArrayList<Scene> scenes;
   private Trailers trailers;
   private CastingOffice castingOffice;
   
   public Board(){
      scenes = new ArrayList<Scene>();
      trailers = new Trailers();
      castingOffice = new CastingOffice();
   }
}