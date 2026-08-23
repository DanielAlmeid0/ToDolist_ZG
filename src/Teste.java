import java.time.LocalDate;
import model.Status;
import model.Task;

public class Teste {
    public Teste() {
    }

    static void main(String[] args) {
        Task t = new Task(1, "estudar java", LocalDate.of(2026, 9, 1), 2, "Estudos", Status.TODO);
        System.out.println(t);

        try {
            Task invalida = new Task(2, "X", LocalDate.now(), 9, "Casa", Status.TODO);
            System.out.println("Criou: " + String.valueOf(invalida));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }

    }
}