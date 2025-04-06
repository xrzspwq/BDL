package src;

import java.util.ArrayList;

public abstract class Elem {
    protected ArrayList<ArrayList<EnumBool>> in;
    protected ArrayList<ArrayList<EnumBool>> out;
    protected String name;
    protected Integer tailleBus;
    protected int nbBusIn;
    protected int nbBusOut;

    /**
     * Constructs a new Elem object with default values.
     *
     * This constructor initializes the input and output lists, sets the default
     * tailleBus to 1,
     * and initializes the name to null.
     *
     */
    public Elem() {
        this.in = new ArrayList<ArrayList<EnumBool>>();
        this.out = new ArrayList<ArrayList<EnumBool>>();
        this.tailleBus = 1;
    }

    /**
     * Sets the name of the Elem.
     *
     * @param name The name to be assigned to the Elem.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the Elem.
     *
     * @return The name of the Elem as a String.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the input connections of the element.
     *
     * @return An ArrayList of ArrayLists containing EnumBool values representing
     *         the input connections of the element.
     */
    public ArrayList<ArrayList<EnumBool>> getIn() {
        return in;
    }

    /**
     * Retrieves the output connections of the element.
     *
     * @return An ArrayList of ArrayLists containing EnumBool values representing
     *         the output connections of the element.
     */
    public ArrayList<ArrayList<EnumBool>> getOut() {
        return out;
    }

    /**
     * Clears all input connections of the element.
     *
     * This method removes all input connections from the element's input list.
     * After calling this method, the getIn() method will return an empty list.
     *
     */
    public void clearIn() {
        in.clear();
    }

    /**
     * Removes the input connection at the specified index from the element.
     *
     * This method removes the input connection at the specified index from the
     * element's input list. After calling this method, the getIn() method will
     * return a list with one less input connection.
     *
     * @param i The index of the input connection to be removed. The index is
     *          zero-based, meaning the first input connection is at index 0, the
     *          second input connection is at index 1, and so on.
     *
     */
    public void removeIn(int i) {
        in.remove(i);
    }

    /**
     * Clears all output connections of the element.
     *
     * This method removes all output connections from the element's output list.
     * After calling this method, the getOut() method will return an empty list.
     *
     */
    public void clearOut() {
        out.clear();
    }

    /**
     * Retrieves the number of input connections for the element.
     *
     * @return An integer representing the number of input connections.
     */
    public int getNbBusIn() {
        return nbBusIn;
    }

    /**
     * Retrieves the number of output connections for the element.
     *
     * @return An integer representing the number of output connections.
     */
    public int getNbBusOut() {
        return nbBusOut;
    }

    public void setNbBusIn(int nbBusIn) {
        this.nbBusIn = nbBusIn;
    }

    public void setNbBusOut(int nbBusOut) {
        this.nbBusOut = nbBusOut;
    }

    // the evalute method that is inherited by the other classes
    public abstract ArrayList<ArrayList<EnumBool>> evaluate();

}
