package View;

import Controller.ControllerMesa;
import Model.Mesa;

import java.util.Scanner;

public class ViewMesa {
    private ControllerMesa controller;
    private Mesa[] mesas;

    public ViewMesa() {
        controller = new ControllerMesa();
        mesas = controller.carregarMesasDoFicheiro("mesas.txt");
    }

    public void exibirMenuMesas() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Menu de Mesas ===");
            System.out.println("0 - Listar Mesas");
            System.out.println("1 - Criar Mesa");
            System.out.println("2 - Editar Mesa");
            System.out.println("3 - Apagar Mesa");
            System.out.println("4 - Gravar Alterações e Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 0:
                    // Listar mesas
                    controller.exibirMesas(mesas);
                    break;
                case 1:
                    // Criar uma nova mesa
                    System.out.print("Digite o ID da nova mesa: ");
                    int idCriar = scanner.nextInt();
                    System.out.print("Digite o número de lugares: ");
                    int lugares = scanner.nextInt();
                    System.out.print("A mesa estará ocupada? (true/false): ");
                    boolean ocupada = scanner.nextBoolean();
                    mesas = controller.criarMesa(mesas, idCriar, lugares, ocupada);
                    break;
                case 2:
                    // Editar uma mesa existente
                    System.out.print("Digite o ID da mesa que deseja editar: ");
                    int idEditar = scanner.nextInt();
                    System.out.print("Digite o novo número de lugares: ");
                    lugares = scanner.nextInt();
                    System.out.print("A mesa está ocupada? (true/false): ");
                    ocupada = scanner.nextBoolean();
                    controller.atualizarMesa(mesas, idEditar, lugares, ocupada);
                    break;
                case 3:
                    // Apagar uma mesa
                    System.out.print("Digite o ID da mesa que deseja apagar: ");
                    int idApagar = scanner.nextInt();
                    mesas = controller.eliminarMesa(mesas, idApagar);
                    break;
                case 4:
                    // Gravar alterações e voltar
                    controller.gravarMesasNoFicheiro(mesas, "mesas.txt");
                    System.out.println("Alterações gravadas. Retornando ao menu principal.");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
