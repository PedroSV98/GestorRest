package Controller;

import Model.Reserva;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class ControllerReserva {

    private final String caminhoCompletoReservas;
    private ConfiguracoesController configuracoesController;

    public ControllerReserva(ConfiguracoesController configController) {
        String basePath = configController.getModelo().getCaminhoFicheiros();
        this.caminhoCompletoReservas = basePath + "Reservas.txt";
    }

    public Reserva[] lerReservas() {
        String separador = configuracoesController.getModelo().getSeparador();

        Reserva[] reservas = new Reserva[1000000000]; // Supondo um limite de 100 reservas
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoCompletoReservas), "UTF-8"))) {
            String linha;
            while ((linha = reader.readLine()) != null && index < reservas.length) {
                String[] partes = linha.split(separador);
                if (partes.length == 3) {
                    Reserva reserva = new Reserva();
                    reserva.setNomeReserva(partes[0]);
                    reserva.setQtdPessoas(Integer.parseInt(partes[1]));
                    reserva.setTempoEntrada(Integer.parseInt(partes[2]));
                    reservas[index++] = reserva;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de reservas: " + e.getMessage());
        }

        // Retorna apenas as reservas carregadas
        Reserva[] reservasCarregadas = new Reserva[index];
        System.arraycopy(reservas, 0, reservasCarregadas, 0, index);
        return reservasCarregadas;
    }
}
