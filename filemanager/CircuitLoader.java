package filemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.lang.Class;

import src.Elem;
import window.GraphicElem;
import window.Attribute;

public class CircuitLoader{
    
    private File file;
    /*
     * Nouvelle instance de CircuitLoader
     * 
     * @param filePath  Chemin du fichier de sauvegarde
     * 
     * @requires    filePath != null
     * @requires    !filePath.isBlank()
     * @ensures     file.exists()
     * @ensures     file.canRead()
     * 
     * @throws IOException  Si le fichier ne peut pas etre lu
     * @throws IllegalArgumentException Si le fichier de sauvegarde n'existe pas ou le filePath est vide
     * @throws NullPointerException Si le filePath est null
     */
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

    /*
     * Nouvelle instance de CircuitLoader
     * 
     * @param file  Fichier de sauvegarde
     * 
     * @requires     file.exists()
     * @requires     file.canRead()
     * 
     * @throws IOException  Si le fichier ne peut pas etre lu
     * @throws IllegalArgumentException Si le fichier de sauvegarde n'existe pas
     */
    public CircuitLoader(File file) throws IOException
    {
        if(!file.exists())
            throw new IllegalArgumentException("File does not exist");
        if(!file.canRead())
            throw new IOException("Can't read file");

        this.file = file;
    }


    /*
     * Charge tout le circuit sous forme de Hashmap de GraphicElem depuis le fichier
     * 
     * @param file  Fichier de sauvegarde
     * 
     * @ensures     file.exists()
     * @ensures     file.canRead()
     * 
     * @throws IOException  Si le circuit ne peut pas etre charge
     */
    public HashMap<Integer,GraphicElem> loadCircuit() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<Integer,GraphicElem> res = new HashMap<>();

        try {
            JsonNode node = mapper.readTree(file);

            int i = 0;
            for(JsonNode elem : node)
            {
                GraphicElem gElem = loadGraphicElem(elem.toString());
                res.put(gElem.getId(),gElem);
                ++i;
            }

            return res;

        }
        catch (IOException e)
        {
            throw new IOException("Failed to load circuit : " + e.getMessage());
        }
    }


    /*
     * Lis source ecrit en JSON et le parse en instance de GraphicElem
     * 
     * @param source  Texte en format JSON
     * 
     * @requires    !source.isBlank()
     * @requires    source != null
     * 
     * @throws IOException  Si l'element ne peut pas etre charge
     */
    public GraphicElem loadGraphicElem(String source) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(source);
            Elem elem = loadElem(node.get("Elem").toString());

            List<Object> attributes = loadAllAttributes(node.get("Attributes"));
            
            GraphicElem res = new GraphicElem(elem,attributes);
            res.setOrientation(node.get("Orientation").asText());

            return res;

        } catch (IOException e) {
            throw new IOException("Failed to load element" + e.getMessage());
        }
        
    }


    /*
     * Lis source ecrit en JSON et le parse en instance d'Elem
     * 
     * @param source  Texte en format JSON
     * 
     * @requires    !source.isBlank()
     * @requires    source != null
     * 
     * @throws IOException  Si l'element ne peut pas etre charge
     */
    public Elem loadElem(String source)
    {
        ObjectMapper mapper = new ObjectMapper();

        try {

            JsonNode node = mapper.readTree(source).get("Elem");

            JsonNode name = node.get("Name");

            Class gateClass = Class.forName(name.asText());
            Elem res = gate.newInstance();
            res.setName(node.get("Name").asText());

            /*JsonNode tailleBusInNode = node.get("TailleBusIn");
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
            }*/

            res.setNbBusIn(node.get("NbBusIn").asInt());
            res.setNbBusOut(node.get("NbBusOut").asInt());
            
            return res;
        }
        catch (IOException e) {
            throw new IOException("Failed to load Element specs");
        }
    }
    

    /*
     * Lis source ecrit en JSON et le parse en une instance d'Attribute
     * 
     * @param source  Texte en format JSON
     * 
     * @requires    !source.isBlank()
     * @requires    source != null
     * 
     * @throws IOException  Si l'element ne pas etre charge
     */
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

    /*
     * Lis source ecrit en JSON et le parse en instance d'une liste d'Attributes
     * 
     * @param source  Texte en format JSON
     * 
     * @requires    !source.isBlank()
     * @requires    source != null
     * 
     * @throws IOException  Si l'element ne pas etre charge
     */
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