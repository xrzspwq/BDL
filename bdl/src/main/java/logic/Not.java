package logic;

import java.util.ArrayList;

public class Not extends ElemLogique {

    // inherit the constructor from the class ElemLogique
    public Not(int NbBusIn, int TailleIn) {
        super();

    }

    /**
     * This method evaluates the logical NOT operation on the input bus.
     * 
     * @return An ArrayList of ArrayLists of EnumBool. The outer ArrayList
     *         representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the
     *         output bus
     *         If the input bus size is not equal to 1, an ArrayList with a single
     *         EnumBool.ERR value is returned.
     *         If any bit in the input bus is neither EnumBool.TRUE nor
     *         EnumBool.FALSE, an ArrayList with a single EnumBool.ERR value is
     *         returned.
     * 
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if (In.size() > 1) {
            output.add(EnumBool.ERR);
            Out.add(output);
            return Out;
        }
        int i;

        for (i = 0; i < In.get(0).size(); i++) {
            if (In.get(0).get(i).equals(EnumBool.FALSE)) {
                output.add(EnumBool.TRUE);
            } else if (In.get(0).get(i).equals(EnumBool.TRUE)) {
                output.add(EnumBool.FALSE);
            } else {
                output.clear();
                output.add(EnumBool.ERR);
                Out.add(output);
                return Out;
            }
        }
        Out.add(output);
        return Out;

    }

}
