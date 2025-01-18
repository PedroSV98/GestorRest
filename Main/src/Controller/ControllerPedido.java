/** package Controller;

import Model.Pedido;
import java.util.Arrays;

public class ControllerPedido {

    // Método para criar um novo pedido
    public Pedido[] criarPedido(Pedido[] pedidos, int idPedido, int idMesa, boolean ocupada, int lugares, double totalCusto, double precoTotal, double lucro) {
        Pedido novoPedido = new Pedido(idMesa, ocupada, lugares);
        novoPedido.setIdPedido(idPedido);
        novoPedido.setTotalCusto(totalCusto);
        novoPedido.setPrecoTotal(precoTotal);
        novoPedido.setLucro(lucro);

        // Expande o array de pedidos
        Pedido[] novosPedidos = Arrays.copyOf(pedidos, pedidos.length + 1);
        novosPedidos[novosPedidos.length - 1] = novoPedido;
        System.out.println("Pedido criado com ID " + idPedido + " para a mesa " + idMesa);
        return novosPedidos;
    }

    // Método para exibir todos os pedidos
    public void exibirPedidos(Pedido[] pedidos) {
        for (Pedido pedido : pedidos) {
            System.out.println("ID Pedido: " + pedido.getidPedido() + ", Mesa ID: " + pedido.getId() +
                    ", Total Custo: " + pedido.getTotalCusto() + ", Preço Total: " + pedido.getPrecoTotal() +
                    ", Lucro: " + pedido.getLucro());
        }
    }

    // Método para atualizar um pedido
    public void atualizarPedido(Pedido[] pedidos, int idPedido, double totalCusto, double precoTotal, double lucro) {
        Pedido pedido = encontrarPedidoPorId(pedidos, idPedido);
        if (pedido != null) {
            pedido.setTotalCusto(totalCusto);
            pedido.setPrecoTotal(precoTotal);
            pedido.setLucro(lucro);
            System.out.println("Pedido com ID " + idPedido + " atualizado.");
        } else {
            System.out.println("Pedido não encontrado com ID " + idPedido);
        }
    }

    // Método para eliminar um pedido pelo ID do pedido
    public Pedido[] eliminarPedido(Pedido[] pedidos, int idPedido) {
        int index = -1;

        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getidPedido() == idPedido) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            Pedido[] pedidosAtualizados = new Pedido[pedidos.length - 1];
            System.arraycopy(pedidos, 0, pedidosAtualizados, 0, index);
            System.arraycopy(pedidos, index + 1, pedidosAtualizados, index, pedidos.length - index - 1);
            System.out.println("Pedido com ID " + idPedido + " deletado.");
            return pedidosAtualizados;
        } else {
            System.out.println("Pedido não encontrado com ID " + idPedido);
            return pedidos;
        }
    }

    // Método para encontrar um pedido pelo ID do pedido
    private Pedido encontrarPedidoPorId(Pedido[] pedidos, int idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getidPedido() == idPedido) {
                return pedido;
            }
        }
        return null;
    }
}
**/