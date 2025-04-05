package window;

public class Attribute<T>
{
    private final Pair<String,T> attribute;

    Attribute(String name, T val)
    {
        attribute = new Pair<>(name,val); 
    }

    /**
     * Returns the name of the attribute.
     *
     * @return the name of the attribute as a {@link String}
     */
    public String getName() {
        return attribute.getKey();
    }


    /**
     * Returns the value of the attribute.
     *
     * @return the value of the attribute as a generic type {@link T}
     */
    public T getValue() {
        return attribute.getValue();
    }


    /*
    public setValue(T value) {
        attribute.setValue(value);
    }
    */
}
