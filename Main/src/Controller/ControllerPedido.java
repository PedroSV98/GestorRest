package Controller;

import Model.Pedido;

public class ControllerPedido {
    private Pedido[] pedidos;
    private ConfiguracoesController configuracoesController;

    // Construtor ajustado para receber ConfiguracoesController
    public ControllerPedido(ConfiguracoesController configuracoesController) {
        this.pedidos = new Pedido[0];
        this.configuracoesController = configuracoesController;
    }

    public void adicionarPedido(Pedido pedido) {
        Pedido[] novosPedidos = new Pedido[pedidos.length + 1];
        System.arraycopy(pedidos, 0, novosPedidos, 0, pedidos.length);
        novosPedidos[pedidos.length] = pedido;
        pedidos = novosPedidos;
    }

    public Pedido encontrarPedidoPorNome(String cliente) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().equalsIgnoreCase(cliente)) {
                return pedido;
            }
        }
        return null;
    }

    public Pedido[] getPedidos() {
        return pedidos;
    }

    public void gerarLog(String mensagem) {
        // Use o configuracoesController para acessar caminhos de ficheiros ou configurações, se necessário
        System.out.println("[LOG] " + mensagem); // Aqui pode ser implementada a escrita em ficheiro
    }
}
