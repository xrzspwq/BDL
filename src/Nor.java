
package src;

import java.util.ArrayList;

public class Nor extends ElemLogique {

    
    public Nor() {
        super();
        nbBusIn = 2;
        nbBusOut = 1;
        name = "Nor Gate";
    }

      
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        And andgate = new And();
        Not notgate = new Not();
        andgate.in=this.in;
        ArrayList<ArrayList<EnumBool>> res = andgate.evaluate();
        notgate.in.set(0,res.get(0));
        res=notgate.evaluate();
        out.add(new ArrayList<>(res.get(0)));        
        return out;

    }

}
