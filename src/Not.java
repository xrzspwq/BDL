package src;

import java.util.ArrayList;
import java.util.Iterator;

public class Not extends ElemLogique {

    // inherit the constructor from the class ElemLogique
    public Not() {
        super();
        nbBusIn = 1;
    }

    public void setNbBusIn(int nbBusIn){
        throw new IllegalStateException();
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
        out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if (in.size() > 1) {
            output.add(EnumBool.ERR);
            out.add(output);
            return out;
        }
        int i;

        for ( i = in.size() - 1; i >= 0; i--) {
            if (in.get(i).isEmpty()) {
                in.remove(i);
            }
        }
        Iterator<ArrayList<EnumBool>> ite = in.iterator();
        ArrayList<EnumBool> entry = null;
        ArrayList<EnumBool> tmp;
        while(ite.hasNext()){
            tmp = ite.next();
            if(!tmp.isEmpty()){
                entry=tmp;
                break;
            }
        }

        if(entry == null){
            output.add(EnumBool.NOTHING);
            out.add(output);
            return out;
        }
        for (i = 0; i < entry.size(); i++) {
            if (in.get(0).get(i).equals(EnumBool.FALSE)) {
                output.add(EnumBool.TRUE);
            } else if (in.get(0).get(i).equals(EnumBool.TRUE)) {
                output.add(EnumBool.FALSE);
            } else {
                output.clear();
                output.add(EnumBool.ERR);
                out.add(output);
                return out;
            }
        }
        out.add(output);
        return out;

    }

    }

