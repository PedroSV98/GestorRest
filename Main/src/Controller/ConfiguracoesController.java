package Controller;

import Model.Configuracoes;
import View.ConfiguracoesView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConfiguracoesController {

    private Configuracoes modelo;
    private static ConfiguracoesController controller;

    // Aqui definimos o caminho completo do config.txt (ajusta se necessário)
    private final String CAMINHO_FICHEIRO = "C:\\Users\\Acer\\Desktop\\LP1\\GestorRest\\Main\\src\\data\\config.txt";

    private ConfiguracoesController() {
        // Instancia o modelo com o caminho do ficheiro
        modelo = new Configuracoes(CAMINHO_FICHEIRO);

        // Tenta carregar assim que o controller é criado
        try {
            modelo.carregarConfiguracoes();
        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações: " + e.getMessage());
        }
    }

    public static ConfiguracoesController getInstancia(){
        // padrão singleton aplicado
        if(controller == null)
            controller = new ConfiguracoesController();

        return controller;

    }

    // Método para retornar o objeto modelo (para a View exibir)
    public Configuracoes getModelo() {
        return modelo;
    }

    // Atualizar campos específicos
    public void atualizarCaminhoFicheiros(String valor) {
        modelo.setCaminhoFicheiros(valor);
    }

    public void atualizarSeparador(String valor) {
        modelo.setSeparador(valor);
    }

    public void atualizarUnidadesTempoDia(int valor) {
        modelo.setUnidadesTempoDia(valor);
    }

    public void atualizarTempoEsperaAcao(int valor) {
        modelo.setTempoEsperaAcao(valor);
    }

    public void atualizarCustoClienteNaoAtendido(double valor) {
        modelo.setCustoClienteNaoAtendido(valor);
    }


    // Guardar no ficheiro
    public void guardar() {
        try {
            modelo.guardarConfiguracoes();
            System.out.println("Configurações guardadas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao guardar configurações: " + e.getMessage());
        }
    }
    public String identificarSeparador(String caminhoFicheiro) {
        String[] separadoresComuns = {";", ":", "/", "-", "_"};
        int[] contadores = new int[separadoresComuns.length];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoFicheiro))) {
            String linha;
            int linhasAnalisadas = 0;

            while ((linha = br.readLine()) != null && linhasAnalisadas < 10) { // Analisa até 10 linhas
                for (int i = 0; i < separadoresComuns.length; i++) {
                    contadores[i] += linha.split(separadoresComuns[i], -1).length - 1; // Conta ocorrências do separador
                }
                linhasAnalisadas++;
            }

            // Identifica o separador com mais ocorrências
            int maxIndex = 0;
            for (int i = 1; i < contadores.length; i++) {
                if (contadores[i] > contadores[maxIndex]) {
                    maxIndex = i;
                }
            }

            return contadores[maxIndex] > 0 ? separadoresComuns[maxIndex] : null; // Retorna o separador mais frequente
        } catch (Exception e) {
            System.out.println("Erro ao identificar o separador: " + e.getMessage());
            return null;
        }
    }


}

