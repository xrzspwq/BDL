package src;

public class Pairs<E,T> {
    private E elem1;
    private T elem2;

    public Pairs(E elem, T elem2) {
        this.elem1 = elem;
        this.elem2 = elem2;
    }

    public E getElem1() {
        return elem1;
    }
    
    public T getElem2() {
        return elem2;
    }
    
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
    
    public static <E, T> Pairs<E, T> empty() {
        return new Pairs<>(null, null);
    }
}
