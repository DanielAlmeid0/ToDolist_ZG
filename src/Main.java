import persistence.TaskFileStorage;
import repository.TaskRepository;
import ui.ConsoleMenu;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        TaskFileStorage storage = new TaskFileStorage(Path.of("tasks.csv"));
        TaskRepository repository = new TaskRepository(storage);
        ConsoleMenu menu = new ConsoleMenu(repository);
        menu.iniciar();
    }
}
