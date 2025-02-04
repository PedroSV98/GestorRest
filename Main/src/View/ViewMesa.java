package View;

import Controller.InputHelper;
import Controller.ControllerMesa;
import Model.Mesa;
import java.util.Scanner;

public class ViewMesa {

    private final ControllerMesa controller;
    private final Scanner scanner;
    private Mesa[] mesas; // Array em memória

    public ViewMesa(ControllerMesa controller) {
        this.controller = controller;
        // Configurar o Scanner para UTF-8
        this.scanner = new Scanner(System.in, "UTF-8");
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

            // Utiliza o InputHelper para ler a opção de forma segura
            opcao = InputHelper.lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 0:
                    // Ler do ficheiro apenas se o array estiver vazio
                    if (mesas == null || mesas.length == 0) {
                        mesas = controller.carregarMesas();
                        System.out.println("Mesas carregadas do ficheiro para o array.");
                    } else {
                        System.out.println("As mesas já foram carregadas. As alterações encontram-se no array.");
                    }
                    // Mostrar o estado atual do array
                    controller.exibirMesas(mesas);
                    break;

                case 1:
                    // Criar mesa
                    int idCriar = InputHelper.lerInteiro(scanner, "ID da nova mesa: ");
                    int lugaresCriar = InputHelper.lerInteiro(scanner, "Número de lugares: ");
                    boolean ocupadaCriar = InputHelper.lerBoolean(scanner, "Está ocupada? (true/false): ");
                    mesas = controller.criarMesa(mesas, idCriar, lugaresCriar, ocupadaCriar);
                    System.out.println("Mesa criada com sucesso.");
                    break;

                case 2:
                    // Editar mesa
                    int idEditar = InputHelper.lerInteiro(scanner, "ID da mesa a editar: ");
                    int lugaresEdit = InputHelper.lerInteiro(scanner, "Novo número de lugares: ");
                    boolean ocupadaEdit = InputHelper.lerBoolean(scanner, "Está ocupada? (true/false): ");
                    controller.atualizarMesa(idEditar, lugaresEdit, ocupadaEdit);
                    System.out.println("Mesa atualizada com sucesso.");
                    break;

                case 3:
                    // Apagar mesa
                    int idApagar = InputHelper.lerInteiro(scanner, "ID da mesa a apagar: ");
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
