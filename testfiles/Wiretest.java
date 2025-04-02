package testfiles;

import src.Input;
import src.Not;
import src.Pair;
import src.Wire;
import src.Elem;

public class Wiretest {
    
    public Wiretest(){
        Input intest1 = new Input();
        Input intest2 = new Input();
        Not nottest = new Not(1, 1);
        Wire lien1 = new Wire(intest1, 1, null);
        
        Object[][] table = new String[15][]; 
        Integer i = 0;
        
        
        table[0] = new String[] {"OUT","EXPECTED"};

        table[1] = new String[] {"Test","getEntry\n"};
        table[2] = new String[] {lien1.getEntry()+""," <"+intest1+",1>"};
        
        table[3] = new String[] {"Test","connect\n"};
        table[4] = new String[] {lien1.connect(nottest, 1)+"","True"};
        
        table[5] = new String[] {"Test","getExit\n"};
        table[6] = new String[] {lien1.getExit()+""," [[<"+nottest+",1>]]"};
        
        lien1.changeEntry(intest2, 1);
        table[7] = new String[] {"Test","change entry\n"};
        table[8] = new String[] {lien1.getEntry()+""," <"+intest2+",1>\n\n\told entry:<"+intest1+",1>\n\n\t  Exit:"+lien1.getExit()};
        
        table[9] = new String[] {"Test getState","default state\n"};
        table[10] = new String[] {lien1.getState()+"","true"};
        
        Pair<Elem,Integer> tmp = new Pair<>(lien1.getExit().get(0).getElem1(),lien1.getExit().get(0).getElem2());
        table[11] = new String[] {"Test","disconnect\n"};
        table[12] = new String[] {lien1.disconnect(lien1.getExit().get(0))+"","\t\ttrue\n\n\tExit contains: "+tmp+"?\n\n\t   "+lien1.getExit().contains(tmp)+"\tfalse"};
    

        System.out.println("\t\tWIRE TEST");
       
        for(Object[] row : table){
            if (i == 13) {
                break;
            }
            System.out.format("%15s%15s%n", row);
            
            
            if (i%2 == 0) {
                System.out.println("\n::::::::::::::::::::::::::::::::::\n");
            }
            i++;
        }
        System.out.println("----------------------------------");
        


    }
}
