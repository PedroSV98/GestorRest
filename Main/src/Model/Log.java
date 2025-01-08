package Model;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static final String LOG_FILE = "./data/log_2025-01-08.txt";

    public void registrarEvento(String mensagem) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(mensagem + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao registrar evento no log: " + e.getMessage());
        }
    }
}
