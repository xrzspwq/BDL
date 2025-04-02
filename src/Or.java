package src;

import java.util.ArrayList;

public class Or extends ElemLogique{
    
    

    public Or() {
        super();
        name = "Or Gate";
    }

    /**
     * This function evaluates the logical OR operation on the inputs provided.
     * It takes no parameters as the inputs are assumed to be stored in the 'In' ArrayList.
     *
     * @return An ArrayList of ArrayLists of EnumBool. The outer ArrayList representing the output bus.
     *         The inner ArrayList contains the evaluated values for each bit of the output bus.
     *         - If the inputs are valid, the result will be either EnumBool.TRUE, EnumBool.FALSE, or EnumBool.ERR.
     *         - If the inputs are invalid (more than 2 inputs or any input is EnumBool.ERR or EnumBool.NOTHING),
     *           the result will be a single EnumBool.ERR.
     */
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

        for(i = 0 ; i<In.get(0).size(); i++){
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
        output.clear();
        return Out;
    }


    
    
}
