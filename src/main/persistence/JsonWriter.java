package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;




// Writes JSON represantation of Board in a file.
// modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {
    private PrintWriter writer;
    private String destination;
    private final int indentFactor = 5;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: connects writer to file
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }


    // MODIFIES: this
    // writes json in file.
    public void write(JSONObject gameData) {
        writer.print(gameData.toString(indentFactor));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

}
