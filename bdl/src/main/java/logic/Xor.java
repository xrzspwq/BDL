package logic;

import java.util.ArrayList;

public class Xor extends ElemLogique {

    /*
     * inherit the constructor from the class ElemLogique
     * with a name of "Xor Gate"
     */
    public Xor() {
        super();
        name = "Xor Gate";
    }

    /**
     * This function evaluates the XOR gate logic for the given input signals.
     * It takes no parameters as the input signals are assumed to be provided
     * through the 'In' ArrayList.
     *
     * @return An ArrayList of ArrayLists of EnumBool. The outer ArrayList
     *         representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the
     *         output bus.
     *         The function returns ERR if the number of input signals is greater
     *         than 2,
     *         or if any input signal is in an invalid state (ERR or NOTHING).
     *         Otherwise, it returns the XOR gate output for each time step.
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if (In.size() > 2) {
            output.add(EnumBool.ERR);
            Out.add(output);
            return Out;
        }
        int i;

        for (i = 0; i < In.get(0).size(); i++) {
            if (In.get(0).get(i) == EnumBool.ERR || In.get(1).get(i) == EnumBool.ERR
                    || In.get(0).get(i) == EnumBool.NOTHING || In.get(1).get(i) == EnumBool.NOTHING) {
                output.add(EnumBool.ERR);
            }

            if (In.get(0).get(i) == EnumBool.FALSE && In.get(1).get(i) == EnumBool.FALSE) {
                output.add(EnumBool.FALSE);
            } else if (In.get(0).get(i) == EnumBool.TRUE && In.get(1).get(i) == EnumBool.TRUE) {
                output.add(EnumBool.FALSE);
            } else if (In.get(0).get(i) == EnumBool.TRUE || In.get(1).get(i) == EnumBool.TRUE) {
                output.add(EnumBool.TRUE);
            } else {
                output.add(EnumBool.ERR);
            }
        }
        Out.add(output);
        return Out;
    }

}
