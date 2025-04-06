package src;

import java.util.ArrayList;
import java.util.Iterator;

public class Or extends ElemLogique {

    /*
     * inherit the constructor from the class ElemLogique
     * with a name of "Or Gate"
     */
    public Or() {
        super();
        name = "Or Gate";
    }

    /**
     * This function evaluates the logical OR operation on the inputs provided.
     * It takes no parameters as the inputs are assumed to be stored in the 'in'
     * ArrayList.
     *
     * @return An ArrayList of ArrayLists of EnumBool. The outer ArrayList
     *         representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the
     *         output bus.
     *         - If the inputs are valid, the result will be either EnumBool.TRUE,
     *         EnumBool.FALSE, or EnumBool.ERR.
     *         - If the inputs are invalid (more than 2 inputs or any input is
     *         EnumBool.ERR or EnumBool.NOTHING),
     *         the result will be a single EnumBool.ERR.
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        
        int i=0;
        
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
        

        for(int j=1; j<Incopy.size();j++){
            for (i = 0; i < Incopy.get(0).size(); i++) {
                result.clear();
                if (Incopy.get(0).get(i) == EnumBool.ERR || Incopy.get(j).get(i) == EnumBool.ERR
                        || Incopy.get(0).get(i)== EnumBool.NOTHING || Incopy.get(j).get(i) == EnumBool.NOTHING) {
                            result.clear();
                            result.add(EnumBool.ERR);
                            Incopy.get(0).clear();
                            Incopy.get(0).addAll(result);
                            out.add(Incopy.get(0));
                            return out;                }
                if (Incopy.get(0).get(i) == EnumBool.FALSE && Incopy.get(j).get(i) == EnumBool.FALSE) {
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
