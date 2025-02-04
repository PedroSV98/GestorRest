package View;

import Controller.ControllerPrato;
import Controller.InputHelper;
import Model.Prato;
import java.util.Scanner;

public class ViewPrato {

    private ControllerPrato controller;
    private Scanner scanner;
    private Prato[] pratos;

    public ViewPrato(ControllerPrato controller) {
        this.controller = controller;
        // Configurar o Scanner para UTF-8
        this.scanner = new Scanner(System.in, "UTF-8");
        this.pratos = new Prato[0]; // Inicialmente vazio
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 5) {
            System.out.println("\n=== Menu de Pratos ===");
            System.out.println("0 - Ler pratos");
            System.out.println("1 - Criar prato");
            System.out.println("2 - Editar prato");
            System.out.println("3 - Apagar prato");
            System.out.println("4 - Gravar no ficheiro");
            System.out.println("5 - Sair");

            opcao = InputHelper.lerInteiro(scanner, "Escolha uma opção: ");

            switch (opcao) {
                case 0:
                    // Ler do ficheiro apenas se o array estiver vazio
                    if (pratos == null || pratos.length == 0) {
                        pratos = controller.carregarPratos();
                        System.out.println("Pratos carregados do ficheiro para o array.");
                    } else {
                        System.out.println("Os pratos já foram carregados. Alterações estão no array.");
                    }
                    // Mostrar o estado atual do array
                    controller.exibirPratos(pratos);
                    break;

                case 1:
                    // Criar prato
                    String nomeCriar = InputHelper.lerString(scanner, "Introduza o nome do prato: ");
                    if (controller.encontrarPratoPorNome(this.pratos, nomeCriar) != null) {
                        System.out.println("Erro: Já existe um prato com esse nome.");
                        break;
                    }
                    String categoria = InputHelper.lerString(scanner, "Introduza a categoria do prato: ");

                    double PC = 0.0;
                    while (true) {
                        String pcStr = InputHelper.lerString(scanner, "Introduza o preço de custo (ex: 1.4): ");
                        try {
                            PC = Double.parseDouble(pcStr.replace(",", "."));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, insira um número válido para o preço de custo.");
                        }
                    }

                    double PV = 0.0;
                    while (true) {
                        String pvStr = InputHelper.lerString(scanner, "Introduza o preço de venda (ex: 3.5): ");
                        try {
                            PV = Double.parseDouble(pvStr.replace(",", "."));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, insira um número válido para o preço de venda.");
                        }
                    }

                    int tempPrep = InputHelper.lerInteiro(scanner, "Introduza o tempo de preparação: ");
                    int tempCons = InputHelper.lerInteiro(scanner, "Introduza o tempo de consumo: ");
                    boolean estado = InputHelper.lerBoolean(scanner, "O prato está disponível? (true/false): ");

                    this.pratos = controller.criarPrato(pratos, nomeCriar, categoria, PC, PV, tempPrep, tempCons, estado);
                    System.out.println("Prato criado em memória.");
                    controller.exibirPratos(this.pratos);
                    break;

                case 2:
                    // Editar prato
                    String nomeEditar = InputHelper.lerString(scanner, "Introduza o nome do prato que deseja editar: ");
                    if (controller.encontrarPratoPorNome(this.pratos, nomeEditar) == null) {
                        System.out.println("Erro: Não existe nenhum prato com esse nome.");
                        break;
                    }
                    String novaCategoria = InputHelper.lerString(scanner, "Introduza a nova categoria: ");

                    double novoPC = 0.0;
                    while (true) {
                        String novoPCStr = InputHelper.lerString(scanner, "Introduza o novo preço de custo (ex: 1.4): ");
                        try {
                            novoPC = Double.parseDouble(novoPCStr.replace(",", "."));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, insira um número válido para o novo preço de custo.");
                        }
                    }

                    double novoPV = 0.0;
                    while (true) {
                        String novoPVStr = InputHelper.lerString(scanner, "Introduza o novo preço de venda (ex: 3.5): ");
                        try {
                            novoPV = Double.parseDouble(novoPVStr.replace(",", "."));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, insira um número válido para o novo preço de venda.");
                        }
                    }

                    int novoTempPrep = InputHelper.lerInteiro(scanner, "Introduza o novo tempo de preparação: ");
                    int novoTempCons = InputHelper.lerInteiro(scanner, "Introduza o novo tempo de consumo: ");
                    boolean novoEstado = InputHelper.lerBoolean(scanner, "O prato estará disponível? (true/false): ");

                    controller.atualizarPrato(pratos, nomeEditar, novaCategoria, novoPC, novoPV, novoTempPrep, novoTempCons, novoEstado);
                    System.out.println("Prato atualizado em memória.");
                    controller.exibirPratos(this.pratos);
                    break;

                case 3:
                    // Apagar prato
                    String nomeEliminar = InputHelper.lerString(scanner, "Introduza o nome do prato que deseja apagar: ");
                    this.pratos = controller.eliminarPrato(this.pratos, nomeEliminar);
                    System.out.println("Prato eliminado.");
                    controller.exibirPratos(this.pratos);
                    break;

                case 4:
                    // Gravar no ficheiro
                    controller.gravarPratos(this.pratos);
                    System.out.println("Pratos gravados com sucesso no ficheiro.");
                    break;

                case 5:
                    System.out.println("A sair...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
