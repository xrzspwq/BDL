package src;

public class Pair<E, T> {
    private E elem1;
    private T elem2;

    /**
     * Constructs a new Pair instance with the given elements.
     *
     * @param elem  The first element of the pair.
     * @param elem2 The second element of the pair.
     */
    public Pair(E elem, T elem2) {
        this.elem1 = elem;
        this.elem2 = elem2;
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element of the pair
     */
    public E getElem1() {
        return elem1;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element of the pair
     */
    public T getElem2() {
        return elem2;
    }

    /**
     * Sets the first element of the pair.
     *
     * This method updates the first element of the pair with the provided value.
     *
     * @param elem the new value for the first element of the pair
     */
    public void setElem(E elem) {
        this.elem1 = elem;
    }

    /**
     * Sets the second element of the pair.
     *
     * This method updates the second element of the pair with the provided value.
     *
     * @param elem2 the new value for the second element of the pair
     */
    public void setElem2(T elem2) {
        this.elem2 = elem2;
    }

    /**
     * Returns a string representation of the pair.
     *
     * This method generates a string representation of the pair in the format
     * "(elem1, elem2)".
     *
     * @return a string representation of the pair
     */
    @Override
    public String toString() {
        return "(" + elem1 + ", " + elem2 + ")";
    }

    /**
     * Returns an empty pair with null values for both elements.
     *
     * This static method creates a new instance of the Pair class with both
     * elements set to null.
     * It is useful when you need to represent an empty pair or when you want to
     * initialize a pair
     * with default values.
     *
     * @param <E> the type of the first element in the pair
     * @param <T> the type of the second element in the pair
     * @return a new Pair instance with both elements set to null
     */
    public static <E, T> Pair<E, T> empty() {
        return new Pair<>(null, null);
    }

}
