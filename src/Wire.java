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
        this.exit = new ArrayList<>();
        this.posStart = start;
        this.state = true;
        // Si le point de départ du cable est un autre cable, il faut connecter le point
        // de départ avec celui du cable déja en place.
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
    public ArrayList<Pair<Elem, Integer>> getExit() {
        return exit;
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
        exit.add(entry);
        this.entry = new Pair<>(e1, indexIn);
        output = e1.evaluate();
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
    public boolean connect(Elem elem, Integer int1) {
        exit.add(new Pair<>(elem, int1));
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
    public Stack<Point2D> CheminLPC(List<List<Integer>> M) throws AucunChemin {

        Stack<Point2D> c = new Stack<Point2D>();
        c.push(posStart);
        Queue<Point2D> F = new LinkedList<>(c);
        ArrayList<Point2D> V = new ArrayList<>();

        while (F.size() != 0) {
            Point2D Head = F.remove();
            if (Head == posEnd)
                return c;

            else {
                if (Head.getX() < 0 || Head.getX() >= M.size() || Head.getY() < 0 || Head.getY() >= M.size()) {
                    Head.setLocation(F.peek().getX(), F.peek().getY());
                }
                if (M.get((int) Head.getX()).get((int) Head.getY()) == 1)
                    if (V.contains(Head)) {
                        V.add(Head);
                        Stack<Point2D> c1 = c, c2 = c, c3 = c, c4 = c;

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
        throw new AucunChemin(); // the path is not found
    }

    /**
     * Evaluates the current state of the wire and its connected elements.
     * This method calculates the output of the wire based on the output of its
     * entry point.
     * It also checks the consistency of the bus size between the entry point and
     * the exit points.
     *
     * @return An ArrayList of ArrayLists of EnumBool values representing the output
     *         of the wire.
     *         Each inner ArrayList contains the output values for a specific bus.
     *         If any error (ERR) is encountered in the output, the state of the
     *         wire is set to false.
     *         If the bus size between the entry point and any exit point is
     *         inconsistent, the state of the wire is set to false.
     *         If the output has already been calculated, it is returned directly
     *         without re-evaluation.
     */
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
        for (int i = 0; i < exit.size(); i++) {
            if (entry.getElem1().TailleBus != exit.get(i).getElem1().TailleBus) {
                state = false;
            }
        }
        return output;
    }

}
