package logic;

import java.util.ArrayList;

public class Input extends ElemLogique {
    private EnumBool output;

    /*
     * constructor with the base class of ElemLogique
     * and set the output as FALSE
     */
    public Input() {
        super();
        output = EnumBool.FALSE;
    }

/**
     * This function toggles the boolean state of the input.
     *
     * The input's boolean state is represented by the EnumBool, which
     * can be either
     * FALSE or TRUE. This function changes the
     * current state to the
     * opposite of its current state. For example, if the current state is
     * FALSE, it will
     * be changed to TRUE, and vice versa.
     *
     * After toggling the state, this function calls the evaluate() method
     * to update the
     * output of the input.
     *
     */
    public void toggle() {
        output = (output == EnumBool.FALSE) ? EnumBool.TRUE : EnumBool.FALSE;
        this.evaluate();
    }

    /**
     * This function evaluates the boolean state of the input and updates the
     * output.
     *
     * The evaluate() method is responsible for determining the current state of the
     * input
     * and updating the output accordingly. In this case, the input's boolean state
     * is
     * represented by the EnumBool.
     *
     * The evaluate() method clears the Out ArrayList, creates a new ArrayList of
     * EnumBool
     * with the current output value, adds it to the Out ArrayList, and then returns
     * the Out ArrayList.
     *
     * @return An ArrayList of ArrayLists of EnumBool. TThe outer ArrayList representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the output bus.
     *
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> bus = new ArrayList<>();
        bus.add(output);

        Out.add(bus);
        return Out;
    }

}
