import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Output{
    private static List<String> outputContent = new ArrayList<>();

    public static void outputAdd(String line){outputContent.add(line);}

    public static void write(String fileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(String line:outputContent){
                writer.write(line+"\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}