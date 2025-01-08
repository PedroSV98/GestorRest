package View;

import Controller.ControllerPedido;
import Model.Pedido;

import java.util.Scanner;

public class ViewPedido {
    // Função principal do menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControllerPedido controller = new ControllerPedido();
        Pedido[] pedidos = new Pedido[0]; // Inicializa o array de pedidos vazio

        int opcao = -1;

        while (opcao != 5) {
            // Menu de opções
            System.out.println("Menu:");
            System.out.println("0 - Ler pedidos");
            System.out.println("1 - Criar pedido");
            System.out.println("2 - Editar pedido");
            System.out.println("3 - Apagar pedido");
            System.out.println("4 - Exibir pedidos");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 0:
                    // Ler pedidos
                    controller.exibirPedidos(pedidos);
                    break;
                case 1:
                    // Criar pedido
                    System.out.print("Digite o ID da mesa: ");
                    int idMesa = scanner.nextInt();
                    System.out.print("Está a mesa ocupada? (true/false): ");
                    boolean ocupada = scanner.nextBoolean();
                    System.out.print("Quantos lugares na mesa? ");
                    int lugares = scanner.nextInt();
                    System.out.print("Digite o total de custo: ");
                    double totalCusto = scanner.nextDouble();
                    System.out.print("Digite o preço total: ");
                    double precoTotal = scanner.nextDouble();
                    System.out.print("Digite o lucro: ");
                    double lucro = scanner.nextDouble();
                    pedidos = controller.criarPedido(pedidos, idMesa, ocupada, lugares, totalCusto, precoTotal, lucro);
                    break;
                case 2:
                    // Editar pedido
                    System.out.print("Digite o ID da mesa para editar o pedido: ");
                    int idMesaEditar = scanner.nextInt();
                    System.out.print("Digite o novo total de custo: ");
                    double totalCustoEditar = scanner.nextDouble();
                    System.out.print("Digite o novo preço total: ");
                    double precoTotalEditar = scanner.nextDouble();
                    System.out.print("Digite o novo lucro: ");
                    double lucroEditar = scanner.nextDouble();
                    controller.atualizarPedido(pedidos, idMesaEditar, totalCustoEditar, precoTotalEditar, lucroEditar);
                    break;
                case 3:
                    // Apagar pedido
                    System.out.print("Digite o ID da mesa para apagar o pedido: ");
                    int idMesaeliminar = scanner.nextInt();
                    pedidos = controller.eliminarPedido(pedidos, idMesaeliminar);
                    break;
                case 4:
                    // Exibir pedidos
                    controller.exibirPedidos(pedidos);
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
