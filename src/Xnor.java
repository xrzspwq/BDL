
package src;

import java.util.ArrayList;
import java.util.Iterator;

public class Xnor extends ElemLogique {

    
    public Xnor() {
        super();
        NbBusIn = 2;
        NbBusOut = 1;
        name = "Xnor Gate";
    }

      
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        Out.clear();
        
        int i=0;
    
        Iterator<ArrayList<EnumBool>> ite = In.iterator();
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
            Out.add(output);
            return Out;
        }

        ArrayList<EnumBool> result = new ArrayList<EnumBool>();


        for(int j=1; j<Incopy.size();j++){
            for ( i = 0 ; i < Incopy.get(0).size(); i++) {
                result.clear();
                if (Incopy.get(0).get(i) == EnumBool.ERR || Incopy.get(j).get(i) == EnumBool.ERR
                        || Incopy.get(0).get(i) == EnumBool.NOTHING || Incopy.get(j).get(i) == EnumBool.NOTHING) {
                    result.clear();
                    result.add(EnumBool.ERR);
                    Incopy.get(0).clear();
                    Incopy.get(0).addAll(result);
                    Out.add(Incopy.get(0));
                    return Out;
                }
                if (Incopy.get(0).get(i).equals(Incopy.get(j).get(i))) {
                    result.add(EnumBool.FALSE);
                } else if (Incopy.get(0).get(i) == EnumBool.FALSE && Incopy.get(j).get(i) == EnumBool.TRUE) {
                    result.add(EnumBool.TRUE);
                }else if (Incopy.get(0).get(i) == EnumBool.TRUE && Incopy.get(j).get(i) == EnumBool.FALSE) {
                    result.add(EnumBool.TRUE);
                } else {
                    result.add(EnumBool.ERR);
                }
            }
            Incopy.get(0).clear();
            Incopy.get(0).addAll(result);
        }

       
        Out.add(Incopy.get(0));
        return Out;


    }

}
