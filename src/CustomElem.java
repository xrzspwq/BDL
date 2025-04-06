package src;

import java.util.ArrayList;




public class CustomElem extends ElemLogique{

    ArrayList<ArrayList<EnumBool>> truthTable;

    public CustomElem(String name, int NbBusIn, int NbBusOut, ArrayList<ArrayList<EnumBool>> truthTable) {
        super();
        this.nbBusIn = NbBusIn;
        this.nbBusOut = NbBusOut;
        this.name = name;
        this.truthTable = truthTable;
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        
        for(int i = 0; i < truthTable.size() ; i++)
        {
            ArrayList<EnumBool> tableVars = truthTable.get(i);
            tableVars.remove(truthTable.size()-1);

            if(in.equals(tableVars))
            {
                out.add(truthTable.get(i));
                return out;
            }
        }

        ArrayList<EnumBool> nothing = new ArrayList<>();
        nothing.add(EnumBool.NOTHING);
        out.add(nothing);
        
        return out;
    }
}
