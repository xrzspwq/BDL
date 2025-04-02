package testfiles;

import java.util.ArrayList;

import src.And;
import src.EnumBool;
import src.Input;
import src.Not;
import src.Wire;
import src.Xor;

public class Littlecircuit {
    public Littlecircuit() {
        Not nottest = new Not(1, 1);
        And andtest = new And();
        Xor xortest = new Xor();

        ArrayList<ArrayList<EnumBool>> out = new ArrayList<ArrayList<EnumBool>>();

        Object[][] table = new String[6][];
        table[0] = new String[] { "Input", "Output" };

        Input intest1 = new Input();
        Input intest2 = new Input();

        Wire lien1 = new Wire(intest1, 1, null);
        Wire lien2 = new Wire(intest2, 1, null);

        /* little circuit */

        System.out.println("\t\u001B[32LITTLE CIRCUIT\u001B[0m\n");
        intest2.toggle();
        

        lien1.connect(andtest, 1);
        lien2.connect(andtest, 2);
        out = andtest.evaluate();

        System.out.println("input\t\tinput\n" + intest1.getOut() + "\t" + intest2.getOut());
        System.out.println("   |\t\t   |\n    ---------------\n\t |");
        System.out.println("\tAnd\n\t" + out + "");
        System.out.println("\t |");

        lien1.changeEntry(andtest, 1);
        lien1.connect(nottest, 1);
        out = nottest.evaluate();
        intest1.toggle();
        System.out.println("\tNot\t\tinput\n\t" + out + "\t" + intest1.getOut() + "");
        System.out.println("\t   |\t\t   |\n\t    ---------------\n\t\t |");

        lien1.changeEntry(nottest, 1);
        lien2.changeEntry(intest1, 1);

        lien1.connect(xortest, 1);
        lien2.connect(xortest, 2);
        out = xortest.evaluate();

        System.out.println("\t\tXor\n\t\t" + out);
        System.out.println("----------------------------------");

    }
}
