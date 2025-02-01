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
                case 2 -> controllerGestao.encaminharPedido();
                case 3 -> {
                    Pedido[] pedidosEncaminhados = controllerGestao.getPedidosEncaminhados(); // Obter pedidos encaminhados

                    if (pedidosEncaminhados.length == 0) {
                        System.out.println("Não há clientes que podem escolher pratos agora.");
                        break;
                    }

                    // Listar clientes encaminhados
                    System.out.println("\n=== Clientes que podem escolher pratos ===");
                    for (int i = 0; i < pedidosEncaminhados.length; i++) {
                        System.out.println((i + 1) + ". " + pedidosEncaminhados[i].getCliente() +
                                " - " + pedidosEncaminhados[i].getQtdPessoas() + " pessoas");
                    }

                    // Usuário escolhe um cliente pelo número
                    System.out.print("Selecione um cliente pelo número: ");
                    int escolha = scanner.nextInt();
                    scanner.nextLine();

                    if (escolha < 1 || escolha > pedidosEncaminhados.length) {
                        System.out.println("Opção inválida.");
                        break;
                    }

                    // Recuperar pedido escolhido e chamar `escolherPratos()`
                    Pedido pedidoEscolhido = pedidosEncaminhados[escolha - 1];
                    controllerGestao.escolherPratos(pedidoEscolhido);
                }

                case 4 -> controllerGestao.processarPagamentos();
                case 5 -> System.out.println("Encerrar Gestão do Dia-a-Dia...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }


}
