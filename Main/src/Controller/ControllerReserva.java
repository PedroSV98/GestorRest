package Controller;

import Model.Reserva;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class ControllerReserva {
    private ConfiguracoesController configController;
    private final String caminhoCompletoReservas;

    public ControllerReserva(ConfiguracoesController configController) {
        String basePath = configController.getModelo().getCaminhoFicheiros();
        this.caminhoCompletoReservas = basePath + "Reservas.txt";
    }

    /**
     * Lê as reservas do ficheiro e retorna um array de objetos Reserva.
     *
     * @return Reserva[] Array contem o todas as reservas carregadas.
     */
    public Reserva[] lerReservas() {
        String separador = configController.getModelo().getSeparador();
        Reserva[] reservasTemp = new Reserva[100]; // Limite inicial, ajustável dinamicamente
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoCompletoReservas), "UTF-8"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split(separador);
                    if (partes.length == 3) {
                        // Expande o array dinamicamente, se necessário
                        if (index >= reservasTemp.length) {
                            reservasTemp = expandirArrayReservas(reservasTemp);
                        }

                        Reserva reserva = new Reserva();
                        reserva.setNomeReserva(partes[0]);
                        reserva.setQtdPessoas(Integer.parseInt(partes[1]));
                        reserva.setTempoEntrada(Integer.parseInt(partes[2]));
                        reservasTemp[index++] = reserva;
                    } else {
                        System.out.println("Linha inválida no ficheiro de reservas: " + linha);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de reservas: " + e.getMessage());
        }

        // Retorna um array com apenas as reservas carregadas
        Reserva[] reservasCarregadas = new Reserva[index];
        System.arraycopy(reservasTemp, 0, reservasCarregadas, 0, index);
        return reservasCarregadas;
    }

    /**
     * Expande o tamanho do array de reservas.
     *
     * @param original O array original a ser expandido.
     * @return Um novo array com o dobro do tamanho do original.
     */
    private Reserva[] expandirArrayReservas(Reserva[] original) {
        Reserva[] novoArray = new Reserva[original.length * 2];
        System.arraycopy(original, 0, novoArray, 0, original.length);
        return novoArray;
    }
}