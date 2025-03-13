package src;

import java.util.ArrayList;

public class Input extends ElemLogique{
    private EnumBool output ;

    //Passer en private car il n'y a pas d'entry dans cette Classe
    private Input(int NbBusIn, int NbBusOut, ArrayList<Integer> TailleBusIn, ArrayList<Integer> TailleBusOut) {
        super(NbBusIn, NbBusIn, TailleBusIn, TailleBusIn); 
    }
    
    public Input(int NbBusOut,ArrayList<Integer> TailleBusOut) {
        super(0, NbBusOut, new ArrayList<>(), TailleBusOut);
        output = EnumBool.FALSE;
    }

    public void toggle(){
        output = (output == EnumBool.FALSE) ? EnumBool.TRUE : EnumBool.FALSE;
        this.evaluate();
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();
        NbBusOut=0;

        ArrayList<EnumBool> bus = new ArrayList<>();
        bus.add(output);

        Out.add(bus);
        NbBusOut+=1;
        return Out;
    }

    
    
}
