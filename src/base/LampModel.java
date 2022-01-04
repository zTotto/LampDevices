package base;

/**
 * Class to model a Lamp.
 */
public class LampModel {

    private boolean state;

    /**
     * Turns the lamp on.
     */
    public void switchOn() {
        state = true;
    }

    /**
     * Turns the lamp off.
     */
    public void switchOff() {
        state = false;
    }

    /**
     * Inverts the lamp state.
     */
    public void invertState() {
        state = !state;
    }

    /**
     * Returns the current state of the lamp.
     * @return whether the lamp is on or off
     */
    public boolean isOn() {
        return state;
    }
}
