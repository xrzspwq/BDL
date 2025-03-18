package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D;

public class Wire extends Elem {
    private Pair<ElemLogique, Integer> entry;
    private ArrayList<Pair<ElemLogique, Integer>> exit;
    private ArrayList<ArrayList<EnumBool>> output;
    private Point2D posStart;
    private Point2D posEnd;

    

    public Wire( ElemLogique elem1, int indexEntry, Point2D start) {
        super();
        this.entry = new Pair<>(elem1, indexEntry);
        this.exit = new ArrayList<>();
        this.posStart = start;
        // Si le point de départ du cable est un autre cable, il faut connecter le point
        // de départ avec celui du cable déja en place.
    }

    public Pair<ElemLogique, Integer> getEntry() {
        return entry;
    }

    public ArrayList<Pair<ElemLogique, Integer>> getExit() {
        return exit;
    }

    public void changeEntry(ElemLogique e1, int indexIn) {
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

    public boolean connect(ElemLogique elem, Integer int1) {
        exit.add(new Pair<>(elem, int1));
        if (output == null) {
            output = entry.getElem1().evaluate();
        }

        Iterator<ArrayList<EnumBool>> i = output.iterator();
        ArrayList<EnumBool> a = i.next();
        return elem.In.add(a);
    }

    public boolean disconnect(Pair<ElemLogique, Integer> p) {
        //p.getElem1().removeIn(p.getElem2());
        return  exit.remove(p);
    }

    public void setPosEnd(Point2D pos) {
        this.posEnd = pos;
    }

    public void CheminLPC(List<List<boolean>> M) {

        Stack<Point2D> stack = new Stack<Point2D>();
        stack.push(posStart);
        Queue<Point2D> F = new LinkedList<>(stack);
        ArrayList<Point2D> V = new ArrayList<>();
        while (F.size() != 0) {
            Point2D Head = F.remove();
        }
    }


    

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        if (output != null) {
            return output;
        }
        output = entry.getElem1().evaluate();
        
        return output;
    }

}
