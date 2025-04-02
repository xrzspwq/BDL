package src;

import java.util.ArrayList;

public abstract class Elem {
    protected ArrayList<ArrayList<EnumBool>> In;
    protected ArrayList<ArrayList<EnumBool>> Out;
    protected String name;
    protected Integer TailleBus;
    protected int NbBusIn;
    protected int NbBusOut;
    // protected ArrayList<Integer> TailleBusIn;
    // protected ArrayList<Integer> TailleBusOut;

    public Elem() {
        this.In = new ArrayList<ArrayList<EnumBool>>();
        this.Out = new ArrayList<ArrayList<EnumBool>>();
        // this.NbBusIn = NbFluxIn;
        // this.NbBusOut = NbFluxOut;
        // this.TailleBusIn = new ArrayList<Integer>(TailleBusIn);
        // this.TailleBusOut = new ArrayList<Integer>(TailleBusOut);
        this.TailleBus = 1;
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
        return In;
    }

    /**
     * Retrieves the output connections of the element.
     *
     * @return An ArrayList of ArrayLists containing EnumBool values representing
     *         the output connections of the element.
     */
    public ArrayList<ArrayList<EnumBool>> getOut() {
        return Out;
    }

    
    public void setNbBusIn(int NbBusIn) {this.NbBusIn = NbBusIn;}

    public void setNbBusOut(int NbBusOut) {this.NbBusOut = NbBusOut;}


    /**
     * Retrieves the number of input connections for the element.
     *
     * @return An integer representing the number of input connections.
     */
    public int getInputNb() {
        return NbBusIn;
    }

    /**
     * Retrieves the number of output connections for the element.
     *
     * @return An integer representing the number of output connections.
     */
    public int getOutputNb() {
        return NbBusOut;
    }

    /**
     * Clears all input connections of the element.
     *
     * This method removes all input connections from the element's input list.
     * After calling this method, the getIn() method will return an empty list.
     *
     */
    public void clearIn() {
        In.clear();
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
     * @throws IndexOutOfBoundsException If the specified index is out of range
     *                                   (index < 0 || index >= getInputNb()).
     */
    public void removeIn(int i) {
        if (i < 0 || i >= getInputNb())
            throw new IndexOutOfBoundsException("Index out of range: " + i);
        In.remove(i);
    }

    /**
     * Clears all output connections of the element.
     *
     * This method removes all output connections from the element's output list.
     * After calling this method, the getOut() method will return an empty list.
     *
     */
    public void clearOut() {
        Out.clear();
    }


    // public int getNbBusIn(){return NbBusIn;}

    // public int getNbBusOut(){return NbBusOut;}

    // public ArrayList<Integer> getTailleBusIn(){return TailleBusIn;}

    // public ArrayList<Integer> getTailleBusOut(){return TailleBusOut;}

    // the evalute method that is inherited by the other classes 
    public abstract ArrayList<ArrayList<EnumBool>> evaluate();

}
