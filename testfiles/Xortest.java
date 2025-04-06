package testfiles;

import java.util.ArrayList;

import src.EnumBool;
import src.Constant;
import src.Wire;
import src.Xor;

public class Xortest {

    public Xortest() {
        ArrayList<ArrayList<EnumBool>> out = new ArrayList<ArrayList<EnumBool>>();

        Object[][] table = new String[7][];
        table[0] = new String[] { "Input", "Output" };

        Constant intest1 = new Constant();
        Constant intest2 = new Constant();

        Wire lien1 = new Wire(intest1, 1, null);
        Wire lien2 = new Wire(intest2, 1, null);
        Wire lien3 = new Wire(intest2, 1, null);

        Xor xortest = new Xor();

        /* XOR TEST */

        ///////////////
        /* True True */

        intest1.toggle();
        intest2.toggle();

        lien1.connectExit(xortest, 1);
        lien2.connectExit(xortest, 2);

        out = xortest.evaluate();

        table[1] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        /////////////////
        /* False False */

        intest1.toggle();
        intest2.toggle();

        lien1.connectExit(xortest, 1);
        lien2.connectExit(xortest, 2);

        out = xortest.evaluate();

        table[2] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        ////////////////
        /* False True */

        intest2.toggle();

        lien1.connectExit(xortest, 1);
        lien2.connectExit(xortest, 2);

        out = xortest.evaluate();

        table[3] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        ////////////////
        /* True False */

        intest1.toggle();
        intest2.toggle();

        lien1.connectExit(xortest, 1);
        lien2.connectExit(xortest, 2);

        out = xortest.evaluate();

        table[4] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        ///////////////////
        /* Excesive input */

        lien1.connectExit(xortest, 1);
        lien2.connectExit(xortest, 2);
        lien3.connectExit(xortest, 2);

        out = xortest.evaluate();

        table[5] = new String[] { "1*Input(1) 2*Input(2)", "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        lien1.connectExit(intest1, 1);
        lien2.connectExit(intest2, 1);

        out = xortest.evaluate();

        table[6] = new String[] { "2*Input(1) 0*Input(2)", "" + out };

        xortest.getIn().removeAll(xortest.getIn());
        xortest.getOut().removeAll(xortest.getOut());

        System.out.println("\t\tXOR TEST");
        for (Object[] row : table) {
            System.out.format("%15s%15s%n", row);
        }
        System.out.println("----------------------------------");

    }
}
