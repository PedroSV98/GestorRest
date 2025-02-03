package View;

import Controller.ControllerMesa;
import Model.Mesa;
import Controller.LogController;
import java.util.Scanner;

public class ViewMesa {

    private final ControllerMesa controller;
    private final LogController.ScannerLog scannerLog;  // Usando ScannerLog
    private Mesa[] mesas; // Array em memória

    public ViewMesa(ControllerMesa controller, LogController logController) {
        this.controller = controller;
        this.scannerLog = logController.new ScannerLog(new Scanner(System.in), logController);  // Inicializando ScannerLog com instância de LogController
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

            // Garantir que a entrada seja um inteiro
            if (scannerLog.hasNextInt()) {
                opcao = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                scannerLog.nextLine();  // Consumindo a quebra de linha após a leitura de um número
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                scannerLog.nextLine();  // Consumindo qualquer entrada inválida
                continue;
            }

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
                    int idCriar = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("Número de lugares: ");
                    int lugaresCriar = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    scannerLog.nextLine();  // Consumindo a quebra de linha
                    boolean ocupadaCriar = obterBoolean("Está ocupada? (true/false): ");
                    mesas = controller.criarMesa(mesas, idCriar, lugaresCriar, ocupadaCriar);
                    System.out.println("Mesa criada com sucesso.");
                    break;

                case 2:
                    // Editar mesa
                    System.out.print("ID da mesa a editar: ");
                    int idEditar = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    System.out.print("Novo número de lugares: ");
                    int lugaresEdit = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    scannerLog.nextLine();  // Consumindo a quebra de linha
                    boolean ocupadaEdit = obterBoolean("Está ocupada? (true/false): ");
                    controller.atualizarMesa(idEditar, lugaresEdit, ocupadaEdit);
                    System.out.println("Mesa atualizada com sucesso.");
                    break;

                case 3:
                    // Apagar mesa
                    System.out.print("ID da mesa a apagar: ");
                    int idApagar = scannerLog.nextInt();  // Usando nextInt() do ScannerLog
                    scannerLog.nextLine(); // Consumindo quebra de linha
                    mesas = controller.eliminarMesa(mesas, idApagar);
                    System.out.println("Mesa apagada com sucesso.");
                    break;

                case 4:
                    // Gravar no ficheiro
                    controller.gravarMesas(mesas);
                    System.out.println("Mesas gravadas no ficheiro com sucesso.");
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

    // Método para ler um boolean de maneira segura
    private boolean obterBoolean(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = scannerLog.nextLine().trim().toLowerCase();
            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Entrada inválida. Digite 'true' ou 'false'.");
            }
        }
    }
}
