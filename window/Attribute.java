package window;

public class Attribute<T>
{
    private final Pair<String,T> attribute;

    Attribute(String name, T val)
    {
        attribute = new Pair<>(name,val); 
    }

    public String getName() {
        return attribute.getKey();
    }

    public T getValue() {
        return attribute.getValue();
    }
}
