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
     * It takes no parameters as the inputs are assumed to be stored in the 'In'
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
        Out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if (In.size() < 2) {
            output.add(EnumBool.NOTHING);
            Out.add(output);
            return Out;
        }
        int i;
        
        
        Iterator<ArrayList<EnumBool>> ite = In.iterator();
        ArrayList<EnumBool> entry = null;
        ArrayList<EnumBool> tmp;
        while(ite.hasNext()){
            tmp = ite.next();
            if(!tmp.equals(new ArrayList<EnumBool>())){
                entry=tmp;
                break;
            }
        }

        if(entry == null){
            output.add(EnumBool.NOTHING);
            Out.add(output);
            return Out;
        }
        
        ArrayList<EnumBool> result = new ArrayList<EnumBool>();
        

        for(int j=1; j<In.size();j++){
            for (i = 0; i < entry.size(); i++) {
                result.clear();
                if (entry.get(i) == EnumBool.ERR || In.get(j).get(i) == EnumBool.ERR
                        || entry.get(i) == EnumBool.NOTHING || In.get(j).get(i) == EnumBool.NOTHING) {
                    result.add(EnumBool.ERR);
                }
                if (entry.get(i) == EnumBool.FALSE && In.get(j).get(i) == EnumBool.FALSE) {
                    result.add(EnumBool.FALSE);
                } else if (entry.get(i) == EnumBool.TRUE || In.get(j).get(i) == EnumBool.TRUE) {
                    result.add(EnumBool.TRUE);
                } else {
                    result.add(EnumBool.ERR);
                }
            }
            
            entry.clear();
            entry.addAll(result);
        }
        Out.add(entry);
        output.clear();
        return Out;

    }

}
