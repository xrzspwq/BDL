
package src;

import java.util.ArrayList;
import java.util.Iterator;

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
                if (entry.get(i) == EnumBool.FALSE || In.get(j).get(i) == EnumBool.FALSE) {
                    result.add(EnumBool.FALSE);
                } else if (entry.get(i) == EnumBool.TRUE && In.get(j).get(i) == EnumBool.TRUE) {
                    result.add(EnumBool.TRUE);
                } else {
                    result.add(EnumBool.ERR);
                }
            }
            entry.clear();
            entry.addAll(result);
        }
        Out.add(entry);
        
        return Out;

    }

}
