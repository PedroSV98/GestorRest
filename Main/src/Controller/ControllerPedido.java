package Controller;

import Model.Pedido;
import Model.Mesa;
import java.util.Arrays;
import java.util.Scanner;

    public class ControllerPedido {

        // Método para criar um novo pedido
        public Pedido[] criarPedido(Pedido[] pedidos, int idMesa, boolean ocupada, int lugares, double totalCusto, double precoTotal, double lucro) {
            Pedido novoPedido = new Pedido(idMesa, ocupada, lugares);
            novoPedido.setTotalCusto(totalCusto);
            novoPedido.setPrecoTotal(precoTotal);
            novoPedido.setLucro(lucro);

            // Expande o array de pedidos
            Pedido[] novosPedidos = Arrays.copyOf(pedidos, pedidos.length + 1);
            novosPedidos[novosPedidos.length - 1] = novoPedido;
            System.out.println("Pedido criado para a mesa " + idMesa);
            return novosPedidos;
        }

        // Método para exibir todos os pedidos
        public void exibirPedidos(Pedido[] pedidos) {
            for (Pedido pedido : pedidos) {
                System.out.println("Mesa ID: " + pedido.getId() + ", Total Custo: " + pedido.getTotalCusto() + ", Preço Total: " + pedido.getPrecoTotal() + ", Lucro: " + pedido.getLucro());
            }
        }

        // Método para atualizar um pedido
        public void atualizarPedido(Pedido[] pedidos, int idMesa, double totalCusto, double precoTotal, double lucro) {
            Pedido pedido = encontrarPedidoPorMesa(pedidos, idMesa);
            if (pedido != null) {
                pedido.setTotalCusto(totalCusto);
                pedido.setPrecoTotal(precoTotal);
                pedido.setLucro(lucro);
                System.out.println("Pedido da mesa " + idMesa + " atualizado.");
            } else {
                System.out.println("Pedido não encontrado para a mesa " + idMesa);
            }
        }

        // Método para eliminar um pedido pelo ID da mesa
        public Pedido[] eliminarPedido(Pedido[] pedidos, int idMesa) {
            int index = -1;

            for (int i = 0; i < pedidos.length; i++) {
                if (pedidos[i].getId() == idMesa) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                Pedido[] pedidosAtualizados = new Pedido[pedidos.length - 1];
                System.arraycopy(pedidos, 0, pedidosAtualizados, 0, index);
                System.arraycopy(pedidos, index + 1, pedidosAtualizados, index, pedidos.length - index - 1);
                System.out.println("Pedido da mesa " + idMesa + " deletado.");
                return pedidosAtualizados;
            } else {
                System.out.println("Pedido não encontrado para a mesa " + idMesa);
                return pedidos;
            }
        }

        // Método para encontrar um pedido pela ID da mesa
        private Pedido encontrarPedidoPorMesa(Pedido[] pedidos, int idMesa) {
            for (Pedido pedido : pedidos) {
                if (pedido.getId() == idMesa) {
                    return pedido;
                }
            }
            return null;
        }
    }


