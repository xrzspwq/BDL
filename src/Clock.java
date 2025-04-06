package src;

import java.time.Instant;
import java.util.ArrayList;
import java.time.Duration;

public class Clock extends Elem {
    Instant lastuse;
    Duration duration;

    EnumBool state = EnumBool.TRUE;


    public Clock() {
        name="clock";
        nbBusIn=1;
        nbBusOut=1;
        lastuse = Instant.now();
        duration = Duration.ofMillis(10*10);
    }


    /**
     * Constructs a new instance of Clock with default duration of 100 milliseconds.
     * The clock starts in the 'true' state and generates a pulse every time the
     * duration has elapsed.
     * The pulse is a single 'true' value followed by a single 'false' value.
     * The clock's state is then reset to 'true' and the cycle repeats.
     */
    public Clock(long tick) {
        name="clock";
        nbBusIn=1;
        nbBusOut=1;
        lastuse = Instant.now();
        duration = Duration.ofMillis(tick*10);
    }

    /**
     * Sets the duration for the clock to toggle its state.
     *
     * This method updates the internal duration value that determines the time
     * interval between state changes.
     * When the elapsed time since the last state change exceeds this duration, the
     * clock's state is toggled.
     *
     * @param duration the new duration for the clock to toggle its state.
     */
    public void setDuration(long tick) {
        this.duration = Duration.ofMillis(tick*10);
    }

    public long getDuration(){
        return duration.toMillis()/10;
    }

    /**
     * Evaluates the clock's state based on the elapsed time since the last state change.
     *
     * This method checks if the elapsed time since the last state change exceeds the
     * specified duration. If the condition is met, the clock's state is toggled and the
     * updated state is added to the output list.
     *
     * @return a list containing a single element representing the current state of the clock.
     *         The element is an EnumBool value, which can be either TRUE or FALSE.
     */
    @Override
    public ArrayList<ArrayList<EnumBool>> evaluate() {
        out.clear();
        
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant inst = Instant.now();
        ArrayList<EnumBool> output = new ArrayList<EnumBool>();
        
        if (Duration.between(lastuse, inst).compareTo(duration) > 0) {
            lastuse = inst;
            if (EnumBool.TRUE.equals(state)) {
                state = EnumBool.FALSE;
            } else if (EnumBool.FALSE.equals(state)) {
                state = EnumBool.TRUE;
            }

            output.add(state);
        }

        out.add(output);
        return out;
    }
}
