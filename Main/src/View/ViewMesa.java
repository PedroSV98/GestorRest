package View;

import Controller.ControllerMesa;
import Model.Mesa;
import java.util.Scanner;

public class ViewMesa {

    private final ControllerMesa controller;
    private final Scanner scanner;
    private Mesa[] mesas; // Array em memória

    public ViewMesa(ControllerMesa controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in, "UTF-8"); // Configurar o Scanner para UTF-8
        this.mesas = new Mesa[0];
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 5) {
            System.out.println("\n=== Menu de Mesas ===");
            System.out.println("0 - Ler mesas");
            System.out.println("1 - Criar mesa");
            System.out.println("2 - Editar mesa");
            System.out.println("3 - Apagar mesa");
            System.out.println("4 - Gravar no ficheiro");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 0:
                    // Ler do ficheiro apenas se o array estiver vazio
                    if (mesas == null || mesas.length == 0) {
                        mesas = controller.carregarMesas();
                        System.out.println("Mesas carregadas do ficheiro para o array.");
                    } else {
                        System.out.println("As mesas já foram carregadas. Alterações estão no array.");
                    }
                    // Mostrar o estado atual do array
                    controller.exibirMesas(mesas);
                    break;

                case 1:
                    // Criar mesa
                    System.out.print("ID da nova mesa: ");
                    int idCriar = scanner.nextInt();
                    System.out.print("Número de lugares: ");
                    int lugaresCriar = scanner.nextInt();
                    System.out.print("Está ocupada? (true/false): ");
                    boolean ocupadaCriar = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir quebra de linha
                    mesas = controller.criarMesa(mesas, idCriar, lugaresCriar, ocupadaCriar);
                    System.out.println("Mesa criada com sucesso.");
                    break;

                case 2:
                    // Editar mesa
                    System.out.print("ID da mesa a editar: ");
                    int idEditar = scanner.nextInt();
                    System.out.print("Novo número de lugares: ");
                    int lugaresEdit = scanner.nextInt();
                    System.out.print("Está ocupada? (true/false): ");
                    boolean ocupadaEdit = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir quebra de linha
                    controller.atualizarMesa(mesas, idEditar, lugaresEdit, ocupadaEdit);
                    System.out.println("Mesa atualizada com sucesso.");
                    break;

                case 3:
                    // Apagar mesa
                    System.out.print("ID da mesa a apagar: ");
                    int idApagar = scanner.nextInt();
                    scanner.nextLine(); // Consumir quebra de linha
                    mesas = controller.eliminarMesa(mesas, idApagar);
                    System.out.println("Mesa apagada com sucesso.");
                    break;

                case 4:
                    // Gravar no ficheiro
                    controller.gravarMesas(mesas);
                    System.out.println("Mesas gravadas no ficheiro com sucesso.");
                    break;

                case 5:
                    System.out.println("A sair do menu de mesas...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
