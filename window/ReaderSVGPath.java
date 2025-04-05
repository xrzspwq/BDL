package window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReaderSVGPath
{
    final private String path;
    
    public ReaderSVGPath(String filePath) throws FileNotFoundException,IllegalArgumentException
    {
        try 
        {
            File myObj = new File(filePath);
            String path = "";
            Scanner reader = new Scanner(myObj);
            boolean firstLoop = true;
            
            do 
            {  
                if (firstLoop) 
                {
                    reader.useDelimiter("<path");
                    if (!reader.hasNext())
                    {
                        reader.close();
                        throw new IllegalArgumentException("invalid file <path not found");
                    } 
                }
              
    
               reader.next();
                    
                reader.useDelimiter(" d\\s*=\\s*\"");
                if (!reader.hasNext())
                {
                    reader.close();
                    throw new IllegalArgumentException("invalid file, d attribute not found");
                } 
                reader.next();
                
                reader.useDelimiter("\"");
                if (!reader.hasNext())
                {
                    reader.close();
                    throw new IllegalArgumentException("invalide file, no quote found for the d attribute value");
                }
                reader.next();
                path += reader.next();

                firstLoop = false;
            } while (reader.findWithinHorizon("<path",0) != null);
         
           
            reader.close();
            this.path = path;

        } catch(FileNotFoundException | IllegalArgumentException e) {throw(e);}

    } 
    
    /**
     * This function returns the SVG path string read from the file.
     * 
     * @return The SVG path string.
     * 
     * @throws FileNotFoundException If the file specified by the filePath does not exist.
     * @throws IllegalArgumentException If the file does not contain a valid SVG path.
     * 
     * The SVG path string is obtained by parsing the file and extracting the 'd' attribute value 
     * from the first '<path>' element found in the file. If the file does not contain a '<path>' 
     * element or if the 'd' attribute is not found within the '<path>' element, an 
     * IllegalArgumentException is thrown.
     */
    public String getPath() {
        return path;
    }

    
}