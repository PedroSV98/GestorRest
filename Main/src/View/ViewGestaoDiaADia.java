package View;

import Controller.ControllerGestaoDiaADia;
import Model.Pedido;
import Model.Reserva;

import java.util.Scanner;

public class ViewGestaoDiaADia {
    private final ControllerGestaoDiaADia controllerGestao;
    private final Scanner scanner;

    public ViewGestaoDiaADia(ControllerGestaoDiaADia controllerGestao) {
        this.controllerGestao = controllerGestao;
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public void exibirMenu() {
        int opcao;
        do { // Mostrar reservas no tempo atual
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
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1 -> controllerGestao.avancarTempo();
                case 2 -> controllerGestao.encaminharPedido();
                case 3 -> controllerGestao.escolherPratos(); // 🚀 Agora chamamos corretamente o método!
                case 4 -> controllerGestao.processarPagamentos();
                case 5 -> controllerGestao.exibirDesempenhoFinanceiro();
                case 6 -> System.out.println("Encerrar Gestão do Dia-a-Dia...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
    }


}
