
package src;

import java.util.ArrayList;

public class And extends ElemLogique {

    /**
     * Constructs a new AND gate with 2 input buses and 1 output bus.
     */
    public And() {
        super();
        NbBusIn = 2;
        NbBusOut = 1;
        name = "And Gate";
    }

    /*
     * Evaluates the AND operation on the input signals.
     * This method performs a bitwise AND operation on the input signals
     * and generates the corresponding output.
     *
     * @return An ArrayList of ArrayList of EnumBool representing the output
     * signals.
     * The outer ArrayList representing the output bus.
     * The inner ArrayList contains the evaluated values for each bit of the output
     * bus
     * Returns ERR if there are fewer than 2 input signals.
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        Out.clear();
        if (In.size() < 2) {
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
            if (In.get(0).get(i) == EnumBool.FALSE || In.get(1).get(i) == EnumBool.FALSE) {
                output.add(EnumBool.FALSE);
            } else if (In.get(0).get(i) == EnumBool.TRUE && In.get(1).get(i) == EnumBool.TRUE) {
                output.add(EnumBool.TRUE);
            } else {
                output.add(EnumBool.ERR);
            }
        }
        Out.add(output);
        
        return Out;

    }

}
