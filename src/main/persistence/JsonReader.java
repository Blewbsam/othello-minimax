package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;

// reads saved game state in JSON file.
// modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
public class JsonReader {
    private String directory;

    public JsonReader(String directory) {
        this.directory = directory;
    }

    // EFECTS: reads directory and converts it to 
    //         returning JSONObject
    public JSONObject read() throws IOException {
        // reads file
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(directory), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
         
        String jsonString = contentBuilder.toString();
        JSONObject dataObject = new JSONObject(jsonString);
        return dataObject;

    }



}
