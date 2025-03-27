package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.geom.Point2D;


public class Wire extends Elem {
    private Pair<Elem, Integer> entry;
    private ArrayList<Pair<Elem, Integer>> exit;
    private ArrayList<ArrayList<EnumBool>> output;
    private Point2D posStart;
    private Point2D posEnd;
    private Boolean state;
    

    public Wire(Elem elem1, int indexEntry, Point2D start) {
        super();
        this.entry = new Pair<>(elem1, indexEntry);
        this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
        // Si le point de départ du cable est un autre cable, il faut connecter le point
        // de départ avec celui du cable déja en place.
    }

    public Pair<Elem, Integer> getEntry() {
        return entry;
    }

    public ArrayList<Pair<Elem, Integer>> getExit() {
        return exit;
    }

    public boolean getState(){return state;}

    public void changeEntry(Elem e1, int indexIn) {
        /**
         * Change the entry of the wire to a new logical element.
         * The current entry is added to the exit list before updating.
         *
         * @param e1 the new logical element to set as the entry.
         */
        exit.add(entry);
        this.entry = new Pair<>(e1, indexIn);
        output = e1.evaluate();
    }

    public boolean connect(Elem elem, Integer int1) {
        exit.add(new Pair<>(elem, int1));
        if (output == null) {
            output = entry.getElem1().evaluate();
        }

        Iterator<ArrayList<EnumBool>> i = output.iterator();
        ArrayList<EnumBool> a = i.next();
        return elem.In.add(a);
    }

    public boolean disconnect(Pair<Elem, Integer> p) {
        //p.getElem1().removeIn(p.getElem2());
        return  exit.remove(p);
    }

    public void setPosEnd(Point2D pos) {
        this.posEnd = pos;
    }

    public void setPosStart(Point2D pos) {
        this.posStart = pos;
    }


    public Stack<Point2D> CheminLPC(List<List<Integer>> M) throws AucunChemin {

        Stack<Point2D> c = new Stack<Point2D>();
        c.push(posStart);
        Queue<Point2D> F = new LinkedList<>(c);
        ArrayList<Point2D> V = new ArrayList<>();

        while (F.size() != 0) {
            Point2D Head = F.remove();
            if (Head == posEnd ) return c;

            else{
              if ( Head.getX() < 0 || Head.getX() >= M.size()  || Head.getY() < 0 || Head.getY() >= M.size() ) {
                    Head.setLocation(F.peek().getX(), F.peek().getY());
              }
                if ( M.get((int) Head.getX()).get((int) Head.getY()) == 1 ) 
                  if( V.contains(Head)){ 
                    V.add(Head);  
                    Stack<Point2D> c1 = c, c2=c, c3 =c , c4 = c; 

                    c1.push(new  Point2D.Double(Head.getX()+ 1 ,  Head.getY()));
                    c2.push(new  Point2D.Double(Head.getX() - 1 , Head.getY()));
                    c3.push(new  Point2D.Double(Head.getX(), Head.getY() + 1));
                    c4.push(new  Point2D.Double(Head.getX() + 1 , Head.getY()-1));
                    F.addAll(c1);
                    F.addAll(c2);
                    F.addAll(c3);
                    F.addAll(c4);
                  }
            }
        }
        throw new AucunChemin(); // le point d'arrivée n'est pas trouvé
    }

    

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        state=true;
        if (output != null) {
            return output;
        }
        output = entry.getElem1().evaluate();

        for(int i=0;i<output.size();i++) {
            if(output.get(i).contains(EnumBool.ERR)){
                state=false;
            }
        } 
        for(int i=0;i<exit.size();i++) {
            if(entry.getElem1().TailleBus!=exit.get(i).getElem1().TailleBus){
                state=false;
            }
        }
        return output;
    }

}
