package FileManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import src.Elem;
import window.GraphicElem;
import window.Attribute;

public class CircuitLoader{
    
    private File file;

    public CircuitLoader(String filePath) throws IOException
    {
        if(filePath == null)
            throw new NullPointerException("Null file path");
        if(filePath.isBlank())
            throw new IllegalArgumentException("Blank file path");

        file = new File(filePath);

        if(!file.exists())
            throw new IllegalArgumentException("File does not exist");
        if(!file.canRead())
            throw new IOException("Can't read file");
    }

    public CircuitLoader(File file) throws IOException
    {
        if(!file.exists())
            throw new IllegalArgumentException("File does not exist");
        if(!file.canRead())
            throw new IOException("Can't read file");

        this.file = file;
    }

    public HashMap<Integer,GraphicElem> loadCircuit(File file) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<Integer,GraphicElem> res = new HashMap<>();

        try {
            JsonNode node = mapper.readTree(file);

            int i = 0;
            for(JsonNode elem : node)
            {
                GraphicElem gElem = loadGraphicElem(elem.toString());
                res.put(gElem.getID(),gElem);
                ++i;
            }

            return res;

        }
        catch (IOException e)
        {
            throw new IOException("Failed to load circuit : " + e.getMessage());
        }
    }

    public GraphicElem loadGraphicElem(String source) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(source);
            Elem elem = loadElem(node.get("Elem").toString());

            List<Object> attributes = loadAllAttributes(node.get("Attributes"));

            return new GraphicElem(elem,attributes);

        } catch (IOException e) {
            throw new IOException("Failed to load element" + e.getMessage());
        }
        
    }

    public Elem loadElem(String source)
    {
        ObjectMapper mapper = new ObjectMapper();

        try {

            JsonNode node = mapper.readTree(source).get("Elem");

            JsonNode tailleBusInNode = node.get("TailleBusIn");
            JsonNode tailleBusOutNode = node.get("TailleBusOut");

            ArrayList<Integer> tailleBusIn = new ArrayList<>();
            ArrayList<Integer> tailleBusOut = new ArrayList<>();

            for(JsonNode busInNode : tailleBusInNode)
            {
                tailleBusIn.add(busInNode.asInt());
            }
            for(JsonNode busOutNode : tailleBusOutNode)
            {
                tailleBusOut.add(busOutNode.asInt());
            }
            Elem res = new Elem(node.get("NbFluxIn").asInt(),node.get("NbFluxOut").asInt(),tailleBusIn,tailleBusOut);
            res.setName(node.get("Name").asText());

            return res;
        }
        catch (IOException e) {
            throw new IOException("Failed to load Element specs");
        }
    }
    

    public Attribute<?> loadAttribute(String source) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        try{
            JsonNode node = mapper.readTree(source);

            String name = node.get("Name").asText();
            String valueClass = node.get("ValueClass").asText();
            
            Object value;

            if(valueClass.equals("Integer"))
            {
                value = Integer.valueOf(node.get("Value").asText()); 
            }
            else{
                value = node.get("Value").asText();
            }

            return new Attribute<>(name,value);
        }
        catch(IOException e)
        {
            throw new IOException("Failed to load attribute : " + e.getMessage());
        }
    }

    public List<Object> loadAllAttributes(JsonNode node) throws IOException
    {
        
        try {
            List<Object> res = new ArrayList<>();

            for(JsonNode attr : node)
            {
                res.add(loadAttribute(attr.toString()));
            }

            return res;
        }        
        catch (IOException e)
        {
            throw new IOException("Failed to load attributes");
        }    
    }
}