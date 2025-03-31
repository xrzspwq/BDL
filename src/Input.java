package src;

import java.util.ArrayList;

public class Input extends ElemLogique{
    private EnumBool output ;

   
    
    public Input() {
        super();
        output = EnumBool.FALSE;
    }

    public void toggle(){
        output = (output == EnumBool.FALSE) ? EnumBool.TRUE : EnumBool.FALSE;
        this.evaluate();
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();

        ArrayList<EnumBool> bus = new ArrayList<>();
        bus.add(output);

        Out.add(bus);
        return Out;
    }

    
    
}
