package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.geom.Point2D;

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
     * @param intest1    The logical element at the entry of the wire.
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
    public Wire(Elem intest1, int indexEntry, Point2D start) {
        super();
        name = "Wire";
        this.entry = new Pair<Elem, Integer>(intest1, indexEntry);
        // this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
    }

    public Wire(Point2D start) {// check
        super();
        name = "Wire";
        // this.entry = new Pair<>(elem1, indexEntry);
        // this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
    }

    public Wire(Elem elem1, Point2D start) {// check
        super();
        name = "Wire";
        this.entry = new Pair<>(elem1, 0);
        // this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
    }
    // une Classe public wire qui permettrait de se connecter par la l'entrée d'un
    // elem plutot que par la sortie

    public Point2D getPosStart() {
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

    public void setExit(Pair<Elem, Integer> exit) {
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
    public void swapEntry(Elem e1, int indexIn) {
        if (e1.equals(this.entry.getElem1())) {
            exit = entry;
            exit.getElem1().in.set(indexIn, this.evaluate().get(indexIn));

            entry.setElem(e1);
            entry.setElem2(indexIn);

            Point2D tmp = (Point2D)posEnd.clone();
            
            posEnd.setLocation(posStart);
            posStart.setLocation(tmp);

            output = e1.evaluate();
        } else {
            this.entry = new Pair<>(e1, indexIn);
            output = e1.evaluate();
        }
    }

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

    public boolean connectExit(Elem elem, int index) throws IndexOutOfBoundsException {
        this.exit = new Pair<Elem, Integer>(elem, index);

        if (output == null) {
            output = entry.getElem1().evaluate();
        }

        try {
            if (entry.getElem1().out.get(0).size() > elem.getNbBusIn()) {
                this.exit = null;
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            state = false;
            return false;
        }

        while (index >= elem.in.size()) {
            elem.in.add(new ArrayList<EnumBool>());
        }

        ArrayList<EnumBool> a = output.get(0);

        try {
            if (elem.in.get(index).isEmpty() || elem.in.get(index).equals(new ArrayList<EnumBool>())) {

                elem.in.set(index, a);
                state = true;
            } else {
                output.clear();
                ArrayList<EnumBool> error = new ArrayList<EnumBool>();
                error.add(EnumBool.ERR);
                elem.in.set(index, error);
                throw new IndexOutOfBoundsException("The element already has a wire on this port");
            }
        } catch (IndexOutOfBoundsException e) {
            exit = null;
            state = false;
        }
        return elem.in.contains(a);
    }

    public boolean connectExit(Elem elem, int index, Point2D posEnd) {
        this.posEnd = posEnd;
        return connectExit(elem, index);
    }

    /**
     * Disconnects the current wire from its exit element, if any.
     * This method sets the exit element to null, effectively removing
     * any connection the wire has to an exit element.
     */
    public void disconnectExit() {
        if (exit != null) {
            exit.getElem1().in.set(exit.getElem2(), new ArrayList<EnumBool>());
            exit = null;
        }
    }

    /*
     * public boolean disconnect(Pair<Elem, Integer> p) {
     * // p.getElem1().removeIn(p.getElem2());
     * return exit.remove(p);
     * }
     * 
     * /**
     * Sets the end position of the wire.
     *
     * @param pos The new end position of the wire. This parameter is a Point2D
     * object representing the coordinates of the end position.
     * The x-coordinate represents the horizontal position, and the
     * y-coordinate represents the vertical position.
     *
     */
    public void setPosEnd(Point2D pos) {
        this.posEnd = (Point2D) pos.clone();
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
        this.posStart = (Point2D) pos.clone();
    }

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
    public Stack<Point2D> CheminLPC(ArrayList<ArrayList<Integer>> M) throws AucunChemin {

        if (M == null || M.isEmpty() || M.get(0).size() == 0) {
            throw new AucunChemin();
        }

        Stack<Point2D> c = new Stack<Point2D>();
        c.push(posStart);
        Queue<Point2D> F = new LinkedList<>(c);
        ArrayList<Point2D> V = new ArrayList<>();
        Point2D Head =(Point2D) posStart.clone();


        V.add(Head);


        if (posEnd.getY() < 0 || posEnd.getY() >= M.size() || posEnd.getX() < 0
                || posEnd.getX() >= M.get((int) posEnd.getY()).size()) {
            throw new IllegalArgumentException("Point out of the matrice");
        }

        if (M.get((int) posEnd.getY()).get((int) posEnd.getX()) != 1) {
            throw new IllegalArgumentException("No 1 found");
        }

        if (M.get((int) Math.round(Head.getY())).get((int) Math.round(Head.getX())) != 1) {
            throw new IllegalArgumentException("No 1 found");
        }

        long mx = Math.round(Math.abs(Head.getX() - posEnd.getX())) / 2;
        while (F.size() != 0) {

            if (Head.getX() == posEnd.getX() && Head.getY() == posEnd.getY()) {
                return c;
            }

            else {
                if (Head.getY() < 0 || Head.getY() >= M.size() || Head.getX() < 0
                        || Head.getX() >= M.get((int) Head.getY()).size()) {
                    F.remove(Head);
                }

                if (Head.getX() < 0 || Head.getX() >= M.get(0).size() || Head.getY() < 0 || Head.getY() >= M.size())
                    continue;

                Point2D last = (Point2D) c.lastElement().clone();

                if (V.contains(Head)) {

                    if (last.getX() < mx) {

                        while (last.getX() < mx) {
                            Point2D point = new Point2D.Double(last.getX()+1,last.getY());
                            c.push(point);
                        }
                    } else if (last.getX() > mx) {

                        while (last.getX() > mx && last.getX() != posEnd.getX()) {
                            Point2D point = new Point2D.Double(last.getX()+1,last.getY());
                            c.push(point);
                        }
                    }

                    if (last.getY() < posEnd.getY()) {
                        System.out.println(M.get(M.size() - 1).size());

                        if (posEnd.getX() == M.get(M.size() - 1).size()) {

                            while (last.getY() < posEnd.getY() || last.getY() != M.get(M.size() - 1).size()) {
                                Point2D point = new Point2D.Double(last.getX(),last.getY()+1);
                                c.push(point);
                            }
                        } else {
                            while (last.getY() < posEnd.getY()) {
                                Point2D point = new Point2D.Double(last.getX(),last.getY()+1);
                                c.push(point);
                            }
                        }
                    } else if (last.getY() > posEnd.getY()) {

                    if (posEnd.getY() == 0) {
                        while (last.getY() > posEnd.getY() || last.getY() != 0) {
                            Point2D point = new Point2D.Double(last.getX(),last.getY()-1);
                            c.push(point);
                        }
                    } else {

                        while (last.getY() > posEnd.getY()) {
                            Point2D point = new Point2D.Double(last.getX(),last.getY()-1);
                            c.push(point);
                        }

                    }

                }

                if (last.getX() < posEnd.getX()) {

                    while (last.getX() < posEnd.getX()) {
                        Point2D point = new Point2D.Double(last.getX()+1,last.getY());
                        c.push(point);
                    }
                } else if (last.getX() > posEnd.getX()) {

                    while (last.getX() > posEnd.getX()) {
                        Point2D point = new Point2D.Double(last.getX()-1,last.getY());
                        c.push(point);
                    }
                }

                if (last.getX() == posEnd.getX() && last.getY() == posEnd.getY()) {
                    return c;
                }
            }

        }
    }
        throw new AucunChemin(); // le point d'arrivée n'est pas trouvé
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {

        output = entry.getElem1().evaluate();

        for (int i = 0; i < output.size(); i++) {
            if (output.get(i).contains(EnumBool.ERR)) {
                state = false;
                output.clear();
                ArrayList<EnumBool> error = new ArrayList<EnumBool>();
                error.add(EnumBool.ERR);
                output.add(error);
                return output;
            } else {
                state = true;
            }
        }

        if (entry.getElem1().tailleBus != exit.getElem1().tailleBus) {
            this.disconnectExit();
            output.clear();
            ArrayList<EnumBool> error = new ArrayList<EnumBool>();
            error.add(EnumBool.ERR);
            output.add(error);
            state = false;
            return output;
        }

        state = true;
        return output;
    }

}
