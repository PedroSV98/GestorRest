package Controller;

import Model.Pedido;
import Model.Prato;
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

    public Prato[] getTodosPratos() {
        int totalPratos = 0;

        // Contar o número total de pratos nos pedidos
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getEntrada() != null) totalPratos++;
            if (pedidos[i].getPrincipal() != null) totalPratos++;
            if (pedidos[i].getSobremesa() != null) totalPratos++;
        }

        // Criar um array do tamanho certo
        Prato[] todosPratos = new Prato[totalPratos];
        int index = 0;

        // Preencher o array com os pratos dos pedidos
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getEntrada() != null) todosPratos[index++] = pedidos[i].getEntrada();
            if (pedidos[i].getPrincipal() != null) todosPratos[index++] = pedidos[i].getPrincipal();
            if (pedidos[i].getSobremesa() != null) todosPratos[index++] = pedidos[i].getSobremesa();
        }

        return todosPratos;
    }


    public void gerarLog(String mensagem) {
        // Use o configuracoesController para acessar caminhos de ficheiros ou configurações, se necessário
        System.out.println("[LOG] " + mensagem); // Aqui pode ser implementada a escrita em ficheiro
    }
}