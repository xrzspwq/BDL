package testfiles;

import java.util.ArrayList;

import src.And;
import src.EnumBool;
import src.Constant;
import src.Wire;

public class Andtest {

    public Andtest() {
        ArrayList<ArrayList<EnumBool>> out = new ArrayList<ArrayList<EnumBool>>();

        Object[][] table = new String[7][];
        table[0] = new String[] { "Input", "Output" };

        And andtest = new And();

        Constant intest1 = new Constant();
        Constant intest2 = new Constant();

        int[] start = {0,0};

        Wire lien1 = new Wire(intest1, 1,start);
        Wire lien2 = new Wire(intest2, 1, start);
        Wire lien3 = new Wire(intest2, 1, start);

        /* AND TEST */

        ////////////////
        /* TRUE TRUE */
        intest1.toggle();
        intest2.toggle();

        lien1.connectExit(andtest, 0);
        lien2.connectExit(andtest, 1);
        out = andtest.evaluate();

        table[1] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        ////////////////
        /* False False */

        intest1.toggle();
        intest2.toggle();

        lien1.connectExit(andtest, 0);
        lien2.connectExit(andtest, 1);

        out = andtest.evaluate();

        table[2] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        ////////////////
        /* False True */

        intest2.toggle();

        lien1.connectExit(andtest, 1);
        lien2.connectExit(andtest, 2);

        out = andtest.evaluate();

        table[3] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        ////////////////
        /* True False */

        intest1.toggle();
        intest2.toggle();
        System.out.println("1-"+intest1.getOut()+" 2-"+intest2.getOut());
        lien1.connectExit(andtest, 1);
        lien2.connectExit(andtest, 2);
        System.out.println("1-"+intest1.getOut()+" 2-"+intest2.getOut());
        out = andtest.evaluate();
       
        table[4] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };
        System.out.println("end");
        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        ///////////////////
        /* Excesive input */

        lien1.connectExit(andtest, 1);
        lien2.connectExit(andtest, 2);
        lien3.connectExit(andtest, 2);

        out = andtest.evaluate();

        table[5] = new String[] { "1*Input(1) 2*Input(2)", "" + out };

        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        lien1.connectExit(andtest, 1);
        lien2.connectExit(andtest, 1);

        out = andtest.evaluate();

        table[6] = new String[] { "2*Input(1) 0*Input(2)", "" + out };

        andtest.getIn().removeAll(andtest.getIn());
        andtest.getOut().removeAll(andtest.getOut());

        System.out.println("----------------------------------");
        System.out.println("\t\tAND TEST");
        for (Object[] row : table) {
            System.out.format("%15s%15s%n", row);
        }
        System.out.println("----------------------------------");

    }
}
