package src;

import java.util.ArrayList;

public class Not extends ElemLogique{
    


    public Not(int NbBusIn, int TailleIn) {
        super();
        
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if(In.size()>1){
            output.add(EnumBool.ERR);
            Out.add(output);
            return Out;
        }
        int i;
        
        for(i = 0 ; i<In.size(); i++){
            if(In.get(0).get(i).equals(EnumBool.FALSE)){
                output.add(EnumBool.TRUE);
            }
            else if(In.get(0).get(i).equals(EnumBool.TRUE)){
                output.add(EnumBool.FALSE);
            }
            else{
                output.clear();
                output.add(EnumBool.ERR);
                Out.add(output);
                return Out;
            }
        }        
        Out.add(output);
        return Out;

    }
    
    
}
