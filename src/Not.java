package src;

import java.util.ArrayList;

public class Not extends ElemLogique{
    
    private Not(int NbBusIn, int NbBusOut, ArrayList<Integer> TailleBusIn, ArrayList<Integer> TailleBusOut) {
        super(NbBusIn, NbBusOut, TailleBusIn, TailleBusOut); 
    }

    public Not(int NbBusIn, int TailleIn) {
        super(NbBusIn, 1, new ArrayList<Integer>(), new ArrayList<Integer>());
        for (int i = 0; i < NbBusIn; i++) {
            TailleBusIn.add(TailleIn);
        } 
        TailleBusOut.add(TailleIn);
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        if(In.size()>1){
            output.add(EnumBool.ERR);
            Out.add(output);
            return Out;
        }
        int i;
        
        for(i = 0 ; i<NbBusIn; i++){
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
