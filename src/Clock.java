package src;

import java.time.Instant;
import java.util.ArrayList;
import java.time.Duration;

public class Clock extends Elem{
    Instant lastuse;
    Duration duration;

    EnumBool state=EnumBool.TRUE;


    public Clock() {
        lastuse = Instant.now();
        duration = Duration.ofMillis(100);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }


    public ArrayList<ArrayList<EnumBool>> evaluate() {
        Instant inst = Instant.now();
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();

        if(Duration.between(lastuse, inst).compareTo(duration) > 0){
            lastuse = inst;
            if(EnumBool.TRUE.equals(state)){
                state = EnumBool.FALSE;
            }else if(EnumBool.FALSE.equals(state)){
                state = EnumBool.TRUE;
            }

            output.add(state);
        }

        Out.add(output);
        return Out;
    }
}

