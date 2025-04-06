package testfiles;

import java.util.ArrayList;
import java.util.Arrays;

import src.And;
import src.Circuit;
import src.Elem;
import src.EnumBool;
import src.Constant;
import src.Not;
import src.Output;
import src.Xor;
import src.Wire;

public class Circuittest {

    public Circuittest(){
        ///////////////
        /* ELem use */
        Not nottest = new Not(1);
        And andtest = new And();
        Xor xortest = new Xor();
        Output out = new Output();
        Constant intest1 = new Constant();
        Constant intest2 = new Constant();
        Constant intest3 = new Constant();
        Constant intest4 = new Constant();
        ////////////////////////////////
    
        intest2.toggle();//set to true
        intest3.toggle();//set to true

        ArrayList<Wire> W  = new ArrayList<>();
        ArrayList<Elem> E  = new ArrayList<>();

        EnumBool res;
        
        //GOOD CIRCUIT//
        W.addAll(Arrays.asList(new Wire(intest1, 1, null),new Wire(intest2, 1, null),
        new Wire(intest3, 1, null),new Wire(andtest, 1, null),
        new Wire(nottest, 1, null),new Wire(xortest, 1, null)));

        W.get(0).connectExit(andtest, 1);
        W.get(1).connectExit(andtest, 2);
        W.get(2).connectExit(xortest, 1);
        W.get(3).connectExit(nottest, 1);
        W.get(4).connectExit(xortest, 2);
        W.get(5).connectExit(out, 1);

        E.addAll(Arrays.asList(nottest,andtest,xortest,intest1,intest2,intest3));

        Circuit circ = new Circuit("Circuit test");
        System.out.println("\t\tTEST CIRCUIT");
        System.out.println("Name of circuit: "+circ.getName());


        res  = circ.simuler(W);
        System.out.println("Good Manner\nGood Circuit ?  : "+res);
        System.out.println();
        //////////////////////////////////////////
        /* BAD CIRCUIT */
        W.add(new Wire(intest4,1,null));
        W.getLast().swapEntry(andtest, 2);
        res = circ.simuler(W) ;
        System.out.println("Bad Manner\nGood Circuit ?  : "+res);

        
        System.out.println("----------------------------------");



    }
    
}
