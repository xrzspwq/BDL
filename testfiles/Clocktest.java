package testfiles;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import src.Clock;
import src.EnumBool;

public class Clocktest {
    
    public Clocktest(){
        Clock horloge = new Clock(1000000000);
        ArrayList<ArrayList<EnumBool>> out = new ArrayList<>();
        System.out.println("Name: "+horloge.getName()+"\n Duration:"+horloge.getDuration()+"\n ");
        for(int i = 0 ; i<2; i++){
            long timestart= System.nanoTime();
            out = horloge.evaluate();
            long timestop= System.nanoTime();
            long res = TimeUnit.SECONDS.convert(timestop - timestart, TimeUnit.NANOSECONDS);
            System.out.println("Result obtain: "+out+" in "+res+" time");
        }
        
    }
}
