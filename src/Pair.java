package src;

public class Pair<E,T> {
    private E elem1;
    private T elem2;

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
     * This method retrieves the second element stored in the pair.
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

    
    public void setElem2(T elem2) {
        this.elem2 = elem2;
    }
    
    @Override
    public String toString() {
        return "(" + elem1 + ", " + elem2 + ")";
    }
    
    public static <E, T> Pair<E, T> empty() {
        return new Pair<>(null, null);
    }
}
