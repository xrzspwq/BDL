
package src;

import java.util.ArrayList;

public class Nand extends ElemLogique {

    
    public Nand() {
        super();
        NbBusIn = 2;
        NbBusOut = 1;
        name = "Nand Gate";
    }

      
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();
        Or orgate = new Or();
        Not notgate = new Not();
        orgate.In=this.In;
        ArrayList<ArrayList<EnumBool>> res = orgate.evaluate();
        notgate.In.set(0,res.get(0));
        res=notgate.evaluate();
        Out.add(new ArrayList<>(res.get(0)));        
        return Out;

    }

}
