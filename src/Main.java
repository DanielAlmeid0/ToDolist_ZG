import repository.TaskRepository;
import ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();
        ConsoleMenu menu = new ConsoleMenu(repository);
        menu.iniciar();
    }
}
