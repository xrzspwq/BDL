package src;

import java.util.ArrayList;

public class Output extends ElemLogique {
    public Output() {
        super();
        NbBusOut = 0;
    }

    @Override
    public void setNbBusOut(int nbBusOut) {
        // throw new UnsupportedOperationException("Cette méthode est désactivée dans
        // l'enfant.");
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();

        if (In.size() != 1) {
            output.add(EnumBool.ERR);
            Out.add(output);
        } else {
            Out = In;
        }
        return Out;
    }
}
