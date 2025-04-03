package src;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Wire extends Elem {
    private Pair<Elem, Integer> entry;
    private Pair<Elem, Integer> exit;
    private ArrayList<ArrayList<EnumBool>> output;
    private Point2D posStart;
    private Point2D posEnd;
    private Boolean state;

    /**
     * Constructs a new instance of a Wire object.
     *
     * @param elem1      The logical element at the entry of the wire.
     * @param indexEntry The index of the input port on the entry element.
     * @param start      The starting position of the wire.
     *
     *                   The constructor initializes the wire with the provided
     *                   entry element, index, and starting position.
     *                   It also sets the name of the wire to "Wire", initializes
     *                   the exit list, the state to true, and the
     *                   starting position of the wire.
     *
     *                   If the starting position of the wire is already connected
     *                   to another wire, the constructor should
     *                   connect the starting point of this wire with the existing
     *                   wire.
     */
    public Wire(Elem elem1, int indexEntry, Point2D start) {
        super();
        name = "Wire";
        this.entry = new Pair<>(elem1, indexEntry);
<<<<<<< Updated upstream
        //this.exit = new ArrayList<>();
        this.posStart = start;
=======
        this.exit = new ArrayList<>();
>>>>>>> Stashed changes
        this.state = true;
        // Si le point de départ du cable est un autre cable, il faut connecter le point
        // de départ avec celui du cable déja en place.
        if (elem1 instanceof Wire) {
            Wire w = (Wire) elem1;
            this.posStart=w.getPosStart();
        }else {
            this.posStart = start;
        }
    }

    public Wire(Point2D start) {//check
        super();
        name = "Wire";
        //this.entry = new Pair<>(elem1, indexEntry);
        //this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
        // Si le point de départ du cable est un autre cable, il faut connecter le point
        // de départ avec celui du cable déja en place.
    }

    public Point2D getPosStart(){
        return posStart;
    }

    /**
     * Returns the entry point of the wire, which is a pair consisting of a logical
     * element and its input index.
     *
     * @return A pair containing the logical element and its input index
     *         representing the entry point of the wire.
     */
    public Pair<Elem, Integer> getEntry() {
        return entry;
    }

    /**
     * Returns the list of exit points of the wire, which are pairs consisting of a
     * logical element and its output index.
     *
     * @return A list of pairs containing the logical elements and their output
     *         indices representing the exit points of the wire.
     */
    public Pair<Elem, Integer> getExit() {
        return exit;
    }

<<<<<<< Updated upstream
    public void setExit(Pair<Elem,Integer> exit)
    {
        this.exit = exit;
    }

    /**
     * Returns the current state of the wire.
     *
     * @return A boolean value representing the state of the wire.
     * 
     */
    public boolean getState() {
        return state;
    }

    /**
     * Changes the entry point of the wire to the provided logical element and input
     * index.
     *
     * @param e1      The logical element to set as the new entry point.
     * @param indexIn The input index of the new entry point on the logical element.
     *
     *                This method updates the entry point of the wire to the
     *                provided logical element and input index.
     *                It also evaluates the output of the new entry element and
     *                updates the wire's output accordingly.
     *                If the wire already has an exit point, it is added to the exit
     *                list before updating the entry point.
     */
    public void changeEntry(Elem e1, int indexIn) {
        //exit.add(entry);
=======
    public void setEntry(Elem e1, int indexIn, Point2D start) {//check
        /**
         * Change the entry of the wire to a new logical element.
         * The current entry is added to the exit list before updating.
         *
         * @param e1 the new logical element to set as the entry.
         */
        exit.add(entry);
>>>>>>> Stashed changes
        this.entry = new Pair<>(e1, indexIn);

        output = e1.evaluate();
    }

<<<<<<< Updated upstream
    /**
     * Connects the current wire to another logical element at the specified index.
     * If the output of the current wire's entry point is null, it evaluates the
     * entry point's output.
     * Then, it adds the first output of the current wire to the input list of the
     * connected element.
     *
     * @param elem The logical element to connect to.
     * @param int1 The index at which to connect to the specified element.
     * @return True if the connection is successful, false otherwise.
     */
    public boolean connect(Elem elem, Integer indexExit) {
        
        this.exit = new Pair<>(elem, indexExit);
        
=======
    public boolean addExit(Elem elem, Integer int1) {//check
        exit.add(new Pair<>(elem, int1));
>>>>>>> Stashed changes
        if (output == null) {
            output = entry.getElem1().evaluate();
        }

        Iterator<ArrayList<EnumBool>> i = output.iterator();
        ArrayList<EnumBool> a = i.next();
        return elem.In.add(a);
    }

    /**
     * Disconnects the specified pair of logical element and its output index from
     * the current wire.
     *
     * @param p A pair consisting of a logical element and its output index to
     *          disconnect.
     * @return True if the disconnection is successful, false otherwise.
     */
    public void disconnect() {//check
        if (exit!=null) 
            exit = null;
    }

<<<<<<< Updated upstream
    /* 
    public boolean disconnect(Pair<Elem, Integer> p) {
        // p.getElem1().removeIn(p.getElem2());
        return exit.remove(p);
    }

    /**
     * Sets the end position of the wire.
     *
     * @param pos The new end position of the wire. This parameter is a Point2D
     *            object representing the coordinates of the end position.
     *            The x-coordinate represents the horizontal position, and the
     *            y-coordinate represents the vertical position.
     *
     */
=======
    public Point2D getPosEnd() {return posEnd;}

    public Point2D getPosStart() {return posStart;}
    
>>>>>>> Stashed changes
    public void setPosEnd(Point2D pos) {
        this.posEnd = pos;
    }

    /**
     * Sets the start position of the wire.
     *
     * @param pos The new start position of the wire. This parameter is a Point2D
     *            object representing the coordinates of the start position.
     *            The x-coordinate represents the horizontal position, and the
     *            y-coordinate represents the vertical position.
     *
     */
    public void setPosStart(Point2D pos) {
        this.posStart = pos;
    }

<<<<<<< Updated upstream
    /**
     * This function calculates the shortest path (Chemin Le Plus Court) from the
     * start position to the end position in a grid represented by a 2D list of
     * integers.
     * The grid is traversed using a breadth-first search (BFS) algorithm.
     *
     * @param M A 2D list of integers representing the grid. Each cell contains
     *          either 0 or 1.
     *          A cell with value 1 is considered as an obstacle and cannot be
     *          traversed.
     *          A cell with value 0 is considered as an empty cell and can be
     *          traversed.
     *
     * @return A stack of Point2D objects representing the shortest path from the
     *         start position to the end position.
     *         If the end position is not reachable, the function throws an
     *         AucunChemin exception.
     *
     * @throws AucunChemin If the end position is not reachable from the start
     *                     position.
     */
=======

>>>>>>> Stashed changes
    public Stack<Point2D> CheminLPC(ArrayList<ArrayList<Integer>> M) throws AucunChemin {

        Stack<Point2D> c = new Stack<Point2D>();
        c.push(posStart);
        Queue<Point2D> F = new LinkedList<>(c);
        ArrayList<Point2D> V = new ArrayList<>(F);

        if ( posEnd.getX() < 0 || posEnd.getX() >= M.size()  || posEnd.getY() < 0 || posEnd.getY() >= M.size() ) {
            throw new IllegalArgumentException();
        }   


        while (!F.isEmpty()) {
            Point2D Head = F.remove();
<<<<<<< Updated upstream
            if (Head == posEnd)
                return c;
=======
            if (Head.equals(posEnd)) return c;
>>>>>>> Stashed changes

            else {
                if (Head.getX() < 0 || Head.getX() >= M.size() || Head.getY() < 0 || Head.getY() >= M.size()) {
                    Head.setLocation(F.peek().getX(), F.peek().getY());
<<<<<<< Updated upstream
                }
                if (M.get((int) Head.getX()).get((int) Head.getY()) == 1)
                    if (V.contains(Head)) {
                        V.add(Head);
                        Stack<Point2D> c1 = c, c2 = c, c3 = c, c4 = c;
=======
              }
                if ( M.get((int) Head.getX()).get((int) Head.getY()) == 0 ) 
                  if( !V.contains(Head)){ 
                    V.add(Head);  
                    Stack<Point2D> c1 = c, c2=c, c3 =c , c4 = c; 
>>>>>>> Stashed changes

                        c1.push(new Point2D.Double(Head.getX() + 1, Head.getY()));
                        c2.push(new Point2D.Double(Head.getX() - 1, Head.getY()));
                        c3.push(new Point2D.Double(Head.getX(), Head.getY() + 1));
                        c4.push(new Point2D.Double(Head.getX() + 1, Head.getY() - 1));
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
        state = true;
        if (output != null) {
            return output;
        }
        output = entry.getElem1().evaluate();

        for (int i = 0; i < output.size(); i++) {
            if (output.get(i).contains(EnumBool.ERR)) {
                state = false;
            }
        }
        
        if(entry.getElem1().TailleBus!=exit.getElem1().TailleBus){
            state=false;
        }

        /* 
        for (int i = 0; i < exit.size(); i++) {
            if (entry.getElem1().TailleBus != exit.get(i).getElem1().TailleBus) {
                state = false;
            }
        }
        */

        return output;
    }

}
