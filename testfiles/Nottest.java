package testfiles;

import java.util.ArrayList;

import src.EnumBool;
import src.Constant;
import src.Not;
import src.Wire;

public class Nottest {
    
    public Nottest(){
        Not nottest = new Not(1);
        ArrayList<ArrayList<EnumBool>> out = new ArrayList<ArrayList<EnumBool>>();
        
        Object[][] table = new String[5][]; 
        table[0] = new String[] {"Input","Output"};    

        Constant intest1 = new Constant();
        Constant intest2 = new Constant();

    
        Wire lien1 = new Wire(intest1, 1, null);
        Wire lien2 = new Wire(intest2, 1, null);


    /*  NOT TEST  */

        ///////////////
        /*True*/

        intest1.toggle();

        lien1.connectExit(nottest, 1);
        
        out = nottest.evaluate();
        
        table[1] = new String[] {intest1.getOut()+"", ""+out};
        
        nottest.getIn().removeAll(nottest.getIn());
        nottest.getOut().removeAll(nottest.getOut());

        /////////////////
        /*False*/

        intest1.toggle();

        lien1.connectExit(nottest, 1);
        
        out = nottest.evaluate();
        
        table[2] = new String[] {intest1.getOut()+"", ""+out};

        nottest.getIn().removeAll(nottest.getIn());
        nottest.getOut().removeAll(nottest.getOut());

        ///////////////////
        /*Excesive input*/

        lien1.connectExit(nottest, 1);
        lien2.connectExit(nottest, 2);   

        out = nottest.evaluate();
        
        table[3] = new String[] {"1*Input(1) 1*Input(2)", ""+out};
    
        nottest.getIn().removeAll(nottest.getIn());
        nottest.getOut().removeAll(nottest.getOut());

        lien1.connectExit(intest1, 1);
        lien2.connectExit(intest2, 1);

        out = nottest.evaluate();

        table[4] = new String[] { "2*Input(1)", "" + out };

        nottest.getIn().removeAll(nottest.getIn());
        nottest.getOut().removeAll(nottest.getOut());

        System.out.println("\t\tNOT TEST");
        for(Object[] row : table){
            System.out.format("%15s%15s%n", row);
        }
        System.out.println("----------------------------------");


    }
}
