package src;

import java.util.ArrayList;

import src.EnumBool;



public class CustomElem extends ElemLogique{

    ArrayList<ArrayList<EnumBool>> truthTable;

    public CustomElem(String name, int NbBusIn, int NbBusOut, ArrayList<ArrayList<EnumBool>> truthTable) {
        super();
        this.NbBusIn = NbBusIn;
        this.NbBusOut = NbBusOut;
        this.name = name;
        this.truthTable = truthTable;
    }

    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Out.clear();
        
        for(int i = 0; i < truthTable.size() ; i++)
        {
            ArrayList<ArrayList<EnumBool>> tableVars = truthTable.get(i);
            tableVars.remove(truthTable.size()-1);

            if(In.equals(tableVars))
            {
                for(int j = 0; j < tableVars.size(); j++)
                    Out.add(truthTable.get(i).get(j));
                
                return Out;
            }
        }

        ArrayList<EnumBool> nothing = new ArrayList<>();
        nothing.add(EnumBool.NOTHING);

        for(int j= 0 ; j < NbBusOut; j++) 
            Out.add(nothing.get(0));
        
        return Out;
    }
}
