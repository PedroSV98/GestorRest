package View;

import Controller.ControllerEstatisticas;
import Controller.ControllerGestaoDiaADia;
import Model.Reserva;

import java.util.Scanner;

public class ViewGestaoDiaADia {
    private final ControllerGestaoDiaADia controllerGestao;
    private final ControllerEstatisticas controllerEstatisticas;
    private final Scanner scanner;

    public ViewGestaoDiaADia(ControllerGestaoDiaADia controllerGestao, ControllerEstatisticas controllerEstatisticas) {
        this.controllerGestao = controllerGestao;
        this.controllerEstatisticas = controllerEstatisticas;
        this.scanner = new Scanner(System.in);
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
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> controllerGestao.avancarTempo();
                case 2 -> controllerGestao.encaminharPedido();
                case 3 -> controllerGestao.escolherPratos();
                case 4 -> controllerGestao.processarPagamentos();
                case 5 -> controllerGestao.exibirDesempenhoFinanceiro();
                case 6 -> controllerEstatisticas.exibirEstatisticasGerais();
                case 7 -> System.out.println("Encerrar Gestão do Dia-a-Dia...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 7);
    }
}