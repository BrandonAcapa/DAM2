import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonDeserialize {

    public static void deserializeLanguage() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File json = new File("../../language.json");

        Language idisomas = mapper.readValue(json, Language.class);
    }
}
