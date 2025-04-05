
package src;

import java.util.ArrayList;

public class Nor extends ElemLogique {

    
    public Nor() {
        super();
        NbBusIn = 2;
        NbBusOut = 1;
        name = "Nor Gate";
    }

      
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();
        And andgate = new And();
        Not notgate = new Not();
        andgate.In=this.In;
        ArrayList<ArrayList<EnumBool>> res = andgate.evaluate();
        notgate.In.set(0,res.get(0));
        res=notgate.evaluate();
        Out.add(new ArrayList<>(res.get(0)));        
        return Out;

    }

}
