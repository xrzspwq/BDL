package testfiles;

import src.Constant;
import src.Not;
import src.Pair;
import src.Wire;
import src.Elem;

public class Wiretest {
    
    public Wiretest(){
        Constant intest1 = new Constant();
        Constant intest2 = new Constant();
        Not nottest = new Not(1, 1);
        Wire lien1 = new Wire(intest1, 1, null);
        
        Object[][] table = new String[15][]; 
        Integer i = 0;
        
        
        table[0] = new String[] {"OUT","EXPECTED"};

        table[1] = new String[] {"Test","getEntry\n"};
        table[2] = new String[] {lien1.getEntry()+""," <"+intest1+",1>"};
        
        table[3] = new String[] {"Test","connect\n"};
        table[4] = new String[] {lien1.connect(nottest, 1) + "","True"};
        
        table[5] = new String[] {"Test","getExit\n"};
        table[6] = new String[] {lien1.getExit()+""," [[<"+nottest+",1>]]"};
        
        lien1.changeEntry(intest2, 1);
        table[7] = new String[] {"Test","change entry\n"};
        table[8] = new String[] {lien1.getEntry()+""," <"+intest2+",1>\n\n\told entry:<"+intest1+",1>\n\n\t  Exit:"+lien1.getExit()};
        
        table[9] = new String[] {"Test getState","default state\n"};
        table[10] = new String[] {lien1.getState()+"","true"};
        
        table[11] = new String[] {"Test","disconnect\n"};
    

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
