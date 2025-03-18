package src;

import java.util.ArrayList;

public class Or extends ElemLogique{
    
    

    public Or(int NbBusIn, int TailleIn) {
        super();
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if(In.size()>2){
            output.add(EnumBool.ERR);
            Out.add(output);
            return Out;
        }
        int i;
        
        for(i = 0 ; i<In.size(); i++){
            if(In.get(0).get(i) == EnumBool.ERR || In.get(1).get(i) == EnumBool.ERR ||In.get(0).get(i) == EnumBool.NOTHING || In.get(1).get(i) == EnumBool.NOTHING){
                output.add(EnumBool.ERR);
            }
            if(In.get(0).get(i) == EnumBool.FALSE && In.get(1).get(i) == EnumBool.FALSE){
                output.add(EnumBool.FALSE);
            }
            else if(In.get(0).get(i) == EnumBool.TRUE || In.get(1).get(i) == EnumBool.TRUE){
                output.add(EnumBool.TRUE);
            }
            else{
                output.add(EnumBool.ERR);
            }
        }        
        Out.add(output);
        return Out;

    }
    
    
}
