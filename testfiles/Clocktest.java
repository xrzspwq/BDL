package testfiles;

import src.Clock;


public class Clocktest {
    
    public Clocktest(){
        Clock horloge = new Clock(1);
        
        System.out.println("Name: "+horloge.getName()+"\n Duration:"+horloge.getDuration()+"\n ");
        
    }
}
