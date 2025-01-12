package View;

import Controller.ControllerPrato;
import Model.Prato;
import java.util.Scanner;

public class ViewPrato {

    private ControllerPrato controller;
    private Scanner scanner;
    private Prato[] pratos;

    public ViewPrato(ControllerPrato controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
        this.pratos = new Prato[0]; // Inicialmente vazio
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 5) {
            System.out.println("\n=== Menu de Pratos ===");
            System.out.println("0 - Ler e Agrupar pratos do ficheiro");
            System.out.println("1 - Criar prato");
            System.out.println("2 - Editar prato");
            System.out.println("3 - Apagar prato");
            System.out.println("4 - Gravar no ficheiro");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 0:
                    // Agrupar pratos do ficheiro com os da memória
                    this.pratos = controller.AgruparComFicheiro(this.pratos);
                    controller.exibirPratos(this.pratos);
                    break;

                case 1:
                    // Criar novo prato
                    System.out.print("Introduza o nome do prato: ");
                    String nomeCriar = scanner.nextLine();
                    System.out.print("Introduza a categoria do prato: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Introduza o preço de custo (ex: 1.4): ");
                    double PC = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    System.out.print("Introduza o preço de venda (ex: 3.5): ");
                    double PV = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    System.out.print("Introduza o tempo de preparação: ");
                    int tempPrep = scanner.nextInt();
                    System.out.print("Introduza o tempo de consumo: ");
                    int tempCons = scanner.nextInt();
                    System.out.print("O prato está disponível? (true/false): ");
                    boolean estado = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir quebra de linha
                    this.pratos = controller.criarPrato(pratos, nomeCriar, categoria, PC, PV, tempPrep, tempCons, estado);
                    System.out.println("Prato criado em memória.");
                    break;

                case 2:
                    // Editar prato existente
                    System.out.print("Introduza o nome do prato que deseja editar: ");
                    String nomeEditar = scanner.nextLine();
                    System.out.print("Introduza a nova categoria: ");
                    String novaCategoria = scanner.nextLine();
                    System.out.print("Introduza o novo preço de custo (ex: 1.4): ");
                    double novoPC = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    System.out.print("Introduza o novo preço de venda (ex: 3.5): ");
                    double novoPV = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    System.out.print("Introduza o novo tempo de preparação: ");
                    int novoTempPrep = scanner.nextInt();
                    System.out.print("Introduza o novo tempo de consumo: ");
                    int novoTempCons = scanner.nextInt();
                    System.out.print("O prato estará disponível? (true/false): ");
                    boolean novoEstado = scanner.nextBoolean();
                    scanner.nextLine(); // Consumir quebra de linha
                    controller.atualizarPrato(pratos, nomeEditar, novaCategoria, novoPC, novoPV, novoTempPrep, novoTempCons, novoEstado);
                    System.out.println("Prato atualizado em memória.");
                    break;

                case 3:
                    // Apagar prato
                    System.out.print("Introduza o nome do prato que deseja apagar: ");
                    String nomeEliminar = scanner.nextLine();
                    this.pratos = controller.eliminarPrato(this.pratos, nomeEliminar);
                    System.out.println("Prato eliminado.");
                    break;

                case 4:
                    // Gravar no ficheiro
                    controller.gravarPratos(this.pratos);
                    break;

                case 5:
                    // Sair
                    System.out.println("A sair...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }}

