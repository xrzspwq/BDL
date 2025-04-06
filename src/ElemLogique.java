package src;

import java.util.ArrayList;

public abstract class ElemLogique extends Elem {
    // inherit the constructor from the class Elem
    public ElemLogique() {
        super();
        nbBusIn = 2;
        nbBusOut = 1;
    }

    /**
     * This function evaluates the logic element and returns the output values.
     *
     * @return @return An ArrayList of ArrayLists of EnumBool. The outer ArrayList
     *         representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the
     *         output bus.
     *
     */
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        return out;
    }
}