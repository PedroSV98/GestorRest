package View;

import Controller.ControllerPrato;
import Model.Prato;
import Controller.LogController;  // Importando o LogController

import java.util.Scanner;

public class ViewPrato {

    private ControllerPrato controller;
    private final LogController.ScannerLog scannerLog;// Usando ScannerLog
    private Prato[] pratos;

    public ViewPrato(ControllerPrato controller, LogController logController) {
        this.controller = controller;
        this.scannerLog = logController.new ScannerLog(new Scanner(System.in), logController);  // Inicializando ScannerLog com instância de LogController
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
            opcao = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
            scannerLog.nextLine();  // Consumindo quebra de linha

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
                    System.out.print("Introduza o nome do prato: ");
                    String nomeCriar = scannerLog.nextLine();  // Usando nextLine() do ScannerLog
                    if (controller.encontrarPratoPorNome(this.pratos, nomeCriar) != null) {
                        System.out.println("Erro: Já existe um prato com esse nome.");
                        break;
                    }

                    System.out.print("Introduza a categoria do prato: ");
                    String categoria = scannerLog.nextLine();  // Usando nextLine() do ScannerLog
                    System.out.print("Introduza o preço de custo (ex: 1.4): ");
                    double PC = Double.parseDouble(scannerLog.nextLine().replace(",", "."));  // Usando nextLine() para capturar e converter
                    System.out.print("Introduza o preço de venda (ex: 3.5): ");
                    double PV = Double.parseDouble(scannerLog.nextLine().replace(",", "."));  // Usando nextLine() para capturar e converter
                    System.out.print("Introduza o tempo de preparação: ");
                    int tempPrep = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("Introduza o tempo de consumo: ");
                    int tempCons = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("O prato está disponível? (true/false): ");
                    boolean estado = scannerLog.nextBoolean();  // Usando nextBoolean() do ScannerLog
                    scannerLog.nextLine();  // Consumindo quebra de linha

                    this.pratos = controller.criarPrato(pratos, nomeCriar, categoria, PC, PV, tempPrep, tempCons, estado);
                    System.out.println("Prato criado em memória.");
                    controller.exibirPratos(this.pratos);
                    break;

                case 2:
                    // Editar prato
                    System.out.print("Introduza o nome do prato que deseja editar: ");
                    String nomeEditar = scannerLog.nextLine();  // Usando nextLine() do ScannerLog
                    if (controller.encontrarPratoPorNome(this.pratos, nomeEditar) == null) {
                        System.out.println("Erro: Não existe nenhum prato com esse nome.");
                        break;
                    }

                    System.out.print("Introduza a nova categoria: ");
                    String novaCategoria = scannerLog.nextLine();  // Usando nextLine() do ScannerLog
                    System.out.print("Introduza o novo preço de custo (ex: 1.4): ");
                    double novoPC = Double.parseDouble(scannerLog.nextLine().replace(",", "."));  // Usando nextLine() para capturar e converter
                    System.out.print("Introduza o novo preço de venda (ex: 3.5): ");
                    double novoPV = Double.parseDouble(scannerLog.nextLine().replace(",", "."));  // Usando nextLine() para capturar e converter
                    System.out.print("Introduza o novo tempo de preparação: ");
                    int novoTempPrep = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("Introduza o novo tempo de consumo: ");
                    int novoTempCons = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("O prato estará disponível? (true/false): ");
                    boolean novoEstado = scannerLog.nextBoolean();  // Usando nextBoolean() do ScannerLog
                    scannerLog.nextLine();  // Consumindo quebra de linha

                    controller.atualizarPrato(pratos, nomeEditar, novaCategoria, novoPC, novoPV, novoTempPrep, novoTempCons, novoEstado);
                    System.out.println("Prato atualizado em memória.");
                    controller.exibirPratos(this.pratos);
                    break;

                case 3:
                    // Apagar prato
                    System.out.print("Introduza o nome do prato que deseja apagar: ");
                    String nomeEliminar = scannerLog.nextLine();  // Usando nextLine() do ScannerLog
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
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scannerLog.close();  // Fechando o ScannerLog
    }
}
