package View;

import Controller.ControllerMesa;
import Model.Mesa;
import java.util.Scanner;

public class ViewMesa {

    private ControllerMesa controller;
    private Scanner scanner;

    public ViewMesa(ControllerMesa controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    // Método para exibir o menu e interagir com o usuário
    public void exibirMenu() {
        Mesa[] mesas = controller.carregarMesasDoFicheiro("mesas.txt");
        int opcao = -1;

        while (opcao != 5) {
            // Menu de opções
            System.out.println("Menu:");
            System.out.println("0 - Ler mesas");
            System.out.println("1 - Criar mesa");
            System.out.println("2 - Editar mesa");
            System.out.println("3 - Apagar mesa");
            System.out.println("4 - Gravar no arquivo");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 0:
                    // Ler e exibir mesas
                    controller.exibirMesas(mesas);
                    break;
                case 1:
                    // Criar uma nova mesa
                    System.out.print("Introduza o número da nova mesa: ");
                    int idCriar = scanner.nextInt();
                    System.out.print("Introduza o número de lugares: ");
                    int lugares = scanner.nextInt();
                    System.out.print("A mesa estará ocupada? (true/false): ");
                    boolean ocupada = scanner.nextBoolean();
                    mesas = controller.criarMesa(mesas, idCriar, lugares, ocupada);
                    break;
                case 2:
                    // Editar uma mesa
                    System.out.print("Introduza o número da mesa que deseja editar: ");
                    int idEditar = scanner.nextInt();
                    System.out.print("Introduza o novo número de lugares: ");
                    lugares = scanner.nextInt();
                    System.out.print("A mesa está ocupada? (true/false): ");
                    ocupada = scanner.nextBoolean();
                    controller.atualizarMesa(mesas, idEditar, lugares, ocupada);
                    break;
                case 3:
                    // Apagar uma mesa
                    System.out.print("Introduza o número da mesa que deseja apagar: ");
                    int idDeletar = scanner.nextInt();
                    mesas = controller.eliminarMesa(mesas, idDeletar);
                    break;
                case 4:
                    // Gravar as mesas no arquivo
                    controller.gravarMesasNoFicheiro(mesas, "mesas.txt");
                    break;
                case 5:
                    // Sair
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
