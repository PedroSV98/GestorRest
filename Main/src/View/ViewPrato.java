package View;

import Controller.ControllerPrato;
import Model.Prato;

import java.util.Scanner;

public class ViewPrato {

    // Função principal do menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControllerPrato controller = new ControllerPrato();
        Prato[] pratos = controller.carregarPratosDoArquivo("pratos.txt");

        int opcao = -1;

        while (opcao != 5) {
            // Menu de opções
            System.out.println("Menu:");
            System.out.println("0 - Ler pratos");
            System.out.println("1 - Criar prato");
            System.out.println("2 - Editar prato");
            System.out.println("3 - Apagar prato");
            System.out.println("4 - Gravar no arquivo");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após a opção

            switch (opcao) {
                case 0:
                    // Ler e exibir pratos
                    controller.exibirPratos(pratos);
                    break;
                case 1:
                    // Criar um novo prato
                    System.out.print("Digite o nome do prato: ");
                    String nomeCriar = scanner.nextLine();
                    System.out.print("Digite a categoria do prato: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Digite o preço de custo: ");
                    double PC = scanner.nextDouble();
                    System.out.print("Digite o preço de venda: ");
                    double PV = scanner.nextDouble();
                    System.out.print("Digite o tempo de preparação: ");
                    int tempPrep = scanner.nextInt();
                    System.out.print("Digite o tempo de consumo: ");
                    int tempCons = scanner.nextInt();
                    System.out.print("O prato está disponível? (true/false): ");
                    boolean estado = scanner.nextBoolean();
                    pratos = controller.criarPrato(pratos, nomeCriar, categoria, PC, PV, tempPrep, tempCons, estado);
                    break;
                case 2:
                    // Editar um prato
                    System.out.print("Digite o nome do prato que deseja editar: ");
                    String nomeEditar = scanner.nextLine();
                    System.out.print("Digite a nova categoria: ");
                    String novaCategoria = scanner.nextLine();
                    System.out.print("Digite o novo preço de custo: ");
                    double novoPC = scanner.nextDouble();
                    System.out.print("Digite o novo preço de venda: ");
                    double novoPV = scanner.nextDouble();
                    System.out.print("Digite o novo tempo de preparação: ");
                    int novoTempPrep = scanner.nextInt();
                    System.out.print("Digite o novo tempo de consumo: ");
                    int novoTempCons = scanner.nextInt();
                    System.out.print("O prato estará disponível? (true/false): ");
                    boolean novoEstado = scanner.nextBoolean();
                    controller.atualizarPrato(pratos, nomeEditar, novaCategoria, novoPC, novoPV, novoTempPrep, novoTempCons, novoEstado);
                    break;
                case 3:
                    // Apagar um prato
                    System.out.print("Digite o nome do prato que deseja apagar: ");
                    String nomeEliminar = scanner.nextLine();
                    pratos = controller.eliminarPrato(pratos, nomeEliminar);
                    break;
                case 4:
                    // Gravar os pratos no arquivo
                    controller.gravarPratosNoArquivo(pratos, "pratos.txt");
                    break;
                case 5:
                    // Sair
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
