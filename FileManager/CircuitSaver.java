package filemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import window.Attribute;
import window.GraphicElem;
import src.Elem;


public class CircuitSaver
{
    private File file;

    public CircuitSaver(String filePath) throws IOException
    {
        if(filePath == null)
            throw new NullPointerException("Null file path");
        if(filePath.isBlank())
            throw new IllegalArgumentException("Blank file path");
        
        try {
            file = new File(filePath);
            if(!file.exists())
                file.createNewFile();   

        } catch (IOException e) {
            throw new IOException("Error file");
        }

        file.setWritable(true);
        file.setReadable(true);
    }

    public boolean saveCircuit(List<GraphicElem> circuit) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode rootNode = mapper.createObjectNode();

        for(GraphicElem elem : circuit){
            JsonNode node = mapper.createObjectNode();
            node.put("Elem",elemToObjectNode(elem.getElem()));
            node.put("Attributes",allAttributesToObjectNode(elem.getAttributes()));
            arrayNode.add(node);
        }
        rootNode.set("Circuit",arrayNode);
        try {
            mapper.writeValue(file,rootNode);
            return true;
        } catch (IOException e) {
            throw new IOException("Error saving circuit: " + e.getMessage());
        }
    }

    static public ObjectNode elemToObjectNode(Elem elem)
    {
        if(elem == null)
            throw new NullPointerException("Elem is null");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("NbFluxIn",elem.getNbBusIn());
        node.put("NbFluxOut",elem.getNbBusOut());

        ArrayNode arrayBusIn = mapper.createArrayNode();
        ArrayNode arrayBusOut = mapper.createArrayNode();


        for(Integer integ : elem.getTailleBusIn())
        {
            arrayBusIn.add(integ);
        }

        node.set("TailleBusIn",arrayBusIn);

        for(Integer integ2 : elem.getTailleBusOut())
        {
            arrayBusOut.add(integ2);
        }

        node.set("TailleBusOut",arrayBusOut);

        return node;
    }

    static public String elemToJson(Elem elem)
    {
        return new ObjectMapper().writeValueAsString(elemToObjectNode(elem));
    }

    static public ObjectNode attributeToObjectNode(Attribute<?> attribute)
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("Name",attribute.getName());
        node.put("Value",attribute.getValue());
        node.put("ValueClass",attribute.getValue().getClass().getSimpleName());

        return node;
        
    }
    static public String attributeToJson(Attribute<?> attribute)
    {
        return new ObjectMapper().writeValueAsString(attributeToObjectNode(attribute)); 
    }

    static public ObjectNode allAttributesToObjectNode(List<Object> attributes)
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode parent = mapper.createObjectNode();
        ArrayNode node = mapper.createArrayNode();

        for(Object obj : attributes){
            Attribute<?> attr = (Attribute<?>) obj;
            node.add(attributeToObjectNode(attr));
        }

        parent.set("Attributes",node);

        return parent;
    }

    static public String allAttributesToJson(List<Object> attributes)
    {
        return new ObjectMapper().writeValueAsString(allAttributesToObjectNode(attributes));
    }

    static public String graphicElemToJson(GraphicElem graphElem)
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        node.put("Elem",elemToObjectNode(graphElem.getElem()));
        node.put("Attributes",allAttributesToObjectNode(graphElem.getAttributes()));
        
        return mapper.writeValueAsString(node);
    }
}