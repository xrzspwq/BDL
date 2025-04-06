package filemanager;

import src.*;
import window.ReaderSVGPath;
import window.GraphicElem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import filemanager.CircuitSaver;

import javafx.scene.shape.SVGPath;

public class ImportElem {
    private File file;

    public ImportElem(File file){
        if(!file.exists())
            throw new IllegalArgumentException("Inexistant file");

        if(file == null)
            throw new NullPointerException("Null file");
            
        if(!file.canRead())
            throw new IOException("Can't read file");

        this.file = file;
    }

    public ImportElem(String filePath)
    {
        if(filePath == null)
            throw new NullPointerException("Null file path");

        if(filePath.isBlank())
            throw new IllegalArgumentException("File path is blank");
        
        file = new File(filePath);

        if(!file.exists())
            throw new IllegalArgumentException("Inexistant file");

        if(!file.canRead())
            throw new IOException("Can't read file");
    }

    public void importComponentSVG(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(file);
        node.get("SVG");

        ReaderSVGPath readerSVG = new ReaderSVGPath(node.asText());

        return;
    }

    public String importName(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(file);
        node.get("Name");

        return node.asText();
    }

    public GraphicElem importGraphicElem(){

        GraphicElem res = new GraphicElem(importCustomElem());
        SVGPath svg = new SVGPath();
        res.setShape(svg.setContent(importComponentSVG()));

        return res;
    }

    public void addToStdlib(GraphicElem elem){
        String filePath = "stdlib/" + elem.getElem().getName() + ".json";
        CircuitSaver elemSaver = new CircuitSaver(filePath);
        HashMap<Integer,GraphicElem> map = new HashMap<>();

        try {
            elemSaver.saveCircuit(map.put(0,elem)); }
            catch(Exception e) {}
    }

    public ArrayList<ArrayList<EnumBool>> importTruthTable(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);
        JsonNode node = root.get("truthTable");

        ArrayList<EnumBool> subRes = new ArrayList<>();
        ArrayList<ArrayList<EnumBool>> res = new ArrayList<>();

        for(JsonNode array : node)
        {
            for(JsonNode subArray : array)
            {
                switch (subArray.asText()) {
                    case "NOTHING":
                        subRes.add(EnumBool.NOTHING);
                        break;
                    case "ERR":
                        subRes.add(EnumBool.ERR);
                        break;
                    case "TRUE":
                        subRes.add(EnumBool.TRUE);
                        break;
                    case "FALSE":
                        subRes.add(EnumBool.FALSE);
                        break;
                } 
            }
            res.add(subRes);
        }

        return res;

    }

    public CustomElem importCustomElem(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(file);

        int nbBusIn = node.get("NbBusIn").asInt();
        int nbBusOut = node.get("NbBusOut").asInt();

        return new CustomElem(importName(), nbBusIn, nbBusOut, importTruthTable());
    }
}