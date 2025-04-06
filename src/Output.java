package src;

import java.util.ArrayList;

public class Output extends ElemLogique {
    public Output() {
        super();
        nbBusOut = 0;
    }

    @Override
    public void setNbBusOut(int nbBusOut) {
        // throw new UnsupportedOperationException("Cette méthode est désactivée dans
        // l'enfant.");
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();

        if (in.size() != 1) {
            output.add(EnumBool.ERR);
            out.add(output);
        } else {
            out = in;
        }
        return out;
    }
}
