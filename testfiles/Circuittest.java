package testfiles;

import java.util.ArrayList;
import java.util.Arrays;

import src.And;
import src.Circuit;
import src.Elem;
import src.EnumBool;
import src.Input;
import src.Not;
import src.Xor;
import src.Wire;

public class Circuittest {

    public Circuittest(){
        Not nottest = new Not(1);
        And andtest = new And();
        Xor xortest = new Xor();

        Input intest1 = new Input();
        Input intest2 = new Input();
        Input intest3 = new Input();
        //intest1.toggle();
        intest2.toggle();
        intest3.toggle();
        ArrayList<Wire> W  = new ArrayList<>();
        ArrayList<Elem> E  = new ArrayList<>();

        EnumBool res;
        
        W.addAll(Arrays.asList(new Wire(intest1, 1, null),new Wire(intest2, 1, null),
        new Wire(intest3, 1, null),new Wire(andtest, 1, null),
        new Wire(nottest, 1, null)));

        W.get(0).connectExit(andtest, 1);
        W.get(1).connectExit(andtest, 2);
        W.get(2).connectExit(xortest, 1);
        W.get(3).connectExit(nottest, 1);
        W.get(4).connectExit(xortest, 2);

        E.addAll(Arrays.asList(nottest,andtest,xortest,intest1,intest2,intest3));

        Circuit circ = new Circuit("Circuit test");

        circ.addElement(E.get(0));
        circ.addElement(E.get(1));
        circ.addElement(E.get(2));
        circ.addElement(E.get(3));
        circ.addElement(E.get(4));
        circ.addElement(E.get(5));

        circ.addWire(W.get(0));
        circ.addWire(W.get(1));
        circ.addWire(W.get(2));
        circ.addWire(W.get(3));
        circ.addWire(W.get(4));
        
        res  = circ.simuler(W);

        System.out.println("final"+res);



    }
    
}
