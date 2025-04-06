package src;

import java.util.ArrayList;
import java.util.Iterator;

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
        out.clear();

        int i=0;

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if (in.size() < 2) {
            output.add(EnumBool.NOTHING);
            out.add(output);
            return out;
        }

        Iterator<ArrayList<EnumBool>> ite = in.iterator();
        ArrayList<EnumBool> tmp;
        ArrayList<ArrayList<EnumBool>> Incopy = new ArrayList<ArrayList<EnumBool>>();
        while(ite.hasNext()){
            tmp=ite.next();
            if(!tmp.isEmpty()){
                Incopy.add(new ArrayList<>(tmp));
            }
        }

        if (Incopy.size() < 2) {
            output.add(EnumBool.NOTHING);
            out.add(output);
            return out;
        }
        ArrayList<EnumBool> result = new ArrayList<EnumBool>();

        for (int j = 1; j < Incopy.size(); j++) {
            for (i = 0; i < Incopy.get(0).size(); i++) {
                result.clear();
                if (Incopy.get(0).get(i) == EnumBool.ERR || Incopy.get(j).get(i) == EnumBool.ERR || Incopy.get(0).get(i) == EnumBool.NOTHING
                        || Incopy.get(j).get(i) == EnumBool.NOTHING) {
                            result.clear();
                            result.add(EnumBool.ERR);
                            Incopy.get(0).clear();
                            Incopy.get(0).addAll(result);
                            out.add(Incopy.get(0));
                            return out;                }

                if (Incopy.get(0).get(i) == EnumBool.FALSE && Incopy.get(j).get(i) == EnumBool.FALSE) {
                    result.add(EnumBool.FALSE);
                } else if (Incopy.get(0).get(i) == EnumBool.TRUE && Incopy.get(j).get(i) == EnumBool.TRUE) {
                    result.add(EnumBool.FALSE);
                } else if (Incopy.get(0).get(i) == EnumBool.TRUE || Incopy.get(j).get(i) == EnumBool.TRUE) {
                    result.add(EnumBool.TRUE);
                } else {
                    result.add(EnumBool.ERR);
                }
            }

            Incopy.get(0).clear();
            Incopy.get(0).addAll(result);
        }
        out.add(Incopy.get(0));
        return out;
    }

}
