
package src;

import java.util.ArrayList;

public class Nand extends ElemLogique {

    
    public Nand() {
        super();
        nbBusIn = 2;
        nbBusOut = 1;
        name = "Nand Gate";
    }

      
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        Or orgate = new Or();
        Not notgate = new Not();
        orgate.in=this.in;
        ArrayList<ArrayList<EnumBool>> res = orgate.evaluate();
        notgate.in.set(0,res.get(0));
        res=notgate.evaluate();
        out.add(new ArrayList<>(res.get(0)));        
        return out;

    }

}
