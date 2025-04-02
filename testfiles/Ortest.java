package testfiles;

import java.util.ArrayList;

import src.EnumBool;
import src.Input;
import src.Or;
import src.Wire;

public class Ortest {

    public Ortest() {
        Or ortest = new Or();
        ArrayList<ArrayList<EnumBool>> out = new ArrayList<ArrayList<EnumBool>>();

        Object[][] table = new String[7][];
        table[0] = new String[] { "Input", "Output" };

        Input intest1 = new Input();
        Input intest2 = new Input();

        Wire lien1 = new Wire(intest1, 1, null);
        Wire lien2 = new Wire(intest2, 1, null);
        Wire lien3 = new Wire(intest2, 1, null);

        ///////////////////////////////////// 
        /* RESET INPUT TO VALUE TRUE TRUE */
        intest1.toggle();
        ///////////////////////////////////

        /* OR TEST */

        ///////////////
        /* True True */

        intest1.toggle();
        intest2.toggle();

        lien1.connect(ortest, 1);
        lien2.connect(ortest, 2);
        out = ortest.evaluate();

        table[1] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        /////////////////
        /* False False */

        intest1.toggle();
        intest2.toggle();

        lien1.connect(ortest, 1);
        lien2.connect(ortest, 2);

        out = ortest.evaluate();

        table[2] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        ////////////////
        /* False True */

        intest2.toggle();

        lien1.connect(ortest, 1);
        lien2.connect(ortest, 2);

        out = ortest.evaluate();

        table[3] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        ////////////////
        /* True False */

        intest1.toggle();
        intest2.toggle();

        lien1.connect(ortest, 1);
        lien2.connect(ortest, 2);

        out = ortest.evaluate();

        table[4] = new String[] { intest1.getOut() + " " + intest2.getOut(), "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        ///////////////////
        /* Excesive input */

        lien1.connect(ortest, 1);
        lien2.connect(ortest, 2);
        lien3.connect(ortest, 2);

        out = ortest.evaluate();

        table[5] = new String[] { "1*Input(1) 2*Input(2)", "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        lien1.connect(intest1, 1);
        lien2.connect(intest2, 1);

        out = ortest.evaluate();

        table[6] = new String[] { "2*Input(1) 0*Input(2)", "" + out };

        ortest.getIn().removeAll(ortest.getIn());
        ortest.getOut().removeAll(ortest.getOut());

        System.out.println("\t\tOR TEST");
        for (Object[] row : table) {
            System.out.format("%15s%15s%n", row);
        }
        System.out.println("----------------------------------");

    }
}
