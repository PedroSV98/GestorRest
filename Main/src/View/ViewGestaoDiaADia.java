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
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1 -> controllerGestao.avancarTempo();
                case 2 -> encaminharPedido();
                case 3 -> escolherPratos();
                case 4 -> controllerGestao.processarPagamentos();
                case 5 -> System.out.println("Encerrar Gestão do Dia-a-Dia...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

    private void encaminharPedido() {
        System.out.print("Digite o nome do cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("Digite o número de pessoas: ");
        int quantidadePessoas = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        controllerGestao.encaminharPedido(cliente, quantidadePessoas);
    }

    public void escolherPratos() {
        System.out.print("Digite o nome do cliente para escolher pratos: ");
        String cliente = scanner.nextLine();

        Pedido pedido = controllerGestao.encontrarPedidoPorNome(cliente);
        if (pedido == null) {
            System.out.println("Pedido para o cliente não encontrado.");
            return;
        }

        controllerGestao.escolherPratos(pedido);
    }
}
