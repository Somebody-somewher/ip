import java.io.FileNotFoundException;
import java.io.IOException;

public interface StorageInterface {

    public void writeToFile() throws IOException;

    public void readFromFile();
}
