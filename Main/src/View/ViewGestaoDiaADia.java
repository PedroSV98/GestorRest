package View;

import Controller.ControllerEstatisticas;
import Controller.ControllerGestaoDiaADia;
import Model.Reserva;
import Controller.LogController;


import java.util.Scanner;

public class ViewGestaoDiaADia {
    private final ControllerGestaoDiaADia controllerGestao;
    private final ControllerEstatisticas controllerEstatisticas;
    private final LogController.ScannerLog scannerLog;

    public ViewGestaoDiaADia(ControllerGestaoDiaADia controllerGestao, ControllerEstatisticas controllerEstatisticas, LogController logController) {
        this.controllerGestao = controllerGestao;
        this.controllerEstatisticas = controllerEstatisticas;
        this.scannerLog = logController.new ScannerLog(new Scanner(System.in), logController);  // Inicializando ScannerLog com instância de LogController
    }

    public void exibirMenu() {
        int opcao;
        do {
            Reserva[] reservasAtuais = controllerGestao.getReservasNoTempoAtual();
            System.out.println("\n=== Reservas no Tempo Atual (" + controllerGestao.getUnidadesTempoAtual() + ") ===");
            if (reservasAtuais.length > 0) {
                for (Reserva reserva : reservasAtuais) {
                    System.out.println("Cliente: " + reserva.getNomeReserva() +
                            ", Pessoas: " + reserva.getQtdPessoas());
                }
            } else {
                System.out.println("Sem reservas para o tempo atual.");
            }

            System.out.println("\n=== Gestão do Dia-a-Dia ===");
            System.out.println("1. Avançar Tempo");
            System.out.println("2. Encaminhar Pedido");
            System.out.println("3. Escolher Pratos para Pedido");
            System.out.println("4. Processar Pagamentos");
            System.out.println("5. Financeiro");
            System.out.println("6. Estatísticas Gerais");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
            scannerLog.nextLine();  // Consumindo o salto de linha

            switch (opcao) {
                case 1 -> {
                    controllerGestao.avancarTempo();
                }
                case 2 -> {
                    controllerGestao.encaminharPedido();
                }
                case 3 -> {
                    controllerGestao.escolherPratos();
                }
                case 4 -> {
                    controllerGestao.processarPagamentos();
                }
                case 5 -> {
                    controllerGestao.exibirDesempenhoFinanceiro();
                }
                case 6 -> {
                    controllerEstatisticas.exibirEstatisticasGerais();
                }
                case 7 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 7);

        scannerLog.close();  // Fechando o ScannerLog
    }
}
