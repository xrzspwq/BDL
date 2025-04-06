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
    /*
     * Nouvelle instance de CircuitSaver
     * 
     * @param filePath  Chemin du fichier de sauvegarde
     * 
     * @requires    filePath != null
     * @requires    !filePath.isBlank()
     * @ensures     file.exists()
     * @ensures     file.canRead()
     * @ensures     file.canWrite()
     * 
     * @throws IOException  Si le fichier renvoie une erreur
     * @throws IllegalArgumentException Si le fichier de sauvegarde n'existe pas ou le filePath est vide
     * @throws NullPointerException Si le filePath est null
     */
    public CircuitSaver(String filePath) throws IOException
    {
        if(filePath == null)
            throw new NullPointerException("Null file path");
        if(filePath.isBlank())
            throw new IllegalArgumentException("Blank file path");

        file = new File(filePath);
        try {
            
            if(!file.exists())
                file.createNewFile();   

        } catch (IOException e) {
            throw new IOException("Error file");
        }

        file.setWritable(true);
        file.setReadable(true);
    }

    /*
     * Nouvelle instance de CircuitSaver
     * 
     * @param file  Fichier de sauvegarde
     * 
     * @ensures     file.exists()
     * @ensures     file.canRead()
     * @ensures     file.canWrite()
     * 
     * @throws NullPointerException Si le fichier est null
     * @throws NullPointerException En cas de probleme d'IO
     */
    public CircuitSaver(File file) throws IOException, NullPointerException
    {
        if(file == null)
            throw new NullPointerException("Null file");
            
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new IOException("IO Error");
            } 
        }  

        file.setWritable(true);
        file.setReadable(true);
    }

    /*
     * Sauvegarder le circuit en parametre dans le fichier
     * 
     * @param circuit  Circuit a sauvegarder
     * 
     * @requires    circuit != null
     * @requires    circuit.isEmpty()
     * 
     * @returns boolean     Renvoie toujours true
     * @throws IOException  Erreur au niveau de l'ecriture sur le fichier
     */
    public boolean saveCircuit(Hashmap<Integer,GraphicElem> circuit) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode rootNode = mapper.createObjectNode();

        for(GraphicElem elem : circuit){
            JsonNode node = mapper.createObjectNode();
            node.put("Orientation",elem.getOrientation().toString());

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

    /*
     * Convertir une instance d'Elem en ObjectNode
     * 
     * @param elem  L'Elem a convertir
     * 
     * @requires    elem != null
     * 
     * @returns ObjectNode  
     * @throws NullPointerException  Si Elem est null
     */
    static public ObjectNode elemToObjectNode(Elem elem)
    {
        if(elem == null)
            throw new NullPointerException("Elem is null");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("NbBusIn",elem.getNbBusIn());
        node.put("NbBusOut",elem.getNbBusOut());

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

    /*
     * Convertir une instance d'Elem en chaine de caracteres au format JSON
     * 
     * @param elem  L'Elem a convertir
     * 
     * @requires    elem != null
     * 
     * @returns String  L'Elem "stringified" en format JSON
     */
    static public String elemToJson(Elem elem)
    {
        return new ObjectMapper().writeValueAsString(elemToObjectNode(elem));
    }

    /*
     * Convertir une instance dun Attribute en ObjectNode
     * 
     * @param attribute L'Attribute a convertir
     * 
     * @requires    attribute != null
     * 
     * @returns ObjectNode  
     */
    static public ObjectNode attributeToObjectNode(Attribute<?> attribute)
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("Name",attribute.getName());
        node.put("Value",attribute.getValue());
        node.put("ValueClass",attribute.getValue().getClass().getSimpleName());

        return node;    
    }

    /*
     * Convertir une instance d'un Attribute en chaine de caracteres au format JSON
     * 
     * @param attribute L'Attribute a convertir
     * 
     * @requires    attribute != null
     * 
     * @returns String  L'Attribute "stringified" en format JSON
     */
    static public String attributeToJson(Attribute<?> attribute)
    {
        return new ObjectMapper().writeValueAsString(attributeToObjectNode(attribute)); 
    }

    /*
     * Convertir une instance d'une liste d'Attribute en ObjectNode
     * 
     * @param attributes La liste d'Attribute a convertir
     * 
     * @requires    attributes != null
     * @requires    !attributes.isEmpty()
     * 
     * @returns ObjectNode  
     */
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

    /*
     * Convertir une instance d'une liste d'Attribute en chaine de caractere au format JSON
     * 
     * @param attributes La liste d'Attribute a convertir
     * 
     * @requires    attributes != null
     * @requires    !attributes.isEmpty()
     * 
     * @returns String  La liste d'Attribute en format JSON  
     */
    static public String allAttributesToJson(List<Object> attributes)
    {
        return new ObjectMapper().writeValueAsString(allAttributesToObjectNode(attributes));
    }

    /*
     * Convertir une instance de GraphicElem en chaine de caractere au format JSON
     * 
     * @param graphElem Le GraphicElem a convertir
     * 
     * @requires    graphElem != null
     * 
     * @returns String  Le GraphicElem en format JSON  
     */
    static public String graphicElemToJson(GraphicElem graphElem)
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        node.put("Elem",elemToObjectNode(graphElem.getElem()));
        node.put("Orientation",graphElem.getOrientation().toString());
        node.put("Attributes",allAttributesToObjectNode(graphElem.getAttributes()));
        
        return mapper.writeValueAsString(node);
    }
}