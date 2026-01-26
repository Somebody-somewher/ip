import java.io.IOException;
import java.util.List;

public interface TaskManagerInterface {

    public List<Command> getCommands();

    public void saveTasks() throws IOException;

    public void loadTasks();
}
