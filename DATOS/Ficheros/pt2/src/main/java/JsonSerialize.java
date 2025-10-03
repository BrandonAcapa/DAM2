import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonSerialize {
    public static void serializeCoche(Coche c){
        ObjectMapper mapper = new ObjectMapper();

        // Creamos el fichero
        File fileCoche = new File("coche.json");
        FileWriter fw = null;
        BufferedWriter bw = null;

        try{
            var json = mapper.writeValueAsString(c);
            System.out.println(json);
            fw = new FileWriter(fileCoche);
            bw = new BufferedWriter(fw);
            bw.write(json);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if (bw!= null) {
                bw.close();}
                if (fw!= null) {
                fw.close();}
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void serializeCoches(Coche[] coches){
        ObjectMapper mapper = new ObjectMapper();

        File fileCcohes = new File("coches.json");
        FileWriter fw = null;
        BufferedWriter bw = null;

        try{
            var json = mapper.writeValueAsString(coches);
        }
    }
}
