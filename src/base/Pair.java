package base;

/**
 * Simple pair utility class.
 * 
 * @param <X> First generic
 * @param <Y> Second generic
 */
public class Pair<X, Y> {

    private X first;
    private Y second;

    /**
     * Empty constructor for the pair.
     */
    public Pair() {
        //Empty Constructor
    }

    /**
     * Constructor for the pair.
     * 
     * @param first  First generic
     * @param second First generic
     */
    public Pair(final X first, final Y second) {
        this.first = first;
        this.second = second;
    }

    /**
     * First object getter.
     * 
     * @return the first object of the pair
     */
    public X getFirst() {
        return first;
    }

    /**
     * Second object getter.
     * 
     * @return the second object of the pair
     */
    public Y getSecond() {
        return second;
    }

    /**
     * First object setter.
     * 
     * @param first sets the first object of the pair
     */
    public void setFirst(final X first) {
        this.first = first;
    }

    /**
     * Second object setter.
     * 
     * @param second sets the second object of the pair
     */
    public void setSecond(final Y second) {
        this.second = second;
    }
}
